package com.iteale.industrialcase.api.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

/**
 * Interface for recipe ingredient matchers.
 *
 * See {@link Recipes#inputFactory} for some default factory methods.
 */
public interface IRecipeInput {
	/**
	 * Check if subject matches this recipe input, ignoring the amount.
	 *
	 * @param subject ItemStack to check
	 * @return true if it matches the requirement
	 */
	boolean matches(ItemStack subject);

	/**
	 * Determine the minimum input stack size.
	 *
	 * @return input amount required
	 */
	int getAmount();

	/**
	 * List all possible inputs (best effort).
	 *
	 * The stack size has to match getAmount().
	 *
	 * @return list of inputs, may be incomplete
	 */
	List<ItemStack> getInputs();
	
	/**
	 * Returns a ingredient for this recipe input.
	 * You are highly advised to cache the resulting Ingredient.
	 * The ingredient should be consistent regarding {@link Ingredient#apply(ItemStack)} <-> {@link #matches(ItemStack)}
	 * and {@link Ingredient#func_193365_a()} <-> {@link #getInputs()}
	 * @return a matching Ingredient
	 */
	default Ingredient getIngredient() {
		return Recipes.inputFactory.getIngredient(this);
	}
}
