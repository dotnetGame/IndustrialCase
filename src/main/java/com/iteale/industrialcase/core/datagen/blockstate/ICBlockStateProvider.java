package com.iteale.industrialcase.core.datagen.blockstate;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ICBlockStateProvider extends BlockStateProvider {
    public ICBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }
    protected void registerCubeAll(Block block) {
        simpleBlock(
                block,
                new ModelFile.ExistingModelFile(
                        new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath()),
                        models().existingFileHelper
                )
        );
    }
}
