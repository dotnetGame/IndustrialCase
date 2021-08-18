package com.iteale.industrialcase.core.block.invslot;

import com.iteale.industrialcase.core.block.IContainerHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class InvSlotConsumable extends InvSlot {
    public InvSlotConsumable(int sizeIn) {
        super(sizeIn);
    }

    public InvSlotConsumable(IContainerHolder<?> base, String name, int count) {
        super(base, name, InvSlot.Access.I, count, InvSlot.InvSide.TOP);
    }

    public InvSlotConsumable(IContainerHolder<?> base, String name, InvSlot.Access access, int count, InvSlot.InvSide preferredSide) {
        super(base, name, access, count, preferredSide);
    }

    public abstract boolean accepts(ItemStack paramItemStack);

    public ItemStack consume(int amount) {
        return consume(amount, false, false);
    }

    public ItemStack consume(int amount, boolean simulate, boolean consumeContainers) {
        ItemStack ret = null;

        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);

            if (StackUtil.getSize(stack) >= 1 && accepts(stack) &&
                    (ret == null || StackUtil.checkItemEqualityStrict(stack, ret)) && (
                    StackUtil.getSize(stack) == 1 || consumeContainers || !stack.getItem().hasContainerItem(stack))) {
                int currentAmount = Math.min(amount, StackUtil.getSize(stack));

                amount -= currentAmount;

                if (!simulate) {
                    if (StackUtil.getSize(stack) == currentAmount) {
                        if (!consumeContainers && stack.getItem().hasContainerItem(stack)) {
                            ItemStack container = stack.getItem().getContainerItem(stack);

                            if (container != null && container.isDamageableItem() && container.getDamageValue() > container.getMaxDamage()) {
                                container = null;
                            }

                            setItem(i, container);
                        } else {
                            removeItemNoUpdate(i);
                        }
                    } else {
                        setItem(i, StackUtil.decSize(stack, currentAmount));
                    }
                }

                if (ret == null) {
                    ret = new ItemStack(stack.getItem(), currentAmount);
                } else {
                    ret = StackUtil.incSize(ret, currentAmount);
                }

                if (amount == 0)
                    break;
            }
        }
        return ret;
    }

    public int damage(int amount, boolean simulate) {
        return damage(amount, simulate, null);
    }

    public int damage(int amount, boolean simulate, LivingEntity src) {
        return 0;
    }
}
