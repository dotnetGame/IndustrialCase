package com.iteale.industrialcase.core.datagen.recipe;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class OresRecipes
        extends RecipeProvider
{
    public OresRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(BlockRegistry.TIN_BLOCK.get())
                .pattern("xxx")
                .pattern("xxx")
                .pattern("xxx")
                .define(Character.valueOf('x'), ItemRegistry.TIN_INGOT.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TIN_INGOT.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/ore/tin_block"));

        ShapedRecipeBuilder.shaped(BlockRegistry.LEAD_BLOCK.get())
                .pattern("xxx")
                .pattern("xxx")
                .pattern("xxx")
                .define(Character.valueOf('x'), ItemRegistry.LEAD_INGOT.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.LEAD_INGOT.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/ore/lead_block"));

        ShapedRecipeBuilder.shaped(ItemRegistry.TIN_INGOT.get(), 9)
                .pattern("x  ")
                .define(Character.valueOf('x'), BlockRegistry.TIN_BLOCK.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.TIN_BLOCK.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/ore/tin_ingot"));


        ShapedRecipeBuilder.shaped(ItemRegistry.LEAD_INGOT.get(), 9)
                .pattern("x  ")
                .define(Character.valueOf('x'), BlockRegistry.LEAD_BLOCK.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.LEAD_BLOCK.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/ore/lead_ingot"));
    }
}
