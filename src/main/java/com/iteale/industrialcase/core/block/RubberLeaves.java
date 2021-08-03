package com.iteale.industrialcase.core.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class RubberLeaves extends LeavesBlock {
    public RubberLeaves() {
        super(
                AbstractBlock.Properties.of(Material.LEAVES)
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
    public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
        super.randomTick(p_225542_1_, p_225542_2_, p_225542_3_, p_225542_4_);
    }

    private static boolean never(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState blockState, IBlockReader blockReader, BlockPos p_235441_2_, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }
}
