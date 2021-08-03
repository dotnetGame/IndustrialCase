package com.iteale.industrialcase.core.world.feature;

import com.iteale.industrialcase.core.block.BlockRegister;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import javax.annotation.Nullable;
import java.util.Random;

public class RubberTree extends Tree {
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> RUBBER_FEATURE =
            Feature.TREE.configured((
                    new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(BlockRegister.RUBBER_LOG.defaultBlockState()),
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