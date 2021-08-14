package com.iteale.industrialcase.api.recipe;

import java.util.Collection;

import com.iteale.industrialcase.api.util.FluidContainerOutputMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;


public interface IEmptyFluidContainerRecipeManager extends IMachineRecipeManager<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> {
	MachineRecipeResult<Void, Output, ItemStack> apply(ItemStack input, Fluid requiredFluid, FluidContainerOutputMode outputMode, boolean acceptTest);

	public static class Output {
		public Output(Collection<ItemStack> container, FluidStack fluid) {
			this.container = container;
			this.fluid = fluid;
		}

		public final Collection<ItemStack> container;
		public final FluidStack fluid;
	}
}
