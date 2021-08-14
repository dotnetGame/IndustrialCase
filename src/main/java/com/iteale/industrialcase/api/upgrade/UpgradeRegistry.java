package com.iteale.industrialcase.api.upgrade;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The registry that contains all known {@link IUpgradeItem}s
 *
 * @author Player
 */
public class UpgradeRegistry {
	/**
	 * Register an upgrade itemstack to the registry
	 *
	 * @param stack The upgrade to register
	 *
	 * @return The stack that has been registered
	 */
	public static ItemStack register(ItemStack stack) {
		if (!(stack.getItem() instanceof IUpgradeItem)) throw new IllegalArgumentException("The stack must represent an IUpgradeItem.");

		upgrades.add(stack);

		return stack;
	}

	/**
	 * @return An unmodifiable list of registered upgrades
	 */
	public static Iterable<ItemStack> getUpgrades() {
		return Collections.unmodifiableCollection(upgrades);
	}

	private static final List<ItemStack> upgrades = new ArrayList<ItemStack>();
}
