package com.iteale.industrialcase.core.item;


import com.iteale.industrialcase.api.item.ICustomDamageItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DamageHandler {
    public static int getDamage(ItemStack stack) {
        Item item = stack.getItem();
        if (item == null) return 0;

        if (item instanceof ICustomDamageItem) {
            return ((ICustomDamageItem)item).getCustomDamage(stack);
        }
        return stack.getDamageValue();
    }


    public static void setDamage(ItemStack stack, int damage, boolean displayOnly) {
        Item item = stack.getItem();
        if (item == null)
            return;
        if (item instanceof ICustomDamageItem) {
            ((ICustomDamageItem)item).setCustomDamage(stack, damage);
        } else if (item instanceof IPseudoDamageItem) {
            if (!displayOnly) throw new IllegalStateException("can't damage " + stack + " physically");

            ((IPseudoDamageItem)item).setStackDamage(stack, damage);
        } else {
            stack.setDamageValue(damage);
        }
    }

    public static int getMaxDamage(ItemStack stack) {
        Item item = stack.getItem();
        if (item == null) return 0;

        if (item instanceof ICustomDamageItem) {
            return ((ICustomDamageItem)item).getMaxCustomDamage(stack);
        }
        return stack.getMaxDamage();
    }


    public static boolean damage(ItemStack stack, int damage, LivingEntity src) {
        Item item = stack.getItem();
        if (item == null) return false;

        if (item instanceof ICustomDamageItem)
            return ((ICustomDamageItem)item).applyCustomDamage(stack, damage, src);
        if (src != null) {
            stack.setDamageValue(damage);
            return true;
        }
        // return stack.attemptDamageItem(damage, IC2.random, (src instanceof EntityPlayerMP) ? (EntityPlayerMP)src : null);
        // FIXME
        stack.setDamageValue(damage);
        return true;
    }
}