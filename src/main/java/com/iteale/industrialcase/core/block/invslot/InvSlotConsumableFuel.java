package com.iteale.industrialcase.core.block.invslot;

import com.iteale.industrialcase.api.info.Info;
import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableFuel extends InvSlotConsumable {
  public final boolean allowLava;
  
  public InvSlotConsumableFuel(IInventorySlotHolder<?> base1, String name1, int count, boolean allowLava1) {
    super(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.SIDE);
    
    this.allowLava = allowLava1;
  }

  
  public boolean accepts(ItemStack stack) {
    return (Info.itemInfo.getFuelValue(stack, this.allowLava) > 0);
  }
  
  public int consumeFuel() {
    ItemStack fuel = consume(1);
    if (fuel == null) return 0;
    
    return Info.itemInfo.getFuelValue(fuel, this.allowLava);
  }
}
