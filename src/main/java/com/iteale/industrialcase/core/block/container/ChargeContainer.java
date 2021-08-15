package com.iteale.industrialcase.core.block.container;

import com.iteale.industrialcase.api.energy.tile.IChargingSlot;
import com.iteale.industrialcase.api.item.ElectricItem;
import com.iteale.industrialcase.core.block.IContainerHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

public class ChargeContainer extends ICContainer implements IChargingSlot {
    public int tier;
    public ChargeContainer(IContainerHolder<?> base, int tier) {
        super(base, "charge", ICContainer.Access.IO, 1, ICContainer.InvSide.TOP);

        this.tier = tier;
    }

    public boolean accepts(ItemStack stack) {
        return ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, this.tier, true, true) > 0.0D;
    }

    @Override
    public double charge(double amount) {
        if (amount <= 0.0D) throw new IllegalArgumentException("Amount must be > 0.");

        ItemStack stack = getItem(0);
        if (StackUtil.isEmpty(stack)) return 0.0D;

        return ElectricItem.manager.charge(stack, amount, this.tier, false, false);
    }

    public void setTier(int tier1) {
        this.tier = tier1;
    }
}
