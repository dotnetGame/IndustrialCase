package com.iteale.industrialcase.core.block.wiring.storage;

import com.iteale.industrialcase.core.block.wiring.storage.blockentity.BatBoxTileEntity;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public class BatBox extends ElectricBlock {
    public BatBox() {
        super(BlockBehaviour.Properties.of(Material.WOOD));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return null;
        } else {
            return createTickerHelper(blockEntityType, BlockEntityRegistry.BATBOX.get(), BatBoxTileEntity::serverTick);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BatBoxTileEntity(pos, state);
    }
}
