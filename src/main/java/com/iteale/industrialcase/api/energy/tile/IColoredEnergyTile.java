package com.iteale.industrialcase.api.energy.tile;

import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;

/**
 * Interface, that allows Blocks to be colored and have their connectivity based on that color.
 * This allows for any Energy Tile to be colored. (Conductors, Sinks, Sources)
 * @author Aroma1997
 */
public interface IColoredEnergyTile extends IEnergyTile {

	/**
	 * This is to get a Energy Tile's color for the given side. Mainly used for checking connectivity.
	 * @param side The side you want to get the color from.
	 * @return The color of the Energy Tile at the given side or null, if it's uncolored.
	 */
	DyeColor getColor(Direction side);
}
