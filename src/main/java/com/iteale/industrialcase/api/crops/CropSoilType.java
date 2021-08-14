package com.iteale.industrialcase.api.crops;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;

/**
 * List of possible blocks to be used as soil for a crop.
 * @author estebes
 */
public enum CropSoilType {
	//DIRT(Blocks.DIRT),
	FARMLAND(Blocks.FARMLAND),
	MYCELIUM(Blocks.MYCELIUM),
	SAND(Blocks.SAND),
	SOULSAND(Blocks.SOUL_SAND);

	private CropSoilType(@Nonnull Block block) {
		this.block = block;
	}

	public Block getBlock() {
		return this.block;
	}

	/**
	 * Check if a block can be used as soil.
	 * @param block the block
	 * @return true if the block is a possible crop soil
	 */
	public static boolean contais(Block block) {
		for (CropSoilType aux : CropSoilType.values()) {
			if (aux.getBlock() == block) {
				return true;
			}
		}
		return false;
	}

	private final Block block;
}
