package com.iteale.industrialcase.api.item;

import net.minecraft.world.item.ItemStack;

/**
 * This interface specifies a manager applicable to items not implementing IElectricItem.
 *
 * The manager implementing this has to be registered with ElectricItem.registerBackupManager().
 */
public interface IBackupElectricItemManager extends IElectricItemManager {
	boolean handles(ItemStack stack);
}
