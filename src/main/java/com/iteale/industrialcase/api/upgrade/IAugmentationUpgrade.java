package com.iteale.industrialcase.api.upgrade;

import net.minecraft.world.item.ItemStack;

/**
 * An interface to mark an item as an {@link UpgradableProperty#Augmentable} type upgrade
 *
 * @author Player, Chocohead
 */
public interface IAugmentationUpgrade extends IUpgradeItem {
	/**
	 * Gets the augmentation of the upgrade on the given {@link IUpgradableBlock}
	 *
	 * @param stack The upgrade stack
	 * @param parent The block being augmented
	 *
	 * @return The augmentation applied
	 */
	int getAugmentation(ItemStack stack, IUpgradableBlock parent);
}