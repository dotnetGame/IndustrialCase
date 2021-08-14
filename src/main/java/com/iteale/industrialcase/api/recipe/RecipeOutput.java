package com.iteale.industrialcase.api.recipe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

@Deprecated
public final class RecipeOutput {
	public RecipeOutput(CompoundTag metadata1, List<ItemStack> items1) {
		assert !items1.contains(null);

		this.metadata = metadata1;
		this.items = items1;
	}

	public RecipeOutput(CompoundTag metadata1, ItemStack... items1) {
		this(metadata1, Arrays.asList(items1));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecipeOutput) {
			RecipeOutput ro = (RecipeOutput) obj;

			if (items.size() == ro.items.size() &&
					(metadata == null && ro.metadata == null || metadata != null && ro.metadata != null && metadata.equals(ro.metadata))) {
				Iterator<ItemStack> itA = items.iterator();
				Iterator<ItemStack> itB = ro.items.iterator();

				while (itA.hasNext() && itB.hasNext()) {
					ItemStack stackA = itA.next();
					ItemStack stackB = itB.next();

					if (ItemStack.isSame(stackA, stackB)) return false;
				}

				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "ROutput<"+items+","+metadata+">";
	}

	public final List<ItemStack> items;
	public final CompoundTag metadata;
}
