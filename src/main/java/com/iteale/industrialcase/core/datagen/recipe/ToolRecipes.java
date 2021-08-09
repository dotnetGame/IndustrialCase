package com.iteale.industrialcase.core.datagen.recipe;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ToolRecipes extends RecipeProvider {
    public ToolRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(BlockRegistry.RUBBER_PLANKS.get(), 4)
                .requires(BlockRegistry.RUBBER_LOG.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.RUBBER_LOG.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/plant/rubber_planks"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.RESIN.get()), ItemRegistry.RUBBER.get(), 0.7F, 200)
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.RESIN.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "crafting/rubber_from_smelting"));
    }
}
