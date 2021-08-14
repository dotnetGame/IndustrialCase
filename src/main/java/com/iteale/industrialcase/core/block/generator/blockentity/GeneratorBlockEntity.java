package com.iteale.industrialcase.core.block.generator.blockentity;

import com.iteale.industrialcase.core.block.comp.Energy;
import com.iteale.industrialcase.core.block.container.ChargeContainer;
import com.iteale.industrialcase.core.block.container.FuelContainer;
import com.iteale.industrialcase.core.block.generator.Generator;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.network.GuiSynced;
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

import java.util.List;

public class GeneratorBlockEntity extends BaseGeneratorBlockEntity {
    public final FuelContainer fuelSlot;
    @GuiSynced
    public int totalFuel;

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
        super(BlockEntityRegistry.GENERATOR.get(), pos, state,
                Math.round(10.0F * 1.0F), 1, 4000);
        this.totalFuel = 0;
        this.fuelSlot = new FuelContainer(this, "fuel", 1, false);
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

    @Override
    public List<String> getNetworkedFields() {
        return null;
    }

    @Override
    public void onNetworkUpdate(String field) {

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
