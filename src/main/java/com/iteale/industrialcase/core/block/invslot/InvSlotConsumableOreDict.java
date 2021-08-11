package com.iteale.industrialcase.core.block.invslot;


import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableOreDict extends InvSlotConsumable {
  protected final String oreDict;
  
  public InvSlotConsumableOreDict(IInventorySlotHolder<?> base, String name, int count, String oreDict) {
    super(base, name, count);
    
    this.oreDict = oreDict;
  }
  
  public InvSlotConsumableOreDict(IInventorySlotHolder<?> base, String name, InvSlot.Access access, int count, InvSlot.InvSide side, String oreDict) {
    super(base, name, access, count, side);
    
    this.oreDict = oreDict;
  }

  
  public boolean accepts(ItemStack stack) {
    if (StackUtil.isEmpty(stack)) return false;

    /*
    for (int ID : OreDictionary.getOreIDs(stack)) {
      if (this.oreDict.equals(OreDictionary.getOreName(ID))) {
        return true;
      }
    }
     */
    
    return false;
  }
}
