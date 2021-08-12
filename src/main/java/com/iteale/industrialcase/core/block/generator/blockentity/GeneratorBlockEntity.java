package com.iteale.industrialcase.core.block.generator.blockentity;

import com.iteale.industrialcase.core.block.comp.Energy;
import com.iteale.industrialcase.core.block.generator.Generator;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class GeneratorBlockEntity extends BaseContainerBlockEntity {
    private Container items;
    private Energy energy;
    public int fuel; // 燃料值
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
        int maxStorage = 4000;
        int tier = 1;
        energy = Energy.asBasicSource(this, maxStorage, tier);
        fuel = 0;
        production = 10.0F;
        items = new SimpleContainer(2);
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
        return items.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        return items.getItem(index);
    }

    @Override
    public ItemStack removeItem(int index, int p_18943_) {
        return items.removeItem(index, p_18943_);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return items.removeItemNoUpdate(index);
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        items.setItem(index, itemStack);
    }

    @Override
    public void clearContent() {
        items.clearContent();
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

    public boolean isConverting() {
        return (!needsFuel() && this.energy.getFreeEnergy() >= this.production);
    }

    public boolean needsFuel() {
        return (this.fuel <= 0 && this.energy.getFreeEnergy() >= this.production);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GeneratorBlockEntity blockEntity) {
        if (blockEntity.items.getItem(1).is(Items.OAK_PLANKS)) {
            blockEntity.fuel = 10;
        }
    }

    public enum GeneratorDataType {
        FUEL(0),
        STORAGE(1),
        CAPACITY(2);

        private final int value;
        private GeneratorDataType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
