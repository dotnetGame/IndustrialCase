package com.iteale.industrialcase.api.recipe;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IPatternStorage {
	boolean addPattern(ItemStack itemstack);

	List<ItemStack> getPatterns();
}
