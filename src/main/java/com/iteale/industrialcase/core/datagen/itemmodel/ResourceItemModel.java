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
        registerItemBlock(BlockRegistry.RUBBER_LOG.get());

        registerItem(ItemRegistry.BRONZE_PLATE.get());
        registerItem(ItemRegistry.COPPER_PLATE.get());
        registerItem(ItemRegistry.GOLD_PLATE.get());
        registerItem(ItemRegistry.IRON_PLATE.get());
        registerItem(ItemRegistry.LEAD_PLATE.get());
        registerItem(ItemRegistry.TIN_PLATE.get());

        registerItem(ItemRegistry.TIN_INGOT.get());
        registerItem(ItemRegistry.LEAD_INGOT.get());

        registerItem(ItemRegistry.RUBBER.get());
        registerItem(ItemRegistry.TREETAP.get());

        registerItem(ItemRegistry.RESIN.get());
        registerItem(ItemRegistry.CUTTER.get());
        registerItem(ItemRegistry.FORGE_HAMMER.get());

    }
}
