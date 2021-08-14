package com.iteale.industrialcase.api.recipe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;


public interface IBasicMachineRecipeManager extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> {
	boolean addRecipe(IRecipeInput input, CompoundTag metadata, boolean replace, ItemStack... outputs);

	@Deprecated
	RecipeOutput getOutputFor(ItemStack input, boolean adjustInput);
}
