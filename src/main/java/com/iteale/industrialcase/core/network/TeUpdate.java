package com.iteale.industrialcase.core.network;


import com.iteale.industrialcase.api.network.INetworkUpdateListener;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.WorldData;
import com.iteale.industrialcase.core.block.BlockEntityBase;
import com.iteale.industrialcase.core.util.LogCategory;
import com.iteale.industrialcase.core.util.ReflectionUtil;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.io.IOException;
import java.util.*;

class TeUpdate {
    public static void send(WorldData worldData, NetworkManager network) throws IOException {
        if (worldData.tesToUpdate.isEmpty())
            return;
        Map<ServerPlayer, GrowingBuffer> buffers = new IdentityHashMap<>();
        List<ServerPlayer> playersInRange = new ArrayList<>();
        GrowingBuffer commonBuffer = new GrowingBuffer();

        for (Map.Entry<BlockEntity, TeUpdateDataServer> entry : (Iterable<Map.Entry<BlockEntity, TeUpdateDataServer>>) worldData.tesToUpdate.entrySet()) {
            BlockEntity te = entry.getKey();

            NetworkManager.getPlayersInRange(te.getLevel(), te.getBlockPos(), playersInRange);
            if (playersInRange.isEmpty())
                continue;
            TeUpdateDataServer updateData = entry.getValue();


            DataEncoder.encode(commonBuffer, te.getBlockPos(), false);
            commonBuffer.mark();
            commonBuffer.writeShort(0);

            for (String field : updateData.getGlobalFields()) {
                NetworkManager.writeFieldData(te, field, commonBuffer);
            }

            commonBuffer.flip();

            for (ServerPlayer player : playersInRange) {
                Collection<String> playerFields = updateData.getPlayerFields(player);
                int fieldCount = updateData.getGlobalFields().size() + playerFields.size();
                if (fieldCount == 0)
                    continue;
                if (fieldCount > 65535) throw new RuntimeException("too many fields for " + te + ": " + fieldCount);


                commonBuffer.reset();
                commonBuffer.writeShort(fieldCount);
                commonBuffer.rewind();


                GrowingBuffer playerBuffer = buffers.get(player);

                if (playerBuffer == null) {
                    playerBuffer = new GrowingBuffer(0);
                    buffers.put(player, playerBuffer);

                    playerBuffer.writeString((player.getLevel()).dimension().location().toString());
                }

                commonBuffer.writeTo(playerBuffer);
                commonBuffer.rewind();

                for (String field : playerFields) {
                    NetworkManager.writeFieldData(te, field, playerBuffer);
                }
            }

            commonBuffer.clear();
            playersInRange.clear();
        }

        worldData.tesToUpdate.clear();

        for (Map.Entry<ServerPlayer, GrowingBuffer> entry : buffers.entrySet()) {
            ServerPlayer player = entry.getKey();
            GrowingBuffer playerBuffer = entry.getValue();

            playerBuffer.flip();
            network.sendLargePacket(player, 0, playerBuffer);
        }
    }

    static void receive(GrowingBuffer buffer) throws IOException {
        final String dimensionId = buffer.readString();
        final TeUpdateDataClient updateData = new TeUpdateDataClient();

        while (buffer.hasAvailable()) {
            BlockPos pos = DataEncoder.<BlockPos>decode(buffer, BlockPos.class);
            int fieldCount = buffer.readUnsignedShort();

            TeUpdateDataClient.TeData teData = updateData.addTe(pos, fieldCount);

            for (int i = 0; i < fieldCount; i++) {
                String fieldName = buffer.readString();
                Object value = DataEncoder.decode(buffer);

                if (fieldName.equals("teBlk")) {
                    String name = (String) value;

                    if (name.startsWith("Old-")) {
                        teData.teClass = TeBlockRegistry.getOld(name);
                    } else {
                        teData.teClass = TeBlockRegistry.get(name).getTeClass();
                    }
                } else {
                    teData.addField(fieldName, value);
                }
            }

            if (teData.teClass != null) {
                for (TeUpdateDataClient.FieldData fieldData : teData.getFields()) {
                    fieldData.field = ReflectionUtil.getFieldRecursive(teData.teClass, fieldData.name);
                }
            }
        }

        if (debug) printDebugOutput(dimensionId, updateData);

        IndustrialCase.platform.requestTick(false, new Runnable() {
            public void run() {
                Level world = IndustrialCase.platform.getPlayerWorld();
                if (world == null || world.dimension().location().toString() != dimensionId)
                    return;
                for (TeUpdateDataClient.TeData update : updateData.getTes()) {
                    try {
                        TeUpdate.apply(update, world);
                    } catch (Throwable t) {
                        IndustrialCase.log.warn(LogCategory.Network, t, "TE update at %s failed.", Util.formatPosition(world, update.pos));
                    }
                }
            }
        });
    }

    private static void printDebugOutput(String dimensionId, TeUpdateDataClient data) {
        StringBuilder out = new StringBuilder();

        out.append("dimension: ");
        out.append(dimensionId);
        out.append(", ");
        out.append(data.getTes().size());
        out.append(" tes:\n");

        for (TeUpdateDataClient.TeData te : data.getTes()) {
            out.append("  pos: ");
            out.append(te.pos.getX());
            out.append('/');
            out.append(te.pos.getY());
            out.append('/');
            out.append(te.pos.getZ());
            out.append(", ");
            out.append(te.getFields().size());
            out.append(" fields:\n");

            for (TeUpdateDataClient.FieldData field : te.getFields()) {
                out.append("    ");
                out.append(field.name);
                out.append(" = ");
                out.append(field.value);

                if (field.value != null) {
                    out.append(" (");
                    out.append(field.value.getClass().getSimpleName());
                    out.append(')');
                }

                out.append('\n');
            }

            if (te.teClass != null) {
                out.append("    TE Class: ");
                out.append(te.teClass.getName());
                out.append('\n');
                continue;
            }
            out.append("    no TE Class\n");
        }


        out.setLength(out.length() - 1);

        IndustrialCase.log.info(LogCategory.Network, "Received TE Update:\n" + out.toString());
    }

    private static void apply(TeUpdateDataClient.TeData update, Level world) {
        BlockEntityBase tileEntityBlock;
        if (!world.isLoaded(update.pos)) {
            if (debug)
                IndustrialCase.log.info(LogCategory.Network, "Skipping update at %s, chunk not loaded.", Util.formatPosition(world, update.pos));

            return;
        }

        BlockEntity te = world.getBlockEntity(update.pos);

        if (update.teClass != null && (te == null || te.getClass() != update.teClass || te.isRemoved() || te.getLevel() != world)) {
            if (debug)
                IndustrialCase.log.info(LogCategory.Network, "Instantiating %s with %s.", Util.formatPosition(world, update.pos), update.teClass.getName());

            tileEntityBlock = BlockEntityBase.instantiate(update.teClass);
            world.setBlockEntity(update.pos, (BlockEntity) tileEntityBlock);

            assert !tileEntityBlock.isRemoved();
            assert tileEntityBlock.getLevel() == world;
        } else {
            if (tileEntityBlock == null) {
                if (debug)
                    IndustrialCase.log.info(LogCategory.Network, "Can't apply update at %s, no te and no teClass.", Util.formatPosition(world, update.pos));
                return;
            }
            if (tileEntityBlock.isRemoved() || tileEntityBlock.getLevel() != world) {
                if (debug)
                    IndustrialCase.log.warn(LogCategory.Network, "Can't apply update at %s, invalid te and no teClass.", Util.formatPosition(world, update.pos));
                return;
            }
            if (debug)
                IndustrialCase.log.info(LogCategory.Network, "TE class at %s unchanged.", Util.formatPosition(world, update.pos));
        }

        for (TeUpdateDataClient.FieldData fieldUpdate : update.getFields()) {
            Object value = DataEncoder.getValue(fieldUpdate.value);

            if (fieldUpdate.field != null) {
                ReflectionUtil.setValue(tileEntityBlock, fieldUpdate.field, value);
            } else {
                ReflectionUtil.setValueRecursive(tileEntityBlock, fieldUpdate.name, value);
            }

            if (tileEntityBlock instanceof INetworkUpdateListener) {
                ((INetworkUpdateListener) tileEntityBlock).onNetworkUpdate(fieldUpdate.name);
            }
        }
    }

    static final boolean debug = (System.getProperty("ic2.network.debug.teupdate") != null);
}
