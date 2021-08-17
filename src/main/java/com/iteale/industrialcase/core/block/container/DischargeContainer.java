package com.iteale.industrialcase.core.block.container;

import com.iteale.industrialcase.api.energy.tile.IDischargingSlot;
import com.iteale.industrialcase.api.info.Info;
import com.iteale.industrialcase.api.item.ElectricItem;
import com.iteale.industrialcase.core.block.IContainerHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DischargeContainer extends ICContainer implements IDischargingSlot {
    public int tier;

    public DischargeContainer(IContainerHolder<?> base, ICContainer.Access access, int tier) {
        this(base, access, tier, ICContainer.InvSide.ANY);
    }
    public boolean allowRedstoneDust;
    public DischargeContainer(IContainerHolder<?> base, ICContainer.Access access, int tier, ICContainer.InvSide preferredSide) {
        this(base, access, tier, true, preferredSide);
    }

    public DischargeContainer(IContainerHolder<?> base, ICContainer.Access access, int tier, boolean allowRedstoneDust, ICContainer.InvSide preferredSide) {
        super(base, "discharge", access, 1, preferredSide);
        this.allowRedstoneDust = true;
        this.tier = tier;
        this.allowRedstoneDust = allowRedstoneDust;
    }

    public boolean accepts(ItemStack stack) {
        if (stack == null)
            return false;
        if (stack.getItem() == Items.REDSTONE && !this.allowRedstoneDust)
            return false;
        return (Info.itemInfo.getEnergyValue(stack) > 0.0D || ElectricItem.manager.discharge(stack, Double.POSITIVE_INFINITY, this.tier, true, true, true) > 0.0D);
    }

    public double discharge(double amount, boolean ignoreLimit) {
        if (amount <= 0.0D)
            throw new IllegalArgumentException("Amount must be > 0.");
        ItemStack stack = getItem(0);
        if (StackUtil.isEmpty(stack))
            return 0.0D;
        double realAmount = ElectricItem.manager.discharge(stack, amount, this.tier, ignoreLimit, true, false);
        if (realAmount <= 0.0D) {
            realAmount = Info.itemInfo.getEnergyValue(stack);
            if (realAmount <= 0.0D)
                return 0.0D;
            setItem(0, StackUtil.decSize(stack));
        }
        return realAmount;
    }

    public void setTier(int tier1) {
        this.tier = tier1;
    }
}

