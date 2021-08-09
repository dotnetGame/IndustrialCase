package com.iteale.industrialcase.api.item;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IEnhancedOverlayProvider {
	/**
	 * Determine whether an item provides an enhanced overlay or not.
	 *
	 * @param world The world the item is being used in
	 * @param pos The position the item is being used at
	 * @param side The side of the block the item is being used on
	 * @param player The player using the item, might be <code>null</code>
	 * @param stack The {@link ItemStack} of the item being used
	 *
	 * @return Whether the item provides an enhanced overlay or not
	 */
	boolean providesEnhancedOverlay(Level world, BlockPos pos, Direction side, Player player, ItemStack stack);
}
