package com.iteale.industrialcase.core.block.invslot;

import ic2.api.recipe.Recipes;
import ic2.core.block.IInventorySlotHolder;
import net.minecraft.item.ItemStack;

public class InvSlotProcessableSmelting
  extends InvSlotProcessable<ItemStack, ItemStack, ItemStack>
{
  public InvSlotProcessableSmelting(IInventorySlotHolder<?> base, String name, int count) {
    super(base, name, count, Recipes.furnace);
  }

  
  protected ItemStack getInput(ItemStack stack) {
    return stack;
  }

  
  protected void setInput(ItemStack input) {
    put(input);
  }
}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\core\block\invslot\InvSlotProcessableSmelting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */