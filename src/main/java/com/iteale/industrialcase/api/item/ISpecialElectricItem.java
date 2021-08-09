package com.iteale.industrialcase.api.item;

import net.minecraft.world.item.ItemStack;

public interface ISpecialElectricItem {
	/**
	 * Supply a custom IElectricItemManager.
	 *
	 * @param stack ItemStack to get the manager for
	 * @return IElectricItemManager instance
	 */
	IElectricItemManager getManager(ItemStack stack);
}
