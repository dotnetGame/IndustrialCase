package com.iteale.industrialcase.core.network;


import com.iteale.industrialcase.api.info.ITeBlock;
import com.iteale.industrialcase.api.network.*;
import com.iteale.industrialcase.core.IHasGui;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.MenuBase;
import com.iteale.industrialcase.core.WorldData;
import com.iteale.industrialcase.core.block.TileEntityBlock;
import com.iteale.industrialcase.core.util.LogCategory;
import com.iteale.industrialcase.core.util.ReflectionUtil;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.event.EventNetworkChannel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

public class NetworkManager implements INetworkManager {
    private static final String PROTOCOL_VERSION = "1.1.0";
    public EventNetworkChannel channel = null;

    public NetworkManager() {
        if (channel == null) {
            channel = NetworkRegistry.newEventChannel(
                    new ResourceLocation(IndustrialCase.MODID, "network.channel"),
                    () -> PROTOCOL_VERSION,
                    PROTOCOL_VERSION::equals,
                    PROTOCOL_VERSION::equals);
        }

        channel.registerObject(this);
    }

    protected boolean isClient() {
        return false;
    }

    public void onTickEnd(WorldData worldData) {
        try {
            TeUpdate.send(worldData, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void sendPlayerItemData(Player player, int slot, Object... data) {
        GrowingBuffer buffer = new GrowingBuffer(256);

        try {
            SubPacketType.PlayerItemData.writeTo(buffer);
            buffer.writeByte(slot);
            DataEncoder.encode(buffer, (player.getInventory().getItem(slot)).getItem(), false);
            buffer.writeVarInt(data.length);

            for (Object o : data) {
                DataEncoder.encode(buffer, o);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buffer.flip();

        if (!isClient()) {
            sendPacket(buffer, true, (ServerPlayer) player);
        } else {
            sendPacket(buffer);
        }
    }


    public final void updateBlockEntityField(BlockEntity te, String field) {
        if (!isClient()) {
            getTeUpdateData(te).addGlobalField(field);
        } else if (getClientModifiableField(te.getClass(), field) == null) {
            IndustrialCase.log.warn(LogCategory.Network, "Field update for %s failed.", new Object[]{te});
        } else {
            GrowingBuffer buffer = new GrowingBuffer(64);

            try {
                SubPacketType.BlockEntityData.writeTo(buffer);
                DataEncoder.encode(buffer, te, false);
                writeFieldData(te, field, buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            buffer.flip();
            sendPacket(buffer);
        }
    }

    private Field getClientModifiableField(Class<?> cls, String fieldName) {
        Field field = ReflectionUtil.getFieldRecursive(cls, fieldName);

        if (field == null) {
            IndustrialCase.log.warn(LogCategory.Network, "Can't find field %s in %s.", new Object[]{fieldName, cls.getName()});
            return null;
        }

        if (field.getAnnotation(ClientModifiable.class) == null) {
            IndustrialCase.log.warn(LogCategory.Network, "The field %s in %s is not modifiable.", new Object[]{fieldName, cls.getName()});
            return null;
        }

        return field;
    }

    private static TeUpdateDataServer getTeUpdateData(BlockEntity te) {
        assert IndustrialCase.platform.isSimulating();
        if (te == null) throw new NullPointerException();

        WorldData worldData = WorldData.get(te.getLevel());

        TeUpdateDataServer ret = (TeUpdateDataServer) worldData.tesToUpdate.get(te);

        if (ret == null) {
            ret = new TeUpdateDataServer();
            worldData.tesToUpdate.put(te, ret);
        }

        return ret;
    }

    public final void updateBlockEntityFieldTo(BlockEntity te, String field, ServerPlayer player) {
        assert !isClient();

        getTeUpdateData(te).addPlayerField(field, player);
    }

    public final void sendComponentUpdate(TileEntityBlock te, String componentName, ServerPlayer player, GrowingBuffer data) {
        assert !isClient();

        if (player.getLevel() != te.getLevel())
            throw new IllegalArgumentException("mismatched world (te " + te.getLevel() + ", player " + player.getLevel() + ")");

        GrowingBuffer buffer = new GrowingBuffer(64);

        try {
            SubPacketType.BlockEntityBlockComponent.writeTo(buffer);
            DataEncoder.encode(buffer, te, false);
            buffer.writeString(componentName);
            buffer.writeVarInt(data.available());
            data.writeTo(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buffer.flip();
        sendPacket(buffer, true, player);
    }


    public final void initiateBlockEntityEvent(BlockEntity te, int event, boolean limitRange) {
        assert !isClient();

        if ((te.getLevel()).players().isEmpty())
            return;
        GrowingBuffer buffer = new GrowingBuffer(32);

        try {
            SubPacketType.BlockEntityEvent.writeTo(buffer);
            DataEncoder.encode(buffer, te, false);
            buffer.writeInt(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buffer.flip();

        for (ServerPlayer target : getPlayersInRange(te.getLevel(), te.getBlockPos(), new ArrayList())) {
            if (limitRange) {
                int dX = (int) (te.getBlockPos().getX() + 0.5D - target.getX());
                int dZ = (int) (te.getBlockPos().getZ() + 0.5D - target.getZ());

                if (dX * dX + dZ * dZ > 400)
                    continue;
            }
            sendPacket(buffer, false, target);
        }
    }


    public final void initiateItemEvent(ServerPlayer player, ItemStack stack, int event, boolean limitRange) {
        if (StackUtil.isEmpty(stack)) throw new NullPointerException("invalid stack: " + StackUtil.toStringSafe(stack));

        assert !isClient();

        GrowingBuffer buffer = new GrowingBuffer(256);

        try {
            SubPacketType.ItemEvent.writeTo(buffer);
            DataEncoder.encode(buffer, player.getGameProfile(), false);
            DataEncoder.encode(buffer, stack, false);
            buffer.writeInt(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        buffer.flip();

        for (ServerPlayer target : getPlayersInRange(player.getLevel(), player.getOnPos(), new ArrayList())) {
            if (limitRange) {
                int dX = (int) (player.getX() - target.getX());
                int dZ = (int) (player.getZ() - target.getZ());

                if (dX * dX + dZ * dZ > 400)
                    continue;
            }
            sendPacket(buffer, false, target);
        }
    }


    public void initiateClientItemEvent(ItemStack stack, int event) {
        assert false;
    }


    public void initiateClientBlockEntityEvent(BlockEntity te, int event) {
        assert false;
    }

    public void initiateRpc(int id, Class<? extends IRpcProvider<?>> provider, Object[] args) {
        assert false;
    }

    public void requestGUI(IHasGui inventory) {
        assert false;
    }

    public final void initiateGuiDisplay(ServerPlayer player, IHasGui inventory, int windowId) {
        initiateGuiDisplay(player, inventory, windowId, null);
    }

    public final void initiateGuiDisplay(ServerPlayer player, IHasGui inventory, int windowId, Integer ID) {
        assert !isClient();

        try {
            GrowingBuffer buffer = new GrowingBuffer(32);

            SubPacketType.GuiDisplay.writeTo(buffer);

            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            boolean isAdmin = server.getPlayerList().isOp(player.getGameProfile());

            buffer.writeBoolean(isAdmin);

            if (inventory instanceof BlockEntity) {
                BlockEntity te = (BlockEntity) inventory;

                buffer.writeByte(0);
                DataEncoder.encode(buffer, te, false);
            } else if (player.getInventory().getSelected() != null && player.getInventory().getSelected() instanceof IHandHeldInventory) {
                buffer.writeByte(1);
                buffer.writeInt(player.getInventory().selected);
                handleSubData(buffer, player.getInventory().getSelected(), ID);
            } else if (player.getOffhandItem() != null && player.getOffhandItem().getItem() instanceof IHandHeldInventory) {
                buffer.writeByte(1);
                buffer.writeInt(-1);
                handleSubData(buffer, player.getOffhandItem(), ID);
            } else {
                IndustrialCase.platform.displayError("An unknown GUI type was attempted to be displayed.\nThis could happen due to corrupted data from a player or a bug.\n\n(Technical information: " + inventory + ")", new Object[0]);
            }

            buffer.writeInt(windowId);

            buffer.flip();
            sendPacket(buffer, true, player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final void handleSubData(GrowingBuffer buffer, ItemStack stack, Integer ID) {
        boolean subInv = (ID != null && stack.getItem() instanceof IHandHeldSubInventory);

        buffer.writeBoolean(subInv);
        if (subInv) {
            buffer.writeShort(ID.intValue());
        }
    }

    public final void sendInitialData(BlockEntity te, ServerPlayer player) {
        assert !isClient();

        if (te instanceof INetworkDataProvider) {
            TeUpdateDataServer updateData = getTeUpdateData(te);

            for (String field : ((INetworkDataProvider) te).getNetworkedFields()) {
                updateData.addPlayerField(field, player);
            }
        }
    }


    public final void sendInitialData(BlockEntity te) {
        assert !isClient();

        if (te instanceof INetworkDataProvider) {
            TeUpdateDataServer updateData = getTeUpdateData(te);
            List<String> fields = ((INetworkDataProvider) te).getNetworkedFields();

            for (String field : fields) {
                updateData.addGlobalField(field);
            }

            if (TeUpdate.debug) {
                IndustrialCase.log.info(LogCategory.Network, "Sending initial TE data for %s (%s).", new Object[]{Util.formatPosition(te), fields});
            }
        }
    }

    public final void sendChat(ServerPlayer player, String message) {
        assert !isClient();

        GrowingBuffer buffer = new GrowingBuffer(message.length() * 2);
        buffer.writeString(message);

        buffer.flip();
        sendLargePacket(player, 1, buffer);
    }

    public final void sendConsole(ServerPlayer player, String message) {
        assert !isClient();

        GrowingBuffer buffer = new GrowingBuffer(message.length() * 2);
        buffer.writeString(message);

        buffer.flip();
        sendLargePacket(player, 2, buffer);
    }

    public final void sendContainerFields(ContainerBase<?> container, String... fieldNames) {
        for (String fieldName : fieldNames) {
            sendContainerField(container, fieldName);
        }
    }

    public final void sendContainerField(ContainerBase<?> container, String fieldName) {
        if (isClient() &&
                getClientModifiableField(container.getClass(), fieldName) == null) {
            IndustrialCase.log.warn(LogCategory.Network, "Field update for %s failed.", new Object[]{container});

            return;
        }
        GrowingBuffer buffer = new GrowingBuffer(256);

        try {
            SubPacketType.ContainerData.writeTo(buffer);
            buffer.writeInt(container.windowId);
            writeFieldData(container, fieldName, buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buffer.flip();

        if (!isClient()) {
            for (IContainerListener listener : container.getListeners()) {
                if (listener instanceof ServerPlayer) sendPacket(buffer, false, (ServerPlayer) listener);
            }
        } else {
            sendPacket(buffer);
        }
    }

    public final void sendContainerEvent(ContainerBase<?> container, String event) {
        GrowingBuffer buffer = new GrowingBuffer(64);

        SubPacketType.ContainerEvent.writeTo(buffer);
        buffer.writeInt(container.windowId);
        buffer.writeString(event);

        buffer.flip();

        if (!isClient()) {
            for (IContainerListener listener : container.getListeners()) {
                if (listener instanceof ServerPlayer) sendPacket(buffer, false, (ServerPlayer) listener);
            }
        } else {
            sendPacket(buffer);
        }
    }

    public final void sendHandHeldInvField(MenuBase<?> container, String fieldName) {
        if (!(container.base instanceof HandHeldInventory)) {
            IndustrialCase.log.warn(LogCategory.Network, "Invalid container (%s) sent for field update.", new Object[]{container});

            return;
        }
        if (isClient() && getClientModifiableField(container.base.getClass(), fieldName) == null) {
            IndustrialCase.log.warn(LogCategory.Network, "Field update for %s failed.", container);

            return;
        }
        GrowingBuffer buffer = new GrowingBuffer(256);

        try {
            SubPacketType.HandHeldInvData.writeTo(buffer);
            buffer.writeInt(container.containerId);
            writeFieldData(container.base, fieldName, buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buffer.flip();

        if (!isClient()) {
            for (ContainerListener listener : container.getListeners()) {
                if (listener instanceof ServerPlayer) sendPacket(buffer, false, (ServerPlayer) listener);
            }
        } else {
            sendPacket(buffer);
        }
    }

    public final void initiateTeblockLandEffect(Level world, double x, double y, double z, int count, ITeBlock teBlock) {
        initiateTeblockLandEffect(world, null, x, y, z, count, teBlock);
    }

    public final void initiateTeblockLandEffect(Level world, BlockPos pos, double x, double y, double z, int count, ITeBlock teBlock) {
        assert !isClient();

        GrowingBuffer buffer = new GrowingBuffer(64);

        try {
            SubPacketType.BlockEntityBlockLandEffect.writeTo(buffer);
            DataEncoder.encode(buffer, world, false);
            if (pos != null) {
                buffer.writeBoolean(true);
                DataEncoder.encode(buffer, pos, false);
            } else {
                buffer.writeBoolean(false);
            }
            buffer.writeDouble(x);
            buffer.writeDouble(y);
            buffer.writeDouble(z);
            buffer.writeInt(count);
            buffer.writeString(teBlock.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buffer.flip();

        for (Player player : world.players()) {
            if (!(player instanceof ServerPlayer))
                continue;
            double distance = player.distanceToSqr(x, y, z);

            if (distance <= 1024.0D) {
                sendPacket(buffer, false, (ServerPlayer) player);
            }
        }
    }

    public final void initiateTeblockRunEffect(Level world, Entity entity, ITeBlock teBlock) {
        initiateTeblockRunEffect(world, null, entity, teBlock);
    }

    public final void initiateTeblockRunEffect(Level world, BlockPos pos, Entity entity, ITeBlock teBlock) {
        assert !isClient();

        GrowingBuffer buffer = new GrowingBuffer(64);

        try {
            SubPacketType.BlockEntityBlockRunEffect.writeTo(buffer);
            DataEncoder.encode(buffer, world, false);
            if (pos != null) {
                buffer.writeBoolean(true);
                DataEncoder.encode(buffer, pos, false);
            } else {
                buffer.writeBoolean(false);
            }
            buffer.writeDouble(entity.getX() + (IndustrialCase.random.nextFloat() - 0.5D) * entity.getBbWidth());
            buffer.writeDouble((entity.getBoundingBox()).minY + 0.1D);
            buffer.writeDouble(entity.getZ() + (IndustrialCase.random.nextFloat() - 0.5D) * entity.getBbWidth());
            buffer.writeDouble(-entity.motionX * 4.0D);

            buffer.writeDouble(-entity.motionZ * 4.0D);
            buffer.writeString(teBlock.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buffer.flip();

        for (Player player : world.players()) {
            if (!(player instanceof ServerPlayer))
                continue;
            double distance = player.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());

            if (distance <= 1024.0D)
                sendPacket(buffer, false, (ServerPlayer) player);
        }
    }

    final void sendLargePacket(ServerPlayer player, int id, GrowingBuffer data) {
        boolean lastPacket;
        GrowingBuffer buffer = new GrowingBuffer(16384);
        buffer.writeShort(0);

        try {
            DeflaterOutputStream deflate = new DeflaterOutputStream(buffer);
            data.writeTo(deflate);
            deflate.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buffer.flip();

        boolean firstPacket = true;


        do {
            lastPacket = (buffer.available() <= 32766);

            if (!firstPacket) buffer.skipBytes(-2);

            SubPacketType.LargePacket.writeTo(buffer);

            int state = 0;

            if (firstPacket) state |= 0x1;
            if (lastPacket) state |= 0x2;

            state |= id << 2;

            buffer.write(state);
            buffer.skipBytes(-2);

            if (lastPacket) {
                sendPacket(buffer, true, player);
                assert !buffer.hasAvailable();
            } else {
                sendPacket(buffer.copy(32766), true, player);
            }

            firstPacket = false;
        } while (!lastPacket);
    }

    @SubscribeEvent
    public void onPacket(NetworkEvent event) {
        // FMLNetworkEvent.ServerCustomPacketEvent
        if (getClass() == NetworkManager.class) {
            try {
                onPacketData(GrowingBuffer.wrap(event.getPacket().payload()),
                        (Player) ((NetHandlerPlayServer) event.getHandler()).player);
            } catch (Throwable t) {
                IndustrialCase.log.warn(LogCategory.Network, t, "Network read failed");
                throw new RuntimeException(t);
            }

            event.getPacket().payload().release();
        }
    }

    private void onPacketData(GrowingBuffer is, final Player player) throws IOException {
        final ItemStack stack;
        final int keyState;
        final Object teDeferred;
        final boolean hand;
        final int event;
        final Object teDeferred;
        if (!is.hasAvailable())
            return;
        SubPacketType packetType = SubPacketType.read(is, true);
        if (packetType == null)
            return;
        switch (packetType) {
            case ItemEvent:
                stack = DataEncoder.<ItemStack>decode(is, ItemStack.class);
                event = is.readInt();

                if (stack.getItem() instanceof INetworkItemEventListener) {
                    IndustrialCase.platform.requestTick(true, new Runnable() {
                        public void run() {
                            ((INetworkItemEventListener) stack.getItem()).onNetworkEvent(stack, player, event);
                        }
                    });
                }
                return;

            case KeyUpdate:
                keyState = is.readInt();

                IndustrialCase.platform.requestTick(true, new Runnable() {
                    public void run() {
                        IndustrialCase.keyboard.processKeyUpdate(player, keyState);
                    }
                });
                return;

            case BlockEntityEvent:
                teDeferred = DataEncoder.decodeDeferred(is, BlockEntity.class);
                event = is.readInt();

                IndustrialCase.platform.requestTick(true, new Runnable() {
                    public void run() {
                        BlockEntity te = DataEncoder.<BlockEntity>getValue(teDeferred);

                        if (te instanceof INetworkClientBlockEntityEventListener) {
                            ((INetworkClientBlockEntityEventListener) te).onNetworkEvent(player, event);
                        }
                    }
                });
                return;

            case RequestGUI:
                hand = is.readBoolean();
                object1 = hand ? null : DataEncoder.decodeDeferred(is, BlockEntity.class);

                IndustrialCase.platform.requestTick(true, new Runnable() {
                    private IHasGui tryFindGUI(ItemStack stack) {
                        if (!StackUtil.isEmpty(stack) && stack.getItem() instanceof IHandHeldInventory) {
                            return ((IHandHeldInventory) stack.getItem()).getInventory(player, stack);
                        }
                        return null;
                    }

                    public void run() {
                        if (hand) {
                            for (ItemStack stack : player.getHeldEquipment()) {
                                IHasGui gui = tryFindGUI(stack);

                                if (gui != null) {
                                    IndustrialCase.platform.launchGui(player, gui);
                                    break;
                                }
                            }
                        } else {
                            BlockEntity te = DataEncoder.<BlockEntity>getValue(teDeferred);

                            if (te instanceof IHasGui) {
                                IndustrialCase.platform.launchGui(player, (IHasGui) te);
                            }
                        }
                    }
                });
                return;

            case Rpc:
                RpcHandler.processRpcRequest(is, (ServerPlayer) player);
                return;
        }

        onCommonPacketData(packetType, true, is, player);
    }

    protected void onCommonPacketData(SubPacketType packetType, boolean simulating, GrowingBuffer is, final EntityPlayer player) throws IOException {
        final int slot, windowId;
        final Object teDeferred;
        final Item item;
        final String fieldName, event;
        int dataCount;
        final Object value, subData[];
        int i;
        switch (packetType) {
            case PlayerItemData:
                slot = is.readByte();
                item = DataEncoder.<Item>decode(is, Item.class);
                dataCount = is.readVarInt();

                subData = new Object[dataCount];

                for (i = 0; i < dataCount; i++) {
                    subData[i] = DataEncoder.decode(is);
                }

                if (slot >= 0 && slot < 9) {
                    IndustrialCase.platform.requestTick(simulating, new Runnable() {
                        public void run() {
                            for (int i = 0; i < subData.length; i++) {
                                subData[i] = DataEncoder.getValue(subData[i]);
                            }

                            ItemStack stack = (ItemStack) player.inventory.mainInventory.get(slot);

                            if (!StackUtil.isEmpty(stack) && stack.getItem() == item &&
                                    item instanceof IPlayerItemDataListener) {
                                ((IPlayerItemDataListener) item).onPlayerItemNetworkData(player, slot, subData);
                            }
                        }
                    });
                }
                return;

            case ContainerData:
                windowId = is.readInt();
                str1 = is.readString();
                value = DataEncoder.decode(is);

                IndustrialCase.platform.requestTick(simulating, new Runnable() {
                    public void run() {
                        if (player.openContainer instanceof ContainerBase && player.openContainer.windowId == windowId && (NetworkManager.this

                                .isClient() || NetworkManager.this.getClientModifiableField(player.openContainer.getClass(), fieldName) != null)) {
                            ReflectionUtil.setValueRecursive(player.openContainer, fieldName, DataEncoder.getValue(value));
                        }
                    }
                });
                return;

            case ContainerEvent:
                windowId = is.readInt();
                event = is.readString();

                IndustrialCase.platform.requestTick(simulating, new Runnable() {
                    public void run() {
                        if (player.openContainer instanceof ContainerBase && player.openContainer.windowId == windowId) {
                            ((ContainerBase) player.openContainer).onContainerEvent(event);
                        }
                    }
                });
                return;

            case HandHeldInvData:
                windowId = is.readInt();
                fieldName = is.readString();
                value = DataEncoder.decode(is);

                IndustrialCase.platform.requestTick(simulating, new Runnable() {
                    public void run() {
                        if (player.openContainer instanceof ContainerBase && player.openContainer.windowId == windowId) {

                            ContainerBase<?> container = (ContainerBase) player.openContainer;

                            if (container.base instanceof ic2.core.item.tool.HandHeldInventory && (NetworkManager.this
                                    .isClient() || NetworkManager.this.getClientModifiableField(container.base.getClass(), fieldName) != null)) {
                                ReflectionUtil.setValueRecursive(container.base, fieldName, DataEncoder.getValue(value));
                            }
                        }
                    }
                });
                return;


            case BlockEntityData:
                teDeferred = DataEncoder.decodeDeferred(is, BlockEntity.class);
                fieldName = is.readString();
                value = DataEncoder.decode(is);

                IndustrialCase.platform.requestTick(simulating, new Runnable() {
                    public void run() {
                        BlockEntity te = DataEncoder.<BlockEntity>getValue(teDeferred);

                        if (te != null && (NetworkManager.this
                                .isClient() || NetworkManager.this.getClientModifiableField(te.getClass(), fieldName) != null)) {
                            ReflectionUtil.setValueRecursive(te, fieldName, DataEncoder.getValue(value));
                        }
                    }
                });
                return;
        }

        IndustrialCase.log.warn(LogCategory.Network, "Unhandled packet type: %s", packetType.name());
    }

    public void initiateKeyUpdate(int keyState) {
    }


    public void sendLoginData() {
    }

    public final void initiateExplosionEffect(Level world, Vec3 pos, ExplosionIC2.Type type) {
        assert !isClient();

        try {
            GrowingBuffer buffer = new GrowingBuffer(32);

            SubPacketType.ExplosionEffect.writeTo(buffer);

            DataEncoder.encode(buffer, world, false);
            DataEncoder.encode(buffer, pos, false);
            DataEncoder.encode(buffer, type, false);
            buffer.flip();

            for (Object obj : world.players()) {
                if (!(obj instanceof ServerPlayer))
                    continue;
                ServerPlayer player = (ServerPlayer) obj;

                if (player.distanceToSqr(pos.x, pos.y, pos.z) < 128.0D) {
                    sendPacket(buffer, false, player);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected final void sendPacket(GrowingBuffer buffer) {
        if (!isClient()) {
            channel.sendToAll(makePacket(buffer, true));
        } else {
            channel.sendToServer(makePacket(buffer, true));
        }
    }

    protected final void sendPacket(GrowingBuffer buffer, boolean advancePos, ServerPlayer player) {
        assert !isClient();
        channel.sendTo(makePacket(buffer, advancePos), player);
    }

    static <T extends Collection<ServerPlayer>> T getPlayersInRange(Level world, BlockPos pos, T result) {
        if (!(world instanceof WorldServer)) return result;

        PlayerChunkMap playerManager = ((WorldServer) world).getPlayerChunkMap();
        PlayerChunkMapEntry instance = playerManager.getEntry(pos.getX() >> 4, pos.getZ() >> 4);
        if (instance == null) return result;

        result.addAll((Collection) ReflectionUtil.getFieldValue(playerInstancePlayers, instance));

        return result;
    }

    private static Field playerInstancePlayers = ReflectionUtil.getField(PlayerChunkMapEntry.class, List.class);
    private static FMLEventChannel channel;
    private static final int maxPacketDataLength = 32766;
    public static final String channelName = "ic2";

    static void writeFieldData(Object object, String fieldName, GrowingBuffer out) throws IOException {
        int pos = fieldName.indexOf('=');

        if (pos != -1) {
            out.writeString(fieldName.substring(0, pos));
            DataEncoder.encode(out, fieldName.substring(pos + 1));
        } else {
            out.writeString(fieldName);

            try {
                DataEncoder.encode(out, ReflectionUtil.getValueRecursive(object, fieldName));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Can't find field " + fieldName + " in " + object.getClass().getName(), e);
            }
        }
    }

    private static FMLProxyPacket makePacket(GrowingBuffer buffer, boolean advancePos) {
        return new FMLProxyPacket(new PacketBuffer(buffer.toByteBuf(advancePos)), "ic2");
    }
}
