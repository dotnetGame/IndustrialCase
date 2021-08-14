package com.iteale.industrialcase.api.recipe;

import java.util.Set;

import net.minecraft.world.level.material.Fluid;

public interface ILiquidAcceptManager {
	boolean acceptsFluid(Fluid fluid);
	Set<Fluid> getAcceptedFluids();
}
