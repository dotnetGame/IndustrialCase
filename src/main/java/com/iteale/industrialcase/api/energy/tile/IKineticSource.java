package com.iteale.industrialcase.api.energy.tile;


import net.minecraft.core.Direction;

/**
 * An interface to mark a tile entity as a producer of kinetic energy
 * <p>Other tile entities that want to draw kinetic energy will call these methods,
 * the source implementor has no obligation to find kinetic sinks/acceptors itself</p>
 *
 * @author Thunderdark, Chocohead
 */
public interface IKineticSource {
	/** @deprecated Use {@link #getConnectionBandwidth(Direction)} */
	@Deprecated
	int  maxrequestkineticenergyTick(Direction directionFrom);

	/**
	 * Gets the maximum kinetic energy that can ever be expected to be taken from the given side
	 *
	 * @param side The side that the energy would be pulled from
	 * @return The theoretical maximum kinetic energy that could be taken from the given side
	 */
	default int getConnectionBandwidth(Direction side) {
		return maxrequestkineticenergyTick(side);
	}

	/** @deprecated Use {@link #drawKineticEnergy(Direction, int, boolean)} */
	@Deprecated
	int requestkineticenergy(Direction directionFrom, int requestkineticenergy);

	/**
	 * Try draw/simulate drawing the requested kinetic energy out of the given side
	 *
	 * @param side The side to draw the energy from
	 * @param request The amount of kinetic energy to draw
	 * @param simulate Whether to actually draw the energy or not
	 *
	 * @return The actual transmitted kinetic energy
	 */
	default int drawKineticEnergy(Direction side, int request, boolean simulate) {
		return !simulate ? requestkineticenergy(side, request) : maxrequestkineticenergyTick(side);
	}
}
