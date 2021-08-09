package com.iteale.industrialcase.api.energy.tile;

import net.minecraft.core.Direction;

/**
 * An interface to mark a tile entity as a producer of heat
 * <p>Other tile entities that want to draw heat will call these methods,
 * the source implementor has no obligation to find heat sinks/acceptors itself</p>
 *
 * @author Thunderdark, Chocohead
 */
public interface IHeatSource {
	/** @deprecated Use {@link #getConnectionBandwidth(Direction)} */
	@Deprecated
	int  maxrequestHeatTick(Direction directionFrom);

	/**
	 * Gets the maximum heat that can ever be expected to be taken from the given side
	 *
	 * @param side The side that the heat would be pulled from
	 * @return The theoretical maximum heat that could be taken from the given side
	 */
	default int getConnectionBandwidth(Direction side) {
		return maxrequestHeatTick(side);
	}

	/** @deprecated Use {@link #drawHeat(Direction, int, boolean)} */
	@Deprecated
	int requestHeat(Direction directionFrom, int requestheat);

	/**
	 * Try draw/simulate drawing the requested heat out of the given side
	 *
	 * @param side The side to draw the heat from
	 * @param request The amount of heat to draw
	 * @param simulate Whether to actually draw the heat or not
	 *
	 * @return The actual transmitted heat
	 */
	default int drawHeat(Direction side, int request, boolean simulate) {
		return !simulate ? requestHeat(side, request) : maxrequestHeatTick(side);
	}
}
