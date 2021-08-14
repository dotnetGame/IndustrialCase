package com.iteale.industrialcase.core.item;


import com.iteale.industrialcase.api.item.ElectricItem;
import com.iteale.industrialcase.api.item.IElectricItem;
import com.iteale.industrialcase.api.item.IElectricItemManager;
import com.iteale.industrialcase.core.slot.ArmorSlot;
import com.iteale.industrialcase.core.util.StackUtil;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ElectricItemManager
        implements IElectricItemManager
{
    public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        IElectricItem item = (IElectricItem)stack.getItem();

        assert item.getMaxCharge(stack) > 0.0D;

        if (amount < 0.0D || StackUtil.getSize(stack) > 1 || item.getTier(stack) > tier) return 0.0D;

        if (!ignoreTransferLimit && amount > item.getTransferLimit(stack)) amount = item.getTransferLimit(stack);

        CompoundTag tNBT = StackUtil.getOrCreateNbtData(stack);
        double newCharge = tNBT.getDouble("charge");

        amount = Math.min(amount, item.getMaxCharge(stack) - newCharge);

        if (!simulate) {
            newCharge += amount;

            if (newCharge > 0.0D) {
                tNBT.putDouble("charge", newCharge);
            } else {
                tNBT.remove("charge");
                if (tNBT.isEmpty()) stack.setTag(null);

            }
            if (stack.getItem() instanceof IElectricItem) {
                item = (IElectricItem)stack.getItem();
                int maxDamage = DamageHandler.getMaxDamage(stack);

                DamageHandler.setDamage(stack, mapChargeLevelToDamage(newCharge, item.getMaxCharge(stack), maxDamage), true);
            } else {
                DamageHandler.setDamage(stack, 0, true);
            }
        }

        return amount;
    }

    private static int mapChargeLevelToDamage(double charge, double maxCharge, int maxDamage) {
        if (maxDamage < 2) return 0;

        maxDamage--;

        return maxDamage - (int) Util.map(charge, maxCharge, maxDamage);
    }


    public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        IElectricItem item = (IElectricItem)stack.getItem();

        assert item.getMaxCharge(stack) > 0.0D;

        if (amount < 0.0D || StackUtil.getSize(stack) > 1 || item.getTier(stack) > tier) return 0.0D;
        if (externally && !item.canProvideEnergy(stack)) return 0.0D;

        if (!ignoreTransferLimit && amount > item.getTransferLimit(stack)) amount = item.getTransferLimit(stack);

        CompoundTag tNBT = StackUtil.getOrCreateNbtData(stack);
        double newCharge = tNBT.getDouble("charge");

        amount = Math.min(amount, newCharge);

        if (!simulate) {
            newCharge -= amount;

            if (newCharge > 0.0D) {
                tNBT.putDouble("charge", newCharge);
            } else {
                tNBT.remove("charge");
                if (tNBT.isEmpty()) stack.setTag(null);

            }
            if (stack.getItem() instanceof IElectricItem) {
                item = (IElectricItem)stack.getItem();
                int maxDamage = DamageHandler.getMaxDamage(stack);

                DamageHandler.setDamage(stack, mapChargeLevelToDamage(newCharge, item.getMaxCharge(stack), maxDamage), true);
            } else {
                DamageHandler.setDamage(stack, 0, true);
            }
        }

        return amount;
    }


    public double getCharge(ItemStack stack) {
        return ElectricItem.manager.discharge(stack, Double.POSITIVE_INFINITY, 2147483647, true, false, true);
    }


    public double getMaxCharge(ItemStack stack) {
        return ElectricItem.manager.getCharge(stack) + ElectricItem.manager
                .charge(stack, Double.POSITIVE_INFINITY, 2147483647, true, true);
    }


    public boolean canUse(ItemStack stack, double amount) {
        return (ElectricItem.manager.getCharge(stack) >= amount);
    }


    public boolean use(ItemStack stack, double amount, LivingEntity entity) {
        if (entity != null) ElectricItem.manager.chargeFromArmor(stack, entity);

        double transfer = ElectricItem.manager.discharge(stack, amount, 2147483647, true, false, true);

        if (Util.isSimilar(transfer, amount)) {
            ElectricItem.manager.discharge(stack, amount, 2147483647, true, false, false);
            if (entity != null) ElectricItem.manager.chargeFromArmor(stack, entity);

            return true;
        }
        return false;
    }



    public void chargeFromArmor(ItemStack target, LivingEntity entity) {
        boolean transferred = false;

        for (EquipmentSlot slot : ArmorSlot.getAll()) {
            int tier; ItemStack source = entity.getItemBySlot(slot);
            if (source == null) {
                continue;
            }

            if (source.getItem() instanceof IElectricItem) {
                tier = ((IElectricItem)source.getItem()).getTier(target);
            } else {
                tier = Integer.MAX_VALUE;
            }


            double transfer = ElectricItem.manager.discharge(source, Double.POSITIVE_INFINITY, 2147483647, true, true, true);
            if (transfer <= 0.0D) {
                continue;
            }
            transfer = ElectricItem.manager.charge(target, transfer, tier, true, false);
            if (transfer <= 0.0D) {
                continue;
            }
            ElectricItem.manager.discharge(source, transfer, 2147483647, true, true, false);
            transferred = true;
        }

        if (transferred && entity instanceof Player) {
            ((Player)entity).getInventory().setChanged();
        }
    }


    public String getToolTip(ItemStack stack) {
        double charge = ElectricItem.manager.getCharge(stack);
        double space = ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, 2147483647, true, true);

        return Util.toSiString(charge, 3) + "/" + Util.toSiString(charge + space, 3) + " EU";
    }

    public static ItemStack getCharged(Item item, double charge) {
        if (!(item instanceof IElectricItem)) throw new IllegalArgumentException("no electric item");

        ItemStack ret = new ItemStack(item);
        ElectricItem.manager.charge(ret, charge, 2147483647, true, false);

        return ret;
    }

    public static void addChargeVariants(Item item, List<ItemStack> list) {
        list.add(getCharged(item, 0.0D));
        list.add(getCharged(item, Double.POSITIVE_INFINITY));
    }


    public int getTier(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof IElectricItem)) {
            return 0;
        }
        return ((IElectricItem)stack.getItem()).getTier(stack);
    }
}
