package com.iteale.industrialcase.api.item;

import net.minecraft.world.item.ItemStack;

public interface IBoxable {
	/**
	 * Determine whether an item can be stored in a toolbox or not.
	 *
	 * @param stack item to be stored
	 * @return Whether to store the item in the toolbox or not
	 */
	public abstract boolean canBeStoredInToolbox(ItemStack stack);
}
