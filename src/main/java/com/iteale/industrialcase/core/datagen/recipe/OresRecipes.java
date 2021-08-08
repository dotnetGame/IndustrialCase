package com.iteale.industrialcase.core.datagen.recipe;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class OresRecipes extends RecipeProvider
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

        ShapelessRecipeBuilder.shapeless(ItemRegistry.COPPER_PLATE.get())
                .requires(Items.COPPER_INGOT)
                .requires(ItemRegistry.FORGE_HAMMER.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT, ItemRegistry.FORGE_HAMMER.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/plate/copper_plate"));

        ShapelessRecipeBuilder.shapeless(ItemRegistry.LEAD_PLATE.get())
                .requires(ItemRegistry.LEAD_INGOT.get())
                .requires(ItemRegistry.FORGE_HAMMER.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.LEAD_INGOT.get(), ItemRegistry.FORGE_HAMMER.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/plate/lead_plate"));

        ShapelessRecipeBuilder.shapeless(ItemRegistry.TIN_PLATE.get())
                .requires(ItemRegistry.TIN_INGOT.get())
                .requires(ItemRegistry.FORGE_HAMMER.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TIN_INGOT.get(), ItemRegistry.FORGE_HAMMER.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/plate/tin_plate"));

        ShapelessRecipeBuilder.shapeless(ItemRegistry.IRON_PLATE.get())
                .requires(Items.IRON_INGOT)
                .requires(ItemRegistry.FORGE_HAMMER.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT, ItemRegistry.FORGE_HAMMER.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/plate/iron_plate"));

        ShapelessRecipeBuilder.shapeless(ItemRegistry.GOLD_PLATE.get())
                .requires(Items.GOLD_INGOT)
                .requires(ItemRegistry.FORGE_HAMMER.get())
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT, ItemRegistry.FORGE_HAMMER.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/plate/gold_plate"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.LEAD_ORE.get()), ItemRegistry.LEAD_INGOT.get(), 0.7F, 200)
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.LEAD_ORE.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/ingot/lead_ingot_from_smelting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.TIN_ORE.get()), ItemRegistry.TIN_INGOT.get(), 0.7F, 200)
                .group(IndustrialCase.MODID)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.TIN_ORE.get()))
                .save(consumer, new ResourceLocation(IndustrialCase.MODID, "resource/ingot/tin_ingot_from_smelting"));
    }
}
