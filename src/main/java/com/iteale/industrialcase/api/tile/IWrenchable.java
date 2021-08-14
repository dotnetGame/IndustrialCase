package com.iteale.industrialcase.api.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;

/**
 * Allows a block to make use of the wrench's removal and rotation functions.
 */
public interface IWrenchable {
	/**
	 * Get direction the block is facing.
	 *
	 * The direction typically refers to the front/main/functionally dominant side of a block.
	 *
	 * @param world World containing the block.
	 * @param pos The block's current position in the world.
	 * @return Current block facing.
	 */
	Direction getFacing(Level world, BlockPos pos);

	/**
	 * Determine if the block could face towards the specified direction.
	 *
	 * Shouldn't actually rotate block, just suggest whether the block could face the requested direction
	 *
	 * @param world World containing the block.
	 * @param pos The block's current position in the world.
	 * @param newDirection The face to try, see {@link #getFacing(Level, BlockPos)}
	 * @param player Player potentially causing the rotation, may be <code>null</code>
	 *
	 * @return If {@link #setFacing(Level, BlockPos, Direction, Player)} with the same parameters would succeed
	 */
	default boolean canSetFacing(Level world, BlockPos pos, Direction newDirection, Player player) {
		return true;
	}

	/**
	 * Set the block's facing to face towards the specified direction.
	 *
	 * Contrary to {@link Block#rotate(Level, BlockPos, Direction)} the block should
	 * always face the requested direction after successfully processing this method.
	 *
	 * @param world World containing the block.
	 * @param pos The block's current position in the world.
	 * @param newDirection Requested facing, see {@link #getFacing}.
	 * @param player Player causing the action, may be null.
	 * @return true if successful, false otherwise.
	 */
	boolean setFacing(Level world, BlockPos pos, Direction newDirection, Player player);

	/**
	 * Determine if the wrench can be used to remove the block.
	 *
	 * @param world World containing the block.
	 * @param pos The block's current position in the world.
	 * @param player Player causing the action, may be null.
	 * @return true if allowed, false otherwise.
	 */
	boolean wrenchCanRemove(Level world, BlockPos pos, Player player);

	/**
	 * Determine the items the block will drop when the wrenching is successful.
	 *
	 * The ItemStack will be copied before creating the EntityItem.
	 *
	 * @param world World containing the block.
	 * @param pos The block's current position in the world.
	 * @param state The block's block state before removal.
	 * @param te The block's tile entity before removal, if any, may be null.
	 * @param player Player removing the block, may be null.
	 * @param fortune Fortune level for drop calculation.
	 * @return ItemStacks to drop, may be empty.
	 */
	List<ItemStack> getWrenchDrops(Level world, BlockPos pos, BlockBehaviour.BlockStateBase state, BlockEntity te, Player player, int fortune);
}

