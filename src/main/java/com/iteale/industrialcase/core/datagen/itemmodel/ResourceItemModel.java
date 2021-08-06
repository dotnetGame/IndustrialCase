package com.iteale.industrialcase.core.datagen.itemmodel;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
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
        // plants
        registerItemBlock(BlockRegistry.RUBBER_LEAVES.get());
        registerItemBlock(BlockRegistry.RUBBER_PLANKS.get());

        // ore
        registerItemBlock(BlockRegistry.LEAD_ORE.get());
        registerItemBlock(BlockRegistry.TIN_ORE.get());
        registerItemBlock(BlockRegistry.PLATINUM_ORE.get());
        registerItemBlock(BlockRegistry.SILVER_ORE.get());

        // block
        registerItemBlock(BlockRegistry.LEAD_BLOCK.get());
        registerItemBlock(BlockRegistry.TIN_BLOCK.get());
    }
}
