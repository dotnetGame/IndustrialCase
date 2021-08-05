package com.iteale.industrialcase.core.block.machine;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityIronFurnace extends BlockEntity {
    public TileEntityIronFurnace(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityType.FURNACE, p_155229_, p_155230_);
    }
}
