package com.iteale.industrialcase.api.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Armor items implementing this can be considered metal armor.
 * 
 * Currently used for determining which boots can be used to slide up a magnetic pole.
 */
public interface IMetalArmor {
	/**
	 * Determine if the given armor piece is metal armor.
	 * 
	 * @param itemstack Armor piece as worn by the player
	 * @param player The player
	 * @return Whether the armor piece is metal armor
	 */
	public boolean isMetalArmor(ItemStack itemstack, Player player);
}
