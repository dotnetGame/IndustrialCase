package com.iteale.industrialcase.core.block.invslot;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.core.block.IInventorySlotHolder;
import java.util.Collection;
import net.minecraft.item.ItemStack;


public class InvSlotProcessableGeneric
  extends InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>
{
  public InvSlotProcessableGeneric(IInventorySlotHolder<?> base, String name, int count, IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> recipeManager) {
    super(base, name, count, recipeManager);
  }

  
  protected ItemStack getInput(ItemStack stack) {
    return stack;
  }

  
  protected void setInput(ItemStack input) {
    put(input);
  }
}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\core\block\invslot\InvSlotProcessableGeneric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */