package com.iteale.industrialcase.core.datagen.itemmodel;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ResourceItemModel extends ICItemModelProvider {
    public ResourceItemModel(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // plate
        registerItem(ItemRegistry.BRONZE_PLATE.get());
        registerItem(ItemRegistry.COPPER_PLATE.get());
        registerItem(ItemRegistry.GOLD_PLATE.get());
        registerItem(ItemRegistry.IRON_PLATE.get());
        registerItem(ItemRegistry.LEAD_PLATE.get());
        registerItem(ItemRegistry.TIN_PLATE.get());

        // casing
        registerItem(ItemRegistry.CASING_BRONZE.get());
        registerItem(ItemRegistry.CASING_COPPER.get());
        registerItem(ItemRegistry.CASING_GOLD.get());
        registerItem(ItemRegistry.CASING_IRON.get());
        registerItem(ItemRegistry.CASING_LEAD.get());
        registerItem(ItemRegistry.CASING_STEEL.get());
        registerItem(ItemRegistry.CASING_TIN.get());

        // crushed
        registerItem(ItemRegistry.CRUSHED_COPPER.get());
        registerItem(ItemRegistry.CRUSHED_GOLD.get());
        registerItem(ItemRegistry.CRUSHED_IRON.get());
        registerItem(ItemRegistry.CRUSHED_LEAD.get());
        registerItem(ItemRegistry.CRUSHED_SILVER.get());
        registerItem(ItemRegistry.CRUSHED_TIN.get());
        registerItem(ItemRegistry.CRUSHED_URANIUM.get());
        
        // dust
        registerItem(ItemRegistry.BRONZE_DUST.get());
        registerItem(ItemRegistry.CLAY_DUST.get());
        registerItem(ItemRegistry.COAL_DUST.get());
        registerItem(ItemRegistry.COPPER_DUST.get());
        registerItem(ItemRegistry.DIAMOND_DUST.get());
        registerItem(ItemRegistry.EMERALD_DUST.get());
        registerItem(ItemRegistry.GOLD_DUST.get());
        registerItem(ItemRegistry.LEAD_DUST.get());
        registerItem(ItemRegistry.LAPIS_DUST.get());
        registerItem(ItemRegistry.TIN_DUST.get());
        registerItem(ItemRegistry.OBSIDIAN_DUST.get());
        registerItem(ItemRegistry.SILVER_DUST.get());

        // ingot
        registerItem(ItemRegistry.TIN_INGOT.get());
        registerItem(ItemRegistry.LEAD_INGOT.get());

        // tree
        registerItemBlock(BlockRegistry.RUBBER_LOG.get());
        registerItem(ItemRegistry.RUBBER.get());
        registerItem(ItemRegistry.TREETAP.get());

        // craft
        registerItem(ItemRegistry.RESIN.get());
        registerItem(ItemRegistry.CUTTER.get());
        registerItem(ItemRegistry.FORGE_HAMMER.get());

    }
}
