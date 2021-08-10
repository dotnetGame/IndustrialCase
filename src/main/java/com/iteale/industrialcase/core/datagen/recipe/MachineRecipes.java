package com.iteale.industrialcase.core.datagen.recipe;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class MachineRecipes extends RecipeProvider {
    public MachineRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(BlockRegistry.IRON_FURNACE.get())
                .pattern(" x ")
                .pattern("x x")
                .pattern("xox")
                .define(Character.valueOf('x'), Items.IRON_INGOT)
                .define(Character.valueOf('o'), Blocks.FURNACE)
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT, Blocks.FURNACE))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "machine/processing/basic/iron_furnace"));

    }
}