package com.iteale.industrialcase.core.block.wiring.storage.blockentity;

import com.iteale.industrialcase.core.block.comp.BlockEntityComponent;
import com.iteale.industrialcase.core.block.generator.blockentity.GeneratorBlockEntity;
import com.iteale.industrialcase.core.block.wiring.storage.BatBox;
import com.iteale.industrialcase.core.block.wiring.storage.menu.BatBoxMenu;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BatBoxBlockEntity extends ElectricBlockEntity {

    public ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            if (index == StorageDataType.STORAGE.getValue()) {
                return (int) BatBoxBlockEntity.this.energy.getStorage();
            } else if (index == StorageDataType.CAPACITY.getValue()) {
                return (int) BatBoxBlockEntity.this.energy.getCapacity();
            } else {
                throw new IndexOutOfBoundsException("Generator container data out of range.");
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == StorageDataType.STORAGE.getValue()) {
                BatBoxBlockEntity.this.energy.setStorage(value);
            } else if (index == StorageDataType.CAPACITY.getValue()) {
                BatBoxBlockEntity.this.energy.setCapacity(value);
            } else {
                throw new IndexOutOfBoundsException("Generator container data out of range.");
            }
        }

        @Override
        public int getCount() {
            return StorageDataType.values().length;
        }
    };
    public BatBoxBlockEntity(BlockPos pos, BlockState state) {
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

    public static void serverTick(Level level, BlockPos pos, BlockState state, BatBoxBlockEntity blockEntity) {
        blockEntity.onServerTick();
    }
}
