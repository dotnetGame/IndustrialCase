package com.iteale.industrialcase.core.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

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

    private static boolean never(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState blockState, IBlockReader blockReader, BlockPos p_235441_2_, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }
}
