package com.iteale.industrialcase.core.item;


import com.iteale.industrialcase.api.item.ElectricItem;
import com.iteale.industrialcase.api.item.IElectricItem;
import com.iteale.industrialcase.api.item.IItemHudInfo;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.util.LogCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseElectricItem extends ItemIC
        implements IPseudoDamageItem, IElectricItem, IItemHudInfo
{
    public BaseElectricItem(Properties properties, double maxCharge, double transferLimit, int tier) {
        super(properties);

        this.maxCharge = maxCharge;
        this.transferLimit = transferLimit;
        this.tier = tier;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack stack) {
        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return this.transferLimit;
    }

    @Override
    public List<String> getHudInfo(ItemStack stack, boolean advanced) {
        List<String> info = new LinkedList<>();
        info.add(ElectricItem.manager.getToolTip(stack));
        return info;
    }

    @Override
    public void setStackDamage(ItemStack stack, int damage) {
        super.setDamage(stack, damage);
    }

    // public static final boolean logIncorrectItemDamaging = ConfigUtil.getBool(MainConfig.get(), "debug/logIncorrectItemDamaging");
    protected final double maxCharge;
    protected final double transferLimit;
    protected final int tier;
}