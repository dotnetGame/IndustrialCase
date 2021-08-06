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

public class ResourceBlockState extends ICBlockStateProvider {

    public ResourceBlockState(DataGenerator generatorIn, String modid, ExistingFileHelper exFileHelper) {
        super(generatorIn, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerCubeAll(BlockRegistry.RUBBER_LEAVES.get());
        registerCubeAll(BlockRegistry.RUBBER_PLANKS.get());
        registerCubeAll(BlockRegistry.LEAD_ORE.get());
        registerCubeAll(BlockRegistry.TIN_ORE.get());
        registerCubeAll(BlockRegistry.LEAD_BLOCK.get());
        registerCubeAll(BlockRegistry.TIN_BLOCK.get());
    }
}
