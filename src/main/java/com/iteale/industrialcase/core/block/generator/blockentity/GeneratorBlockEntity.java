package com.iteale.industrialcase.core.block.generator.blockentity;

import com.iteale.industrialcase.core.block.comp.Energy;
import com.iteale.industrialcase.core.block.container.ChargeContainer;
import com.iteale.industrialcase.core.block.container.FuelContainer;
import com.iteale.industrialcase.core.block.generator.Generator;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GeneratorBlockEntity extends BaseContainerBlockEntity {
    private ChargeContainer chargeSlot;
    private FuelContainer fuelSlot;
    private Energy energy;
    public int fuel; // 燃料值
    public int totalFuel; // 总燃料值
    protected double production; // 转换率

    public ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            if (index == GeneratorDataType.FUEL.value) {
                return GeneratorBlockEntity.this.fuel;
            } else if (index == GeneratorDataType.STORAGE.value) {
                return (int) GeneratorBlockEntity.this.energy.getStorage();
            } else if (index == GeneratorDataType.CAPACITY.value) {
                return (int) GeneratorBlockEntity.this.energy.getCapacity();
            } else if (index == GeneratorDataType.TOTAL_FUEL.value) {
                return GeneratorBlockEntity.this.totalFuel;
            } else {
                throw new IndexOutOfBoundsException("Generator container data out of range.");
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == GeneratorDataType.FUEL.value) {
                GeneratorBlockEntity.this.fuel = value;
            } else if (index == GeneratorDataType.STORAGE.value) {
                GeneratorBlockEntity.this.energy.setStorage(value);
            } else if (index == GeneratorDataType.CAPACITY.value) {
                GeneratorBlockEntity.this.energy.setCapacity(value);
            } else if (index == GeneratorDataType.TOTAL_FUEL.value) {
                GeneratorBlockEntity.this.totalFuel = value;
            } else {
                throw new IndexOutOfBoundsException("Generator container data out of range.");
            }
        }

        @Override
        public int getCount() {
            return GeneratorDataType.values().length;
        }
    };

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.GENERATOR.get(), pos, state);
        chargeSlot = new ChargeContainer(1);
        fuelSlot = new FuelContainer(1, false);
        int maxStorage = 4000;
        int tier = 1;
        energy = Energy.asBasicSource(this, maxStorage, tier);
        fuel = 0;
        totalFuel = 0;
        production = 10.0F;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.fuel = tag.getInt("fuel");
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        nbt.putInt("fuel", this.fuel);
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

    @Override
    public int getContainerSize() {
        return fuelSlot.getContainerSize() + chargeSlot.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return fuelSlot.isEmpty() && chargeSlot.isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        if (index == 0) {
            return chargeSlot.getItem(0);
        } else if (index == 1) {
            return fuelSlot.getItem(0);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int index, int splitCount) {
        if (index == 0) {
            return chargeSlot.removeItem(0, splitCount);
        } else if (index == 1) {
            return fuelSlot.removeItem(0, splitCount);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        if (index == 0) {
            return chargeSlot.removeItemNoUpdate(0);
        } else if (index == 1) {
            return fuelSlot.removeItemNoUpdate(0);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        if (index == 0) {
            chargeSlot.setItem(0, itemStack);
        } else if (index == 1) {
            fuelSlot.setItem(0, itemStack);
        }
    }

    @Override
    public void clearContent() {
        chargeSlot.clearContent();
        fuelSlot.clearContent();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
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
        return (!needsFuel() && this.energy.getFreeEnergy() >= this.production);
    }

    public boolean needsFuel() {
        return (this.fuel <= 0 && this.energy.getFreeEnergy() >= this.production);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GeneratorBlockEntity blockEntity) {
        boolean needsInvUpdate = false;
        if (blockEntity.needsFuel())
            needsInvUpdate = blockEntity.gainFuel();
        boolean newActive = blockEntity.gainEnergy();
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
