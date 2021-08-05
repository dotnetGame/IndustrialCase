package com.iteale.industrialcase.core.world.feature;

import com.iteale.industrialcase.core.block.BlockRegister;
import com.iteale.industrialcase.core.block.RubberLog;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.grower.AcaciaTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import javax.annotation.Nullable;
import java.util.Random;

public class RubberTree extends AbstractTreeGrower {
    public static final BlockState DEFAULT_RUBBER_LOG = BlockRegister.RUBBER_LOG.defaultBlockState();
    public static final BlockState RUBBER_LOG_NORTH_WET = BlockRegister.RUBBER_LOG.defaultBlockState()
            .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_north);
    public static final BlockState RUBBER_LOG_SOUTH_WET = BlockRegister.RUBBER_LOG.defaultBlockState()
            .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_south);
    public static final BlockState RUBBER_LOG_EAST_WET = BlockRegister.RUBBER_LOG.defaultBlockState()
            .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_east);
    public static final BlockState RUBBER_LOG_west_WET = BlockRegister.RUBBER_LOG.defaultBlockState()
            .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_west);

    public static final ConfiguredFeature<TreeConfiguration, ?> RUBBER_FEATURE =
            Feature.TREE.configured((
                    new TreeConfiguration.TreeConfigurationBuilder(
                            new WeightedStateProvider(
                                    new SimpleWeightedRandomList.Builder<BlockState>()
                                            .add(DEFAULT_RUBBER_LOG, 8)
                                            .add(RUBBER_LOG_NORTH_WET, 1)
                                            .add(RUBBER_LOG_SOUTH_WET, 1)
                                            .add(RUBBER_LOG_EAST_WET, 1)
                                            .add(RUBBER_LOG_west_WET, 1)
                                            .build()
                            ),
                            new StraightTrunkPlacer(4, 2, 0),
                            new SimpleStateProvider(BlockRegister.RUBBER_LEAVES.defaultBlockState()),
                            new SimpleStateProvider(BlockRegister.RUBBER_LEAVES.defaultBlockState()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)
                    )
            ).ignoreVines().build());
    @Nullable
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random rand, boolean p_225546_2_) {
        SimpleWeightedRandomList<BlockState> a = new SimpleWeightedRandomList.Builder<BlockState>()
                .add(DEFAULT_RUBBER_LOG, 8)
                .add(RUBBER_LOG_NORTH_WET, 1)
                .add(RUBBER_LOG_SOUTH_WET, 1)
                .add(RUBBER_LOG_EAST_WET, 1)
                .add(RUBBER_LOG_west_WET, 1)
                .build();
        return RUBBER_FEATURE;
    }

}