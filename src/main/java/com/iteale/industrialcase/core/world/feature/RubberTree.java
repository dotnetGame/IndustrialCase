package com.iteale.industrialcase.core.world.feature;

import com.iteale.industrialcase.core.block.BlockRegister;
import com.iteale.industrialcase.core.block.RubberLog;
import net.minecraft.block.BlockState;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.WeightedList;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import javax.annotation.Nullable;
import java.util.Random;

public class RubberTree extends Tree {
    public static final BlockState DEFAULT_RUBBER_LOG = BlockRegister.RUBBER_LOG.defaultBlockState();
    public static final BlockState RUBBER_LOG_NORTH_WET = BlockRegister.RUBBER_LOG.defaultBlockState()
            .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_north);
    public static final BlockState RUBBER_LOG_SOUTH_WET = BlockRegister.RUBBER_LOG.defaultBlockState()
            .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_south);
    public static final BlockState RUBBER_LOG_EAST_WET = BlockRegister.RUBBER_LOG.defaultBlockState()
            .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_east);
    public static final BlockState RUBBER_LOG_west_WET = BlockRegister.RUBBER_LOG.defaultBlockState()
            .setValue(RubberLog.STATE, RubberLog.RubberLogState.wet_west);
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> RUBBER_FEATURE =
            Feature.TREE.configured((
                    new BaseTreeFeatureConfig.Builder(
                            new WeightedBlockStateProvider()
                                    .add(DEFAULT_RUBBER_LOG, 8)
                                    .add(RUBBER_LOG_NORTH_WET, 1)
                                    .add(RUBBER_LOG_SOUTH_WET, 1)
                                    .add(RUBBER_LOG_EAST_WET, 1)
                                    .add(RUBBER_LOG_west_WET, 1),
                            new SimpleBlockStateProvider(BlockRegister.RUBBER_LEAVES.defaultBlockState()),
                            new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
                            new StraightTrunkPlacer(4, 2, 0),
                            new TwoLayerFeature(1, 0, 1)
                    )
            ).ignoreVines().build());
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean p_225546_2_) {
        return RUBBER_FEATURE;
    }

}