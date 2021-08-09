package com.iteale.industrialcase.api.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Allows an item to act as a terraformer blueprint.
 */
public interface ITerraformingBP {
	/**
	 * Get the energy consumption per operation of the blueprint.
	 * @param stack TODO
	 *
	 * @return Energy consumption in EU
	 */
	double getConsume(ItemStack stack);

	/**
	 * Get the maximum range of the blueprint.
	 * Should be a divisor of 5.
	 * @param stack TODO
	 *
	 * @return Maximum range in blocks
	 */
	int getRange(ItemStack stack);

	boolean canInsert(ItemStack stack, Player player, Level world, BlockPos pos);

	/**
	 * Perform the terraforming operation.
	 * @param stack TODO
	 * @param world world to terraform
	 * @param pos position to terraform
	 *
	 * @return Whether the operation was successful and the terraformer should consume energy.
	 */
	boolean terraform(ItemStack stack, Level world, BlockPos pos);
}
