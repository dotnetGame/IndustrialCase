package com.iteale.industrialcase.core.slot;


import com.iteale.industrialcase.core.util.StackUtil;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotHologramSlot extends Slot {
    public SlotHologramSlot(ItemStack[] stacks, int index, int x, int y, int stackSizeLimit, ChangeCallback changeCallback) {
        super(null, 0, x, y);

        if (index >= stacks.length) throw new ArrayIndexOutOfBoundsException(index);

        this.stacks = stacks;
        this.index = index;
        this.stackSizeLimit = stackSizeLimit;
        this.changeCallback = changeCallback;
    }

    protected final ItemStack[] stacks;
    protected final int index;
    protected final int stackSizeLimit;
    protected final ChangeCallback changeCallback;

    public boolean canTakeStack(Player player) {
        return false;
    }


    public int getSlotStackLimit() {
        return this.stackSizeLimit;
    }

    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    public ItemStack getStack() {
        return StackUtil.wrapEmpty(this.stacks[this.index]);
    }


    public void putStack(ItemStack stack) {
        this.stacks[this.index] = stack;
    }


    public void onSlotChanged() {
        if (Util.inDev()) System.out.println(StackUtil.toStringSafe(this.stacks));
        if (this.changeCallback != null) this.changeCallback.onChanged(this.index);

    }

    public ItemStack decrStackSize(int amount) {
        return StackUtil.emptyStack;
    }


    public boolean isHere(Container inventory, int index) {
        return false;
    }

    public ItemStack slotClick(int dragType, ClickType clickType, Player player) {
        if (Util.inDev() && player.level.isClientSide)
            System.out.printf("dragType=%d clickType=%s stack=%s%n", dragType, clickType, player.getInventory().getSelected());

        if (clickType == ClickType.PICKUP && (dragType == 0 || dragType == 1)) {
            ItemStack playerStack = player.getInventory().getSelected();
            ItemStack slotStack = this.stacks[this.index];

            if (!StackUtil.isEmpty(playerStack)) {
                int curSize = StackUtil.getSize(slotStack);
                int extraSize = (dragType == 0) ? StackUtil.getSize(playerStack) : 1;
                int limit = Math.min(playerStack.getMaxStackSize(), this.stackSizeLimit);

                if (curSize + extraSize > limit) extraSize = Math.max(0, limit - curSize);

                if (curSize == 0) {
                    this.stacks[this.index] = StackUtil.copyWithSize(playerStack, extraSize);
                } else if (StackUtil.checkItemEquality(playerStack, slotStack)) {
                    if (Util.inDev()) System.out.println("add " + extraSize + " to " + slotStack + " -> " + (curSize + extraSize));
                    this.stacks[this.index] = StackUtil.incSize(slotStack, extraSize);
                } else {
                    this.stacks[this.index] = StackUtil.copyWithSize(playerStack, Math.min(StackUtil.getSize(playerStack), limit));
                }
            } else if (!StackUtil.isEmpty(slotStack)) {
                if (dragType == 0) {
                    this.stacks[this.index] = StackUtil.emptyStack;
                } else {
                    int newSize = StackUtil.getSize(slotStack) / 2;

                    if (newSize <= 0) {
                        this.stacks[this.index] = StackUtil.emptyStack;
                    } else {
                        this.stacks[this.index] = StackUtil.setSize(slotStack, newSize);
                    }
                }
            }

            onSlotChanged();
        }

        return StackUtil.emptyStack;
    }

    public static interface ChangeCallback {
        void onChanged(int param1Int);
    }
}

