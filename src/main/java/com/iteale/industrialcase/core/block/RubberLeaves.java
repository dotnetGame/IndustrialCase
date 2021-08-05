package com.iteale.industrialcase.core.block;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class RubberLeaves extends LeavesBlock {
    public RubberLeaves() {
        super(
                BlockBehaviour.Properties.of(Material.LEAVES)
                        .strength(0.2F)
                        .randomTicks()
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .isValidSpawn(RubberLeaves::ocelotOrParrot)
                        .isSuffocating(RubberLeaves::never)
                        .isViewBlocking(RubberLeaves::never)
        );
    }

    @Override
    public void randomTick(BlockState p_225542_1_, ServerLevel p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
        super.randomTick(p_225542_1_, p_225542_2_, p_225542_3_, p_225542_4_);
    }

    private static boolean never(BlockState blockState, BlockGetter iBlockReader, BlockPos blockPos) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState blockState, BlockGetter blockReader, BlockPos p_235441_2_, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }
}
