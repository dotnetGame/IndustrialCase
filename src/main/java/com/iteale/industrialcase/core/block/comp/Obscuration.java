package com.iteale.industrialcase.core.block.comp;


import com.iteale.industrialcase.api.event.RetextureEvent;
import com.iteale.industrialcase.core.block.BlockEntityBase;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;

public class Obscuration extends BlockEntityComponent {
    private final Runnable changeHandler;
    // private ObscurationData[] dataMap;

    public Obscuration(BlockEntityBase parent, Runnable changeHandler) {
        super(parent);

        this.changeHandler = changeHandler;
    }
    /*
    public void load(CompoundTag nbt)
    {
        if (nbt.isEmpty())
            return;
        for (Direction facing : Direction.values()) {
            if (nbt.contains(facing.getName(), Tag.TAG_COMPOUND)) {

                CompoundTag cNbt = nbt.getCompound(facing.getName());

                Block block = Util.getBlock(cNbt.getString("block"));
                if (block != null) {

                    String variant = cNbt.getString("variant");
                    IBlockState state = BlockStateUtil.getState(block, variant);
                    if (state != null) {

                        int rawSide = cNbt.getByte("side");
                        if (rawSide >= 0 && rawSide < Direction.values().length) {
                            Direction side = Direction.values()[rawSide];

                            int[] colorMultipliers = ItemObscurator.internColorMultipliers(cNbt.getIntArray("colorMuls"));

                            ObscurationData data = new ObscurationData(state, variant, side, colorMultipliers);

                            if (this.dataMap == null) this.dataMap = new ObscurationData[EnumFacing.VALUES.length];

                            this.dataMap[facing.ordinal()] = data.intern();
                        }
                    }
                }
            }
        }
    }

    public CompoundTag save() {
        if (this.dataMap == null) return null;

        CompoundTag ret = new CompoundTag();

        for (Direction facing : Direction.values()) {
            ObscurationData data = this.dataMap[facing.ordinal()];
            if (data != null) {

                CompoundTag nbt = new CompoundTag();

                nbt.putString("block", Util.getName(data.state.getBlock()).toString());
                nbt.putString("variant", data.variant);
                nbt.putByte("side", (byte)data.side.ordinal());
                nbt.putIntArray("colorMuls", data.colorMultipliers);

                ret.put(facing.getName(), nbt);
            }
        }
        return ret;
    }
    public boolean applyObscuration(Direction side, ObscurationData data) {
        if (this.dataMap != null && data.equals(this.dataMap[side.ordinal()])) return false;

        if (this.dataMap == null) this.dataMap = new ObscurationData[Direction.values().length];

        this.dataMap[side.ordinal()] = data.intern();

        this.changeHandler.run();

        return true;
    }

    public void clear() {
        this.dataMap = null;

        this.changeHandler.run();
    }

    public boolean hasObscuration() {
        return (this.dataMap != null);
    }

    public ObscurationData[] getRenderState() {
        if (this.dataMap == null) return null;

        return Arrays.<ObscurationData>copyOf(this.dataMap, this.dataMap.length);
    }

    public static class ObscurationComponentEventHandler {
        public static void init() {
            new ObscurationComponentEventHandler();
        }

        private ObscurationComponentEventHandler() {
            MinecraftForge.EVENT_BUS.register(this);
        }

        @SubscribeEvent
        public void onObscuration(RetextureEvent event) {
            if (event.state.getBlock() != BlockName.te.getInstance())
                return;
            BlockEntity teRaw = event.getWorld().getBlockEntity(event.pos);
            if (!(teRaw instanceof BlockEntityBase))
                return;
            Obscuration obscuration = (Obscuration)((BlockEntityBase)teRaw).getComponent(Obscuration.class);
            if (obscuration == null)
                return;
            Obscuration.ObscurationData data = new Obscuration.ObscurationData(event.refState, event.refVariant, event.refSide, event.refColorMultipliers);

            if (obscuration.applyObscuration(event.side, data)) {
                event.applied = true;
                event.setCanceled(true);
            }
        } }
    public static class ObscurationData {
        public final IBlockState state;
        public final String variant;

        public ObscurationData(IBlockState state, String variant, Direction side, int[] colorMultipliers) {
            this.state = state;
            this.variant = variant;
            this.side = side;
            this.colorMultipliers = colorMultipliers;
        }
        public final Direction side;
        public final int[] colorMultipliers;

        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (!(obj instanceof ObscurationData)) return false;

            ObscurationData o = (ObscurationData)obj;

            return (o.state.equals(this.state) && o.variant
                    .equals(this.variant) && o.side == this.side &&

                    Arrays.equals(o.colorMultipliers, this.colorMultipliers));
        }


        public int hashCode() {
            return (this.state.hashCode() * 7 + this.side.ordinal()) * 23;
        }


        public ObscurationData intern() {
            return this;
        }
    }
     */
}
