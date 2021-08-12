package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.block.inventory.InvSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockEntityInventory extends BlockEntity
        implements IInventorySlotHolder<BlockEntityInventory> {
    private final List<InvSlot> invSlots;

    public BlockEntityInventory(BlockEntityType<?> t, BlockPos pos, BlockState state) {
        super(t, pos, state);
        this.invSlots = new ArrayList();
    }

    @Override
    public void load(CompoundTag nbtTagCompound) {
        super.load(nbtTagCompound);
        CompoundTag invSlotsTag = nbtTagCompound.getCompound("InvSlots");
        for (InvSlot invSlot : this.invSlots)
            invSlot.load(invSlotsTag.getCompound(invSlot.name));
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        CompoundTag invSlotsTag = new CompoundTag();
        for (InvSlot invSlot : this.invSlots) {
            CompoundTag invSlotTag = new CompoundTag();
            invSlot.save(invSlotTag);
            invSlotsTag.put(invSlot.name, invSlotTag);
        }
        nbt.put("InvSlots", invSlotsTag);
        return nbt;
    }
}