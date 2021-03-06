package com.iteale.industrialcase.api.recipe;

import net.minecraft.world.item.ItemStack;

/**
 * Recipe manager interface for crafting recipes.
 *
 * @author Richard
 */
public interface ICraftingRecipeManager {
	/**
	 * Adds a shaped crafting recipe.
	 *
	 * @param output Recipe output
	 * @param input Recipe input format
	 */
	public void addRecipe(ItemStack output, Object... input);

	/**
	 * Adds a shapeless crafting recipe.
	 *
	 * @param output Recipe output
	 * @param input Recipe input
	 */
	public void addShapelessRecipe(ItemStack output, Object... input);

	/**
	 * Attribute container to pass additional information to AdvRecipe and AdvShapelessRecipe
	 *
	 * @author Chocohead
	 */
	public static class AttributeContainer {
		public final boolean hidden;
		public final boolean consuming;
		public final boolean fixedSize;

		/**
		 * @param hidden Whether the recipe is hidden from JEI by default
		 * @param consuming Whether containers should be consumed or returned empty
		 */
		public AttributeContainer(boolean hidden, boolean consuming) {
			this(hidden, consuming, false);
		}

		/**
		 * @param hidden Whether the recipe is hidden from JEI by default
		 * @param consuming Whether containers should be consumed or returned empty
		 * @param fixedSize Whether empty rows or columns can be safely ignored as intentional
		 */
		public AttributeContainer(boolean hidden, boolean consuming, boolean fixedSize) {
			this.hidden = hidden;
			this.consuming = consuming;
			this.fixedSize = fixedSize;
		}
	}
}