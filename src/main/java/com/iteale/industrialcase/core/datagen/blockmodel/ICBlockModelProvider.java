package com.iteale.industrialcase.core.datagen.blockmodel;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ICBlockModelProvider extends BlockModelProvider {
    public ICBlockModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    protected void registerSimple(Block block) {
        String name = "block/" + block.getRegistryName().getPath();
        cubeAll(name, new ResourceLocation(IndustrialCase.MODID, name));
    }
}
