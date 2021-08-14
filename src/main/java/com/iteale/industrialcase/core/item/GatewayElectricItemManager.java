package com.iteale.industrialcase.core.item;


import com.iteale.industrialcase.api.item.ElectricItem;
import com.iteale.industrialcase.api.item.IElectricItem;
import com.iteale.industrialcase.api.item.IElectricItemManager;
import com.iteale.industrialcase.api.item.ISpecialElectricItem;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GatewayElectricItemManager
        implements IElectricItemManager
{
    public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        if (StackUtil.isEmpty(stack)) return 0.0D;

        IElectricItemManager manager = getManager(stack);
        if (manager == null) return 0.0D;

        return manager.charge(stack, amount, tier, ignoreTransferLimit, simulate);
    }


    public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        if (StackUtil.isEmpty(stack)) return 0.0D;

        IElectricItemManager manager = getManager(stack);
        if (manager == null) return 0.0D;

        return manager.discharge(stack, amount, tier, ignoreTransferLimit, externally, simulate);
    }


    public double getCharge(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) return 0.0D;

        IElectricItemManager manager = getManager(stack);
        if (manager == null) return 0.0D;

        return manager.getCharge(stack);
    }


    public double getMaxCharge(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) return 0.0D;

        IElectricItemManager manager = getManager(stack);
        if (manager == null) return 0.0D;

        return manager.getMaxCharge(stack);
    }


    public boolean canUse(ItemStack stack, double amount) {
        if (StackUtil.isEmpty(stack)) return false;

        IElectricItemManager manager = getManager(stack);
        if (manager == null) return false;

        return manager.canUse(stack, amount);
    }


    public boolean use(ItemStack stack, double amount, LivingEntity entity) {
        if (StackUtil.isEmpty(stack)) return false;

        if (entity instanceof Player && ((Player)entity).isCreative())
        {
            return canUse(stack, amount);
        }

        IElectricItemManager manager = getManager(stack);
        if (manager == null) return false;

        return manager.use(stack, amount, entity);
    }


    public void chargeFromArmor(ItemStack stack, LivingEntity entity) {
        if (StackUtil.isEmpty(stack))
            return;  if (entity == null)
            return;
        IElectricItemManager manager = getManager(stack);
        if (manager == null)
            return;
        manager.chargeFromArmor(stack, entity);
    }


    public String getToolTip(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) return null;

        IElectricItemManager manager = getManager(stack);
        if (manager == null) return null;

        return manager.getToolTip(stack);
    }

    private IElectricItemManager getManager(ItemStack stack) {
        Item item = stack.getItem();
        if (item == null) return null;

        if (item instanceof ISpecialElectricItem)
            return ((ISpecialElectricItem)item).getManager(stack);
        if (item instanceof IElectricItem) {
            return ElectricItem.rawManager;
        }
        return ElectricItem.getBackupManager(stack);
    }



    public int getTier(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) return 0;

        IElectricItemManager manager = getManager(stack);
        if (manager == null) return 0;

        return manager.getTier(stack);
    }
}
