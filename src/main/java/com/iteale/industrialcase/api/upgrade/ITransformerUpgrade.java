package com.iteale.industrialcase.api.upgrade;

import net.minecraft.world.item.ItemStack;

/**
 * An interface to mark an item as an {@link UpgradableProperty#Transformer} type upgrade
 *
 * @author Player, Chocohead
 */
public interface ITransformerUpgrade extends IUpgradeItem {
	/**
	 * Gets the additional tier that the given {@link IUpgradableBlock} can take
	 *
	 * @param stack The upgrade stack
	 * @param parent The block receiving energy
	 *
	 * @return The value to tier the tier by
	 */
	int getExtraTier(ItemStack stack, IUpgradableBlock parent);
}