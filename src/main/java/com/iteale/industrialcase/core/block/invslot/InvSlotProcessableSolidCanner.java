package com.iteale.industrialcase.core.block.invslot;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.machine.tileentity.TileEntitySolidCanner;
import net.minecraft.item.ItemStack;

public class InvSlotProcessableSolidCanner
  extends InvSlotProcessable<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput> {
  public InvSlotProcessableSolidCanner(TileEntitySolidCanner base1, String name1, int count) {
    super((IInventorySlotHolder<?>)base1, name1, count, (IMachineRecipeManager<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput>)Recipes.cannerBottle);
  }

  
  protected ICannerBottleRecipeManager.RawInput getInput(ItemStack stack) {
    return new ICannerBottleRecipeManager.RawInput(((TileEntitySolidCanner)this.base).canInputSlot.get(), stack);
  }

  
  protected void setInput(ICannerBottleRecipeManager.RawInput input) {
    ((TileEntitySolidCanner)this.base).canInputSlot.put(input.container);
    put(input.fill);
  }
}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\core\block\invslot\InvSlotProcessableSolidCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */