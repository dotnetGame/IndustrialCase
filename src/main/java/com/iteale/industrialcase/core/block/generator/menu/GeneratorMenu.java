package com.iteale.industrialcase.core.block.generator.menu;

import com.iteale.industrialcase.core.block.generator.blockentity.GeneratorBlockEntity;
import com.iteale.industrialcase.core.registries.MenuTypeRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;

public class GeneratorMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;
    public GeneratorMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(2), new SimpleContainerData(4));
    }

    public GeneratorMenu(int containerId, Inventory inventory, Container containerIn, ContainerData dataIn) {
        super(MenuTypeRegistry.GENERATOR_MENU.get(), containerId);
        container = containerIn;
        data = dataIn;

        initSlots(containerId, inventory);
        addDataSlots(data);
    }

    private void initSlots(int containerId, Inventory inventory) {
        this.addSlot(new Slot(container, 0, 56, 17));

        this.addSlot(new Slot(container, 1, 56, 53));

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 79 + y * 18));
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

    public int getTotalFuel() {
        return this.data.get(GeneratorBlockEntity.GeneratorDataType.TOTAL_FUEL.getValue());
    }

    public int getStorage() {
        return this.data.get(GeneratorBlockEntity.GeneratorDataType.STORAGE.getValue());
    }

    public int getCapacity() {
        return this.data.get(GeneratorBlockEntity.GeneratorDataType.CAPACITY.getValue());
    }
}
