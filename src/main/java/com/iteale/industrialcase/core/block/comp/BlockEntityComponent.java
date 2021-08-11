package com.iteale.industrialcase.core.block.comp;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.io.DataInput;
import java.io.IOException;

public abstract class BlockEntityComponent {
    protected final BlockEntity parent;

    public BlockEntityComponent(BlockEntity parent) {
        this.parent = parent;
    }

    public BlockEntity getParent() {
        return this.parent;
    }
    public void load(CompoundTag nbt) {}

    public CompoundTag save() {
        return null;
    }

    public void onLoaded() {}


    public void onUnloaded() {}

    public boolean enableWorldTick() {
        return false;
    } public void onNeighborChange(Block srcBlock, BlockPos srcPos) {} public void onContainerUpdate(EntityPlayerMP player) {}
    public void onNetworkUpdate(DataInput is) throws IOException {}
    public void onWorldTick() {}
    protected void setNetworkUpdate(ServerPlayer player, GrowingBuffer data) {
        ((NetworkManager)IC2.network.get(true)).sendComponentUpdate(this.parent, Components.getId((Class)getClass()), player, data);
    }

    public Collection<? extends Capability<?>> getProvidedCapabilities(Direction side) {
        return Collections.emptySet();
    }

    public <T> T getCapability(Capability<T> cap, Direction side) {
        return null;
    }
}
