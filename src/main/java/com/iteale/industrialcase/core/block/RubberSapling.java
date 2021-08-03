package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.world.feature.RubberTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class RubberSapling extends SaplingBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    public RubberSapling() {
        super(
                new RubberTree(),
                AbstractBlock.Properties.of(Material.PLANT)
                        .noCollission()
                        .randomTicks()
                        .instabreak().sound(SoundType.GRASS)
        );
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
