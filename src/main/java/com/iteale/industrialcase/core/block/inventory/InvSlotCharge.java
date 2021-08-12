package com.iteale.industrialcase.core.block.inventory;

import com.iteale.industrialcase.api.energy.tile.IChargingSlot;
import com.iteale.industrialcase.api.item.ElectricItem;
import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

public class InvSlotCharge extends InvSlot implements IChargingSlot {
    public int tier;

    public InvSlotCharge(IInventorySlotHolder<?> base1, int tier) {
        super(base1, "charge", InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);

        this.tier = tier;
    }


    public boolean accepts(ItemStack stack) {
        return (ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, this.tier, true, true) > 0.0D);
    }

    public double charge(double amount) {
        if (amount <= 0.0D) throw new IllegalArgumentException("Amount must be > 0.");

        ItemStack stack = get(0);
        if (StackUtil.isEmpty(stack)) return 0.0D;

        return ElectricItem.manager.charge(stack, amount, this.tier, false, false);
    }

    public void setTier(int tier1) {
        this.tier = tier1;
    }
}
