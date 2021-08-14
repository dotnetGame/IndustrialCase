package com.iteale.industrialcase.api.recipe;


import net.minecraft.nbt.CompoundTag;

public class MachineRecipe<I, O> {
	public MachineRecipe(I input, O output) {
		this(input, output, null);
	}

	public MachineRecipe(I input, O output, CompoundTag meta) {
		this.input = input;
		this.output = output;
		this.meta = meta;
	}

	public I getInput() {
		return input;
	}

	public O getOutput() {
		return output;
	}

	public CompoundTag getMetaData() {
		return meta;
	}

	public <AI> MachineRecipeResult<I, O, AI> getResult(AI adjustedInput) {
		return new MachineRecipeResult<I, O, AI>(this, adjustedInput);
	}

	private final I input;
	private final O output;
	private final CompoundTag meta;
}
