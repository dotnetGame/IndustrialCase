package com.iteale.industrialcase.core.datagen.recipe;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class WiringRecipes extends RecipeProvider {
    public WiringRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(BlockRegistry.COPPER_CABLE.get(), 2)
                .requires(ItemRegistry.COPPER_PLATE.get())
                .requires(ItemRegistry.CUTTER.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CUTTER.get(), ItemRegistry.COPPER_PLATE.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "wiring/cable/copper_cable"));

        ShapelessRecipeBuilder.shapeless(BlockRegistry.TIN_CABLE.get(), 2)
                .requires(ItemRegistry.TIN_PLATE.get())
                .requires(ItemRegistry.CUTTER.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CUTTER.get(), ItemRegistry.TIN_PLATE.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "wiring/cable/tin_cable"));

        ShapelessRecipeBuilder.shapeless(BlockRegistry.COPPER_CABLE_INSULATED.get(), 1)
                .requires(BlockRegistry.COPPER_CABLE.get())
                .requires(ItemRegistry.RUBBER.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.RUBBER.get(), BlockRegistry.COPPER_CABLE.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "wiring/cable/copper_cable_insulated"));
    }
}