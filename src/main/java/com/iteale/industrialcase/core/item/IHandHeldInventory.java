package com.iteale.industrialcase.core.item;

import com.iteale.industrialcase.core.IHasGui;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IHandHeldInventory {
    IHasGui getInventory(Player paramEntityPlayer, ItemStack paramItemStack);
}