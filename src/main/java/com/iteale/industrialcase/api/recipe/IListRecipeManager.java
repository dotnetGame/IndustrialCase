package com.iteale.industrialcase.api.recipe;

import java.util.List;

import net.minecraft.world.item.ItemStack;

/**
 * Recipe manager interface for basic lists.
 * 
 * @author Richard
 */
public interface IListRecipeManager extends Iterable<IRecipeInput> {
	/**
	 * Adds a stack to the list.
	 * 
	 * @param stack Stack to add
	 */
	public void add(IRecipeInput input);

	/**
	 * Checks whether the specified stack is in the list.
	 * 
	 * @param stack Stack to check
	 * @return Whether the stack is in the list
	 */
	public boolean contains(ItemStack stack);

	/**
	 * @return if the List is Empty
	 */
	public boolean isEmpty();

	/**
	 * Gets the list of stacks.
	 * 
	 * You're a mad evil scientist if you ever modify this.
	 * 
	 * @return List of stacks
	 */
	public List<IRecipeInput> getInputs();
}
