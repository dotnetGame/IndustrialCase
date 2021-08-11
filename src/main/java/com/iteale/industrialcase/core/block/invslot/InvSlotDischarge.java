package com.iteale.industrialcase.core.block.invslot;


import com.iteale.industrialcase.api.energy.tile.IDischargingSlot;
import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

public class InvSlotDischarge extends InvSlot implements IDischargingSlot {
  public int tier;
  
  public InvSlotDischarge(IInventorySlotHolder<?> base, InvSlot.Access access, int tier) {
    this(base, access, tier, InvSlot.InvSide.ANY);
  }
  public boolean allowRedstoneDust;
  public InvSlotDischarge(IInventorySlotHolder<?> base, InvSlot.Access access, int tier, InvSlot.InvSide preferredSide) {
    this(base, access, tier, true, preferredSide);
  }
  
  public InvSlotDischarge(IInventorySlotHolder<?> base, InvSlot.Access access, int tier, boolean allowRedstoneDust, InvSlot.InvSide preferredSide) {
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
    ItemStack stack = get(0);
    if (StackUtil.isEmpty(stack))
      return 0.0D; 
    double realAmount = ElectricItem.manager.discharge(stack, amount, this.tier, ignoreLimit, true, false);
    if (realAmount <= 0.0D) {
      realAmount = Info.itemInfo.getEnergyValue(stack);
      if (realAmount <= 0.0D)
        return 0.0D; 
      put(0, StackUtil.decSize(stack));
    } 
    return realAmount;
  }
  
  public void setTier(int tier1) {
    this.tier = tier1;
  }
}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\core\block\invslot\InvSlotDischarge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */