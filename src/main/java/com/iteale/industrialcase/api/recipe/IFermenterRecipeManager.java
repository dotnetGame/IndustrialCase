package com.iteale.industrialcase.api.recipe;

import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Fermenter recipe manager, very closely based off of {@link ISemiFluidFuelManager}
 *
 * @author Chocohead
 */
public interface IFermenterRecipeManager extends ILiquidAcceptManager {
	/**
	 * Add a new recipe to the fermenter.
	 *
	 * @param input The name of the fluid to ferment
	 * @param inputAmount The amount of input fluid used per operation (in millibuckets)
	 * @param heat The total amount of heat needed to convert the input into the output
	 * @param output The name of the fluid that is being fermented into
	 * @param outputAmount The amount of output fluid produced per operation
	 */
	void addRecipe(String input, int inputAmount, int heat, String output, int outputAmount);

	/**
	 * Get a fermenter recipe for the given fluid
	 *
	 * @return The found recipe or null if no recipe is found
	 */
	FermentationProperty getFermentationInformation(Fluid fluid);

	/**
	 * Get the {@link FluidStack} output for the given input fluid
	 *
	 * @param Input fluid
	 * @return Output fluid + amount
	 */
	FluidStack getOutput(Fluid input);

	/**
	 * Gets the whole current fluid mappings
	 * @return The current fluid map
	 */
	Map<String, FermentationProperty> getRecipeMap();

	public static final class FermentationProperty {
		public FermentationProperty(int inputAmount, int heat, String output, int outputAmount) {
			this.inputAmount = inputAmount;
			this.heat = heat;
			this.output = output;
			this.outputAmount = outputAmount;
		}

		public FluidStack getOutput() {
			RegistryObject<Fluid> fluid = RegistryObject.of(new ResourceLocation(this.output), ForgeRegistries.FLUIDS);
			return !fluid.isPresent() ? null : new FluidStack(fluid.get(), this.outputAmount);
		}

		public final int inputAmount;
		public final int heat;
		public final String output;
		public final int outputAmount;
	}
}