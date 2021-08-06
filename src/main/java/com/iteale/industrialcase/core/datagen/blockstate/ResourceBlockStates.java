package com.iteale.industrialcase.core.datagen.blockstate;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Random;
import java.util.function.Function;

public class ResourceBlockStates extends BlockStateProvider {

    public ResourceBlockStates(DataGenerator generatorIn, String modid, ExistingFileHelper exFileHelper) {
        super(generatorIn, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        cubeAll(BlockRegistry.RUBBER_LEAVES.get());
        cubeAll(BlockRegistry.RUBBER_PLANKS.get());
        cubeAll(BlockRegistry.LEAD_ORE.get());
        cubeAll(BlockRegistry.TIN_ORE.get());
        cubeAll(BlockRegistry.LEAD_BLOCK.get());
        cubeAll(BlockRegistry.TIN_BLOCK.get());


        /*
        simpleBlock(
                BlockRegistry.TIN_BLOCK.get(),
                new ModelFile.ExistingModelFile(
                        new ResourceLocation(IndustrialCase.MODID, "block/resource/block/tin_block"),
                        models().existingFileHelper
                )
        );
        */
    }
}
