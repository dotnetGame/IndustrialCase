package com.iteale.industrialcase.core.block.invslot;

import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class InvSlotConsumable
  extends InvSlot {
  public InvSlotConsumable(IInventorySlotHolder<?> base, String name, int count) {
    super(base, name, InvSlot.Access.I, count, InvSlot.InvSide.TOP);
  }
  
  public InvSlotConsumable(IInventorySlotHolder<?> base, String name, InvSlot.Access access, int count, InvSlot.InvSide preferredSide) {
    super(base, name, access, count, preferredSide);
  }

  public abstract boolean accepts(ItemStack paramItemStack);

  
  public boolean canOutput() {
    return (super.canOutput() || (this.access != InvSlot.Access.NONE && 
      !isEmpty() && !accepts(get())));
  }

  public ItemStack consume(int amount) {
    return consume(amount, false, false);
  }

  public ItemStack consume(int amount, boolean simulate, boolean consumeContainers) {
    ItemStack ret = null;
    
    for (int i = 0; i < size(); i++) {
      ItemStack stack = get(i);
      
      if (StackUtil.getSize(stack) >= 1 &&
        accepts(stack) && (ret == null || 
        StackUtil.checkItemEqualityStrict(stack, ret)) && (
        StackUtil.getSize(stack) == 1 || consumeContainers || !stack.getItem().hasContainerItem(stack))) {
        int currentAmount = Math.min(amount, StackUtil.getSize(stack));
        
        amount -= currentAmount;
        
        if (!simulate) {
          if (StackUtil.getSize(stack) == currentAmount) {
            if (!consumeContainers && stack.getItem().hasContainerItem(stack)) {
              ItemStack container = stack.getItem().getContainerItem(stack);
              
              if (container != null && container.isDamageableItem() && DamageHandler.getDamage(container) > DamageHandler.getMaxDamage(container)) {
                container = null;
              }
              
              put(i, container);
            } else {
              clear(i);
            } 
          } else {
            put(i, StackUtil.decSize(stack, currentAmount));
          } 
        }
        
        if (ret == null) {
          ret = StackUtil.copyWithSize(stack, currentAmount);
        } else {
          ret = StackUtil.incSize(ret, currentAmount);
        } 
        
        if (amount == 0)
          break; 
      } 
    } 
    return ret;
  }
  
  public int damage(int amount, boolean simulate) {
    return damage(amount, simulate, null);
  }

  public int damage(int amount, boolean simulate, LivingEntity src) {
    int damageApplied = 0;
    ItemStack target = null;
    
    for (int i = 0; i < size() && amount > 0; i++) {
      ItemStack stack = get(i);
      if (!StackUtil.isEmpty(stack)) {
        
        Item item = stack.getItem();
        
        if (accepts(stack) && item.isDamageable() && (target == null || (item == target
          .getItem() && ItemStack.areItemStackTagsEqual(stack, target)))) {
          if (target == null) target = stack.copy(); 
          if (simulate) stack = stack.copy();
          
          int maxDamage = DamageHandler.getMaxDamage(stack);
          
          do {
            int currentAmount = Math.min(amount, maxDamage - DamageHandler.getDamage(stack));
            
            DamageHandler.damage(stack, currentAmount, src);
            
            damageApplied += currentAmount;
            amount -= currentAmount;

            
            if (DamageHandler.getDamage(stack) < maxDamage)
              continue;  stack = StackUtil.decSize(stack);
            if (!StackUtil.isEmpty(stack)) {
              DamageHandler.setDamage(stack, 0, false);
            } else {
              
              break;
            } 
          } while (amount > 0 && !StackUtil.isEmpty(stack));
          
          if (!simulate) {
            put(i, stack);
          }
        } 
      } 
    } 
    return damageApplied;
  }
}