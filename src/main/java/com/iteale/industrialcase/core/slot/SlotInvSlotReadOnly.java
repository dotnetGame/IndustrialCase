package com.iteale.industrialcase.core.slot;


import com.iteale.industrialcase.core.block.invslot.InvSlot;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SlotInvSlotReadOnly extends SlotInvSlot {
    public SlotInvSlotReadOnly(InvSlot invSlot, int index, int xDisplayPosition, int yDisplayPosition) {
        super(invSlot, index, xDisplayPosition, yDisplayPosition);
    }


    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    public ItemStack onTake(Player player, ItemStack stack) {
        return stack;
    }

    public boolean canTakeStack(Player player) {
        return false;
    }

    public ItemStack decrStackSize(int par1) {
        return StackUtil.emptyStack;
    }
}

