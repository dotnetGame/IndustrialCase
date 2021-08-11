package com.iteale.industrialcase.core.block.invslot;


import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableClass
  extends InvSlotConsumable
{
  private final Class<?> clazz;
  
  public InvSlotConsumableClass(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, Class<?> clazz) {
    super(base1, name1, access1, count, preferredSide1);
    this.clazz = clazz;
  }
  
  public InvSlotConsumableClass(IInventorySlotHolder<?> base1, String name1, int count, Class<?> clazz) {
    super(base1, name1, count);
    this.clazz = clazz;
  }

  
  public boolean accepts(ItemStack stack) {
    if (StackUtil.isEmpty(stack)) return false;
    if (stack.getItem() instanceof net.minecraft.item.ItemBlock) {
      return this.clazz.isInstance(Block.getBlockFromItem(stack.getItem()));
    }
    
    return this.clazz.isInstance(stack.getItem());
  }
}
