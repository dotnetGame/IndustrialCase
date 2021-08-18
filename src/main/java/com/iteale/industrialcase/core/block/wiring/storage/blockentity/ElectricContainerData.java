package com.iteale.industrialcase.core.block.wiring.storage.blockentity;

import net.minecraft.world.inventory.ContainerData;

public class ElectricContainerData implements ContainerData {
    private ElectricBlockEntity blockEntity;
    public ElectricContainerData(ElectricBlockEntity blockEntityIn) {
        blockEntity = blockEntityIn;
    }
    @Override
    public int get(int index) {
        if (index == ElectricBlockEntity.StorageDataType.STORAGE.getValue()) {
            return (int) blockEntity.energy.getStorage();
        } else if (index == ElectricBlockEntity.StorageDataType.CAPACITY.getValue()) {
            return (int) blockEntity.energy.getEnergy();
        } else {
            throw new IndexOutOfBoundsException("Generator container data out of range.");
        }
    }

    @Override
    public void set(int index, int value) {
        if (index == ElectricBlockEntity.StorageDataType.STORAGE.getValue()) {
            blockEntity.energy.setStorage(value);
        } else if (index == ElectricBlockEntity.StorageDataType.CAPACITY.getValue()) {
            blockEntity.energy.setEnergy(value);
        } else {
            throw new IndexOutOfBoundsException("Generator container data out of range.");
        }
    }

    @Override
    public int getCount() {
        return ElectricBlockEntity.StorageDataType.values().length;
    }
}
