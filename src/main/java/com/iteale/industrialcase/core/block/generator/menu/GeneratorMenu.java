package com.iteale.industrialcase.core.block.generator.menu;

import com.iteale.industrialcase.core.block.generator.blockentity.GeneratorBlockEntity;
import com.iteale.industrialcase.core.registries.MenuTypeRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;

public class GeneratorMenu extends AbstractContainerMenu {
    // private final CraftingContainer craftSlots = new CraftingContainer(this, 3, 3);
    // private final ResultContainer resultSlots = new ResultContainer();
    private final Container container;
    private final ContainerData data;
    public GeneratorMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(2), new SimpleContainerData(3));
    }

    public GeneratorMenu(int containerId, Inventory inventory, Container containerIn, ContainerData dataIn) {
        super(MenuTypeRegistry.GENERATOR_MENU.get(), containerId);
        container = containerIn;
        data = dataIn;
        initSlots(containerId, inventory);
    }

    private void initSlots(int containerId, Inventory inventory) {
        this.addSlot(new Slot(container, 0, 56, 16));

        this.addSlot(new Slot(container, 1, 56, 52));

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, 79 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 137));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    // Container data
    public int getFuel() {
        return this.data.get(GeneratorBlockEntity.GeneratorDataType.FUEL.getValue());
    }

    public int getStorage() {
        return this.data.get(GeneratorBlockEntity.GeneratorDataType.STORAGE.getValue());
    }

    public int getCapacity() {
        return this.data.get(GeneratorBlockEntity.GeneratorDataType.CAPACITY.getValue());
    }
}