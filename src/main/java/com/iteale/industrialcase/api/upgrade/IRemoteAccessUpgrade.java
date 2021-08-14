package com.iteale.industrialcase.api.upgrade;


import net.minecraft.world.item.ItemStack;

/**
 * An interface to mark an item as a {@link UpgradableProperty#RemotelyAccessible} type upgrade
 *
 * @author Chocohead
 */
public interface IRemoteAccessUpgrade extends IUpgradeItem {
	/**
	 * Compute the change to the range the presence of this upgrade causes in the given {@link IUpgradableBlock}
	 *
	 * @param stack The upgrade {@link ItemStack}
	 * @param parent The {@link IUpgradableBlock} receiving the range change
	 * @param existingRange The existing range the block has before this upgrade is applied
	 *
	 * @return The new range the block should have
	 */
	int getRangeAmplification(ItemStack stack, IUpgradableBlock parent, int existingRange);
}