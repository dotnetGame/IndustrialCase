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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class BatteryRecipes extends RecipeProvider {
    public BatteryRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(BlockRegistry.IRON_FURNACE.get())
                .pattern(" o ")
                .pattern("x#x")
                .pattern("x#x")
                .define(Character.valueOf('x'), ItemRegistry.TIN_INGOT.get())
                .define(Character.valueOf('o'), BlockRegistry.COPPER_CABLE_INSULATED.get())
                .define(Character.valueOf('#'), Blocks.REDSTONE_WIRE)
                .group(IndustrialCase.MODID)
                .unlockedBy(
                        "item",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                ItemRegistry.TIN_INGOT.get(),
                                BlockRegistry.COPPER_CABLE_INSULATED.get(),
                                Blocks.REDSTONE_WIRE)
                )
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "battery/re_battery"));

    }
}
