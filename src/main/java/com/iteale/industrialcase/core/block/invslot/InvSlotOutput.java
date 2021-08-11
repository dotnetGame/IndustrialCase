package com.iteale.industrialcase.core.block.invslot;

import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Collections;

public class InvSlotOutput
  extends InvSlot
{
  public InvSlotOutput(IInventorySlotHolder<?> base1, String name1, int count) {
    this(base1, name1, count, InvSlot.InvSide.BOTTOM);
  }
  
  public InvSlotOutput(IInventorySlotHolder<?> base1, String name1, int count, InvSlot.InvSide side) {
    super(base1, name1, InvSlot.Access.O, count, side);
  }

  
  public boolean accepts(ItemStack stack) {
    return false;
  }

  public int add(Collection<ItemStack> stacks) {
    return add(stacks, false);
  }
  
  public int add(ItemStack stack) {
    if (stack == null) throw new NullPointerException("null ItemStack");
    
    return add(Collections.singletonList(stack), false);
  }
  
  public boolean canAdd(Collection<ItemStack> stacks) {
    return (add(stacks, true) == 0);
  }
  
  public boolean canAdd(ItemStack stack) {
    if (stack == null) throw new NullPointerException("null ItemStack");
    
    return (add(Collections.singletonList(stack), true) == 0);
  }
  
  private int add(Collection<ItemStack> stacks, boolean simulate) {
    if (stacks == null || stacks.isEmpty()) return 0;
    
    ItemStack[] backup = simulate ? backup() : null;
    
    int totalAmount = 0;
    
    for (ItemStack stack : stacks) {
      int amount = StackUtil.getSize(stack);
      if (amount <= 0)
        continue;  int pass;
      label47: for (pass = 0; pass < 2; pass++) {
        for (int i = 0; i < size(); i++) {
          ItemStack existingStack = get(i);
          int space = getStackSizeLimit();
          
          if (!StackUtil.isEmpty(existingStack)) {
            space = Math.min(space, existingStack.getMaxStackSize()) - StackUtil.getSize(existingStack);
          }
          
          if (space > 0)
          {
            if (pass == 0 && !StackUtil.isEmpty(existingStack) && StackUtil.checkItemEqualityStrict(stack, existingStack)) {
              if (space >= amount) {
                put(i, StackUtil.incSize(existingStack, amount));
                amount = 0;
                break label47;
              } 
              put(i, StackUtil.incSize(existingStack, space));
              amount -= space;
            }
            else if (pass == 1 && StackUtil.isEmpty(existingStack)) {
              if (space >= amount) {
                put(i, StackUtil.copyWithSize(stack, amount));
                amount = 0;
                break label47;
              } 
              put(i, StackUtil.copyWithSize(stack, space));
              amount -= space;
            } 
          }
        } 
      } 
      
      totalAmount += amount;
    } 
    
    if (simulate) restore(backup);
    
    return totalAmount;
  }
}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\core\block\invslot\InvSlotOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */