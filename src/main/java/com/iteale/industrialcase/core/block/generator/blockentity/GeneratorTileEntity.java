package com.iteale.industrialcase.core.block.generator.blockentity;

import com.iteale.industrialcase.core.block.invslot.InvSlotConsumableFuel;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.network.GuiSynced;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GeneratorTileEntity extends BaseGeneratorTileEntity {
    public final InvSlotConsumableFuel fuelSlot;
    @GuiSynced
    public int totalFuel;

    public ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            if (index == GeneratorDataType.FUEL.value) {
                return GeneratorTileEntity.this.fuel;
            } else if (index == GeneratorDataType.STORAGE.value) {
                return (int) GeneratorTileEntity.this.energy.getStorage();
            } else if (index == GeneratorDataType.CAPACITY.value) {
                return (int) GeneratorTileEntity.this.energy.getEnergy();
            } else if (index == GeneratorDataType.TOTAL_FUEL.value) {
                return GeneratorTileEntity.this.totalFuel;
            } else {
                throw new IndexOutOfBoundsException("Generator container data out of range.");
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == GeneratorDataType.FUEL.value) {
                GeneratorTileEntity.this.fuel = value;
            } else if (index == GeneratorDataType.STORAGE.value) {
                GeneratorTileEntity.this.energy.setStorage(value);
            } else if (index == GeneratorDataType.CAPACITY.value) {
                GeneratorTileEntity.this.energy.setEnergy(value);
            } else if (index == GeneratorDataType.TOTAL_FUEL.value) {
                GeneratorTileEntity.this.totalFuel = value;
            } else {
                throw new IndexOutOfBoundsException("Generator container data out of range.");
            }
        }

        @Override
        public int getCount() {
            return GeneratorDataType.values().length;
        }
    };

    public GeneratorTileEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.GENERATOR.get(), pos, state,
                Math.round(10.0F * 1.0F), 1, 4000);
        this.totalFuel = 0;
        this.fuelSlot = new InvSlotConsumableFuel(this, "fuel", 1, false);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.totalFuel = tag.getInt("totalFuel");
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        nbt.putInt("totalFuel", this.totalFuel);
        return nbt;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("gui.generator.electric.generator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new GeneratorMenu(containerId, inventory, this, this.dataAccess);
    }

    public boolean gainEnergy() {
        if (isConverting()) {
            this.energy.addEnergy(this.production);
            this.fuel--;
            return true;
        }
        return false;
    }

    public double getFuelRatio() {
        if (this.fuel <= 0)
            return 0.0D;
        return this.fuel / this.totalFuel;
    }

    public boolean gainFuel() {
        int fuelValue = this.fuelSlot.consumeFuel() / 4;
        if (fuelValue == 0)
            return false;
        this.fuel += fuelValue;
        this.totalFuel = fuelValue;
        return true;
    }

    public boolean isConverting() {
        return !needsFuel() && this.energy.getFreeEnergy() >= this.production;
    }

    public boolean needsFuel() {
        return this.fuel <= 0 && this.energy.getFreeEnergy() >= this.production;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GeneratorTileEntity blockEntity) {
        blockEntity.onServerTick();
        boolean needsInvUpdate = false;
        if (blockEntity.needsFuel())
            needsInvUpdate = blockEntity.gainFuel();
        boolean conversionActive = blockEntity.gainEnergy();
        if (!conversionActive && blockEntity.fuel > 0 ) {
            blockEntity.fuel--;
        }
    }

    public enum GeneratorDataType {
        FUEL(0),
        STORAGE(1),
        CAPACITY(2),
        TOTAL_FUEL(3);

        private final int value;
        private GeneratorDataType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
