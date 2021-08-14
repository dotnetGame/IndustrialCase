package com.iteale.industrialcase.api.recipe;

import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface IScrapboxManager extends IBasicMachineRecipeManager {
	void addDrop(ItemStack drop, float rawChance);

	ItemStack getDrop(ItemStack input, boolean adjustInput);

	Map<ItemStack, Float> getDrops();
}
