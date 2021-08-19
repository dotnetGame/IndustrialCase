package com.iteale.industrialcase.core.slot;


import com.iteale.industrialcase.core.block.invslot.InvSlot;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotInvSlot extends Slot {
    public final InvSlot invSlot;

    public SlotInvSlot(InvSlot invSlot, int index, int x, int y) {
        super(invSlot.base.getParent(), invSlot.base.getBaseIndex(invSlot) + index, x, y);

        this.invSlot = invSlot;
        this.index = index;
    }
    public final int index;

    public boolean isItemValid(ItemStack stack) {
        return this.invSlot.accepts(stack);
    }

    public ItemStack getStack() {
        return this.invSlot.getItem(this.index);
    }


    public void putStack(ItemStack stack) {
        this.invSlot.setItem(this.index, stack);
        super.setChanged();
    }

    public ItemStack decrStackSize(int amount) {
        ItemStack ret;
        if (amount <= 0) return StackUtil.emptyStack;

        ItemStack stack = this.invSlot.getItem(this.index);
        if (StackUtil.isEmpty(stack)) return StackUtil.emptyStack;

        amount = Math.min(amount, StackUtil.getSize(stack));


        if (StackUtil.getSize(stack) == amount) {
            ret = stack;
            this.invSlot.removeItemNoUpdate(this.index);
        } else {
            ret = StackUtil.copyWithSize(stack, amount);
            this.invSlot.setItem(this.index, StackUtil.decSize(stack, amount));
        }

        super.setChanged();

        return ret;
    }


    public boolean isHere(Container inventory, int index) {
        if (inventory != this.invSlot.base) return false;

        int baseIndex = this.invSlot.base.getBaseIndex(this.invSlot);
        if (baseIndex == -1) return false;

        return (baseIndex + this.index == index);
    }


    public int getSlotStackLimit() {
        return this.invSlot.getStackSizeLimit();
    }


    public void onTake(Player player, ItemStack stack) {
        super.onTake(player, stack);

        this.invSlot.onPickupFromSlot(player, stack);
    }
}