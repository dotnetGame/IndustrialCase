package com.iteale.industrialcase.core.block.invslot;


import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import net.minecraft.world.level.material.Fluid;

public class InvSlotConsumableLiquidByManager extends InvSlotConsumableLiquid {
  private final ILiquidAcceptManager manager;
  
  public InvSlotConsumableLiquidByManager(IInventorySlotHolder<?> base1, String name1, int count, ILiquidAcceptManager manager1) {
    super(base1, name1, count);
    this.manager = manager1;
  }
  
  public InvSlotConsumableLiquidByManager(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, InvSlotConsumableLiquid.OpType opType, ILiquidAcceptManager manager1) {
    super(base1, name1, access1, count, preferredSide1, opType);
    this.manager = manager1;
  }

  
  protected boolean acceptsLiquid(Fluid fluid) {
    return this.manager.acceptsFluid(fluid);
  }

  
  protected Iterable<Fluid> getPossibleFluids() {
    return this.manager.getAcceptedFluids();
  }
}
