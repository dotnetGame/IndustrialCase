package com.iteale.industrialcase.core.util;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StrictItemComparableItemStack
{
  private final Item item;
  private final int meta;
  private final CompoundTag nbt;
  private final int hashCode;
  
  public StrictItemComparableItemStack(ItemStack stack, boolean copyNbt) {
    this.item = stack.getItem();
    // FIXME
    // this.meta = StackUtil.getRawMeta(stack);
    this.meta = 0;

    CompoundTag nbt = stack.getTag();
    
    if (nbt != null) {
      if (nbt.isEmpty()) {
        nbt = null;
      } else if (copyNbt) {
        nbt = nbt.copy();
      } 
    }
    
    this.nbt = nbt;
    this.hashCode = calculateHashCode();
  }

  
  public boolean equals(Object obj) {
    if (!(obj instanceof StrictItemComparableItemStack)) return false;
    
    StrictItemComparableItemStack cmp = (StrictItemComparableItemStack)obj;
    if (cmp.hashCode != this.hashCode) return false; 
    if (cmp == this) return true;
    
    return (cmp.item == this.item && cmp.meta == this.meta && ((cmp.nbt == null && this.nbt == null) || (cmp.nbt != null && this.nbt != null && cmp.nbt
      
      .equals(this.nbt))));
  }

  
  public int hashCode() {
    return this.hashCode;
  }
  
  private int calculateHashCode() {
    int ret = 0;
    if (this.item != null) ret = System.identityHashCode(this.item);
    
    ret = ret * 31 + this.meta;
    
    if (this.nbt != null) {
      ret = ret * 61 + this.nbt.hashCode();
    }
    
    return ret;
  }
}
