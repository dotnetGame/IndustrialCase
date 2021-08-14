package com.iteale.industrialcase.api.recipe;

import java.util.Collection;

import com.iteale.industrialcase.api.util.FluidContainerOutputMode;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFillFluidContainerRecipeManager extends IMachineRecipeManager<Void, Collection<ItemStack>, IFillFluidContainerRecipeManager.Input> {
	MachineRecipeResult<Void, Collection<ItemStack>, Input> apply(Input input, FluidContainerOutputMode outputMode, boolean acceptTest);

	public static class Input {
		public Input(ItemStack container, FluidStack fluid) {
			this.container = container;
			this.fluid = fluid;
		}

		public final ItemStack container;
		public final FluidStack fluid;
	}
}
