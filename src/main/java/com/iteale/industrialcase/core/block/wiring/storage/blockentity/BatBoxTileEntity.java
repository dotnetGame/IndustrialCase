package com.iteale.industrialcase.core.block.wiring.storage.blockentity;

import com.iteale.industrialcase.core.block.wiring.storage.menu.BatBoxMenu;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BatBoxTileEntity extends ElectricTileEntity {
    public BatBoxTileEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.BATBOX.get(), pos, state, 1, 32, 40000);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("gui.wiring.storage.batbox");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new BatBoxMenu(containerId, inventory, this, this.dataAccess);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BatBoxTileEntity blockEntity) {
        blockEntity.onServerTick();
    }
}
