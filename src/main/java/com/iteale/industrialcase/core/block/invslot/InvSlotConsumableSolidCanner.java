package com.iteale.industrialcase.core.block.invslot;


import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableSolidCanner
  extends InvSlotConsumableLiquid {
  public InvSlotConsumableSolidCanner(BlockEntitySolidCanner base1, String name1, int count) {
    super((IInventorySlotHolder<?>)base1, name1, count);
  }

  
  public boolean accepts(ItemStack stack) {
    return (Recipes.cannerBottle.apply(new ICannerBottleRecipeManager.RawInput(stack, ((TileEntitySolidCanner)this.base).inputSlot.get()), true) != null);
  }
}
