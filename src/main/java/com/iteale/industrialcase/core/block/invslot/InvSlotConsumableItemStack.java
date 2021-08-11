package com.iteale.industrialcase.core.block.invslot;


import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class InvSlotConsumableItemStack extends InvSlotConsumable {
  private final Set<ItemComparableItemStack> stacks;
  
  public InvSlotConsumableItemStack(IInventorySlotHolder<?> base1, String name1, int count, ItemStack... stacks) {
    this(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.TOP, stacks);
  }
  
  public InvSlotConsumableItemStack(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, ItemStack... stacks) {
    super(base1, name1, access1, count, preferredSide1);

    this.stacks = new HashSet<>();
    for (ItemStack stack : stacks)
      this.stacks.add(new ItemComparableItemStack(stack, true)); 
  }
  
  public boolean accepts(ItemStack stack) {
    return this.stacks.contains(new ItemComparableItemStack(stack, false));
  }
}