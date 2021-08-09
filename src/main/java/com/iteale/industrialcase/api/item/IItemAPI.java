package com.iteale.industrialcase.api.item;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A simple interface for the ItemAPI.
 * @author Aroma1997
 *
 */
public interface IItemAPI {

	/**
	 * Get the <b>default</b> blockstate for a specific block name and variant.<br/>
	 * For machines etc, you might want to make your detection a bit more fuzzy,
	 * because the BlockState also contains properties like the facing and the active
	 * state of a machine.
	 * @param name The name of the block.
	 * @param variant The variant of the block.
	 * @return The default state of the block with that variant.
	 */
	BlockBehaviour.BlockStateBase getBlockState(String name, String variant);

	/**
	 * Get an ItemStack for a specific item name.
	 *
	 * @param name
	 *            item name
	 * @param variant
	 *            the variant/subtype for the Item.
	 * @return The item or null if the item does not exist or an error occurred
	 */
	ItemStack getItemStack(String name, String variant);

	/**
	 * Get a Block for a specific block name.
	 *
	 * @param name
	 *            block name
	 * @return The Block or null if the block does not exist or an error occurred
	 */
	Block getBlock(String name);

	/**
	 * Get an Item for a specific block name.
	 *
	 * @param name
	 *            item name
	 * @return The Item or null if the block does not exist or an error occurred
	 */
	Item getItem(String name);

}
