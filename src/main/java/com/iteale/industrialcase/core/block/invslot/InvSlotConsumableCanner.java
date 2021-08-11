package com.iteale.industrialcase.core.block.invslot;


import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableCanner
  extends InvSlotConsumableLiquid {
  public InvSlotConsumableCanner(BlockEntityCanner base1, String name1, int count) {
    super(base1, name1, count);
  }

  
  public boolean accepts(ItemStack stack) {
    switch (this.base.getMode()) {
      case BottleSolid:
        return (Recipes.cannerBottle.apply(new ICannerBottleRecipeManager.RawInput(stack, ((TileEntityCanner)this.base).inputSlot.get()), true) != null);
      case BottleLiquid:
      case EmptyLiquid:
      case EnrichLiquid:
        return super.accepts(stack);
    } 
    assert false;
    return false;
  }
}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\core\block\invslot\InvSlotConsumableCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */