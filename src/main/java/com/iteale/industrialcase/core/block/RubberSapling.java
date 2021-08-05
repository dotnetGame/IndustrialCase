package com.iteale.industrialcase.core.block;


import com.iteale.industrialcase.core.world.feature.RubberTree;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

public class RubberSapling extends SaplingBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    public RubberSapling() {
        super(
                new RubberTree(),
                BlockBehaviour.Properties.of(Material.PLANT)
                        .noCollission()
                        .randomTicks()
                        .instabreak().sound(SoundType.GRASS)
        );
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
