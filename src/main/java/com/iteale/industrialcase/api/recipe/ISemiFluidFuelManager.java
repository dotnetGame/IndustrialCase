package com.iteale.industrialcase.api.recipe;

import net.minecraft.world.level.material.Fluid;

import java.util.Map;

public interface ISemiFluidFuelManager extends ILiquidAcceptManager {
	/**
	 * Add a new fluid to the semi fluid generator.
	 *
	 * @param fluidName the fluid to burn
	 * @param energyPerMb amount of energy produced by 1 mb of fluid
	 * @param energyPerTick amount of energy generated per tick
	 */
	void addFluid(String fluidName, long energyPerMb, long energyPerTick);

	void removeFluid(String fluidName);

	FuelProperty getFuelProperty(Fluid fluid);

	Map<String, FuelProperty> getFuelProperties();

	final class FuelProperty {
		public FuelProperty(long energyPerMb, long energyPerTick) {
			this.energyPerMb = energyPerMb;
			this.energyPerTick = energyPerTick;
		}

		public final long energyPerMb;
		public final long energyPerTick;
	}
}
