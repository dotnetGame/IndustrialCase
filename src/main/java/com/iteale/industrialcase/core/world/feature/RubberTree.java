package com.iteale.industrialcase.core.world.feature;

import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.block.RubberLog;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Random;

public class RubberTree extends AbstractTreeGrower {
    public static TreeConfiguration getTreeConfig() {
        BlockState rubberLog = BlockRegistry.RUBBER_LOG.get().defaultBlockState();
        BlockState rubberLogNorthWet = BlockRegistry.RUBBER_LOG.get().defaultBlockState()
                .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_north);
        BlockState rubberLogSouthWet = BlockRegistry.RUBBER_LOG.get().defaultBlockState()
                .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_south);
        BlockState rubberLogEastWet = BlockRegistry.RUBBER_LOG.get().defaultBlockState()
                .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_east);
        BlockState rubberLogWestWet = BlockRegistry.RUBBER_LOG.get().defaultBlockState()
                .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_west);
        return (new TreeConfiguration.TreeConfigurationBuilder(
                new WeightedStateProvider(
                        new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(rubberLog, 8)
                                .add(rubberLogNorthWet, 1)
                                .add(rubberLogSouthWet, 1)
                                .add(rubberLogEastWet, 1)
                                .add(rubberLogWestWet, 1)
                                .build()
                ),
                new StraightTrunkPlacer(4, 2, 0),
                new SimpleStateProvider(BlockRegistry.RUBBER_LEAVES.get().defaultBlockState()),
                new SimpleStateProvider(BlockRegistry.RUBBER_SAPLING.get().defaultBlockState()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        )).ignoreVines().build();
    }

    @Nullable
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random rand, boolean p_225546_2_) {

        ConfiguredFeature<TreeConfiguration, ?> RUBBER_FEATURE =
                Feature.TREE.configured(getTreeConfig());
        return RUBBER_FEATURE;
    }

}