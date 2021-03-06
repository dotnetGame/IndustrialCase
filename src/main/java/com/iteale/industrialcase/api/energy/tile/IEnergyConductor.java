package com.iteale.industrialcase.api.energy.tile;

/**
 * Tile entities which conduct energy pulses without buffering (mostly cables) have to implement this
 * interface.
 * 
 * See ic2/api/energy/usage.txt for an overall description of the energy net api.
 */
public interface IEnergyConductor extends IEnergyAcceptor, IEnergyEmitter {
	/**
	 * Energy loss for the conductor in EU per block.
	 * 
	 * @return Energy loss
	 */
	double getConductionLoss();

	/**
	 * Amount of energy the insulation will handle before shocking nearby players and mobs.
	 * 
	 * @return Insulation energy absorption in EU
	 */
	double getInsulationEnergyAbsorption();

	/**
	 * Amount of energy the insulation will handle before it is destroyed.
	 * Ensure that this value is greater than the insulation energy absorption + 64.
	 *
	 * @return Insulation-destroying energy in EU
	 */
	double getInsulationBreakdownEnergy();

	/**
	 * Amount of energy the conductor will handle before it melts.
	 * 
	 * @return Conductor-destroying energy in EU
	 */
	double getConductorBreakdownEnergy();

	/**
	 * Remove the conductor's insulation if the insulation breakdown energy was exceeded.
	 * 
	 * @see #getInsulationBreakdownEnergy()
	 */
	void removeInsulation();

	/**
	 * Remove the conductor if the conductor breakdown energy was exceeded.
	 * 
	 * @see #getConductorBreakdownEnergy()
	 */
	void removeConductor();
}

