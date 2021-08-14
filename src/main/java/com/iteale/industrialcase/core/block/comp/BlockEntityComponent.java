package com.iteale.industrialcase.core.block.comp;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;

import java.io.DataInput;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public abstract class BlockEntityComponent {
    protected final BlockEntityBase parent;

    public BlockEntityComponent(BlockEntityBase parent) {
        this.parent = parent;
    }

    public BlockEntityBase getParent() {
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
    }
    public void onNeighborChange(Block srcBlock, BlockPos srcPos) {}
    public void onContainerUpdate(ServerPlayer player) {}
    public void onNetworkUpdate(DataInput is) throws IOException {}
    public void onWorldTick() {}
    /*
    protected void setNetworkUpdate(ServerPlayer player, FriendlyByteBuf data) {
        (IndustrialCase.network.get(true)).sendComponentUpdate(this.parent, Components.getId((Class)getClass()), player, data);
    }
    */

    public Collection<? extends Capability<?>> getProvidedCapabilities(Direction side) {
        return Collections.emptySet();
    }

    public <T> T getCapability(Capability<T> cap, Direction side) {
        return null;
    }
}