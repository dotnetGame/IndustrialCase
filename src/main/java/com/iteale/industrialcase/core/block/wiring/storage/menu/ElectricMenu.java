package com.iteale.industrialcase.core.block.wiring.storage.menu;

import com.iteale.industrialcase.core.block.MenuBase;
import com.iteale.industrialcase.core.block.wiring.storage.blockentity.ElectricBlockEntity;
import com.iteale.industrialcase.core.registries.MenuTypeRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;

public class ElectricMenu extends MenuBase {
    private final Container container;
    private final ContainerData data;
    public ElectricMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(2), new SimpleContainerData(2));
    }

    public ElectricMenu(int containerId, Inventory inventory, Container containerIn, ContainerData dataIn) {
        super(MenuTypeRegistry.BATBOX_MENU.get(), containerId);
        container = containerIn;
        data = dataIn;

        initSlots(containerId, inventory);
        addDataSlots(data);
    }

    private void initSlots(int containerId, Inventory inventory) {
        this.addSlot(new Slot(container, 0, 56, 17));
        this.addSlot(new Slot(container, 1, 56, 53));
        this.addInventorySlots(inventory, 8, 114);
        this.addHotbarSlots(inventory, 8 , 172);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    // api
    public int getStorage() {
        return this.data.get(ElectricBlockEntity.StorageDataType.STORAGE.getValue());
    }

    public int getCapacity() {
        return this.data.get(ElectricBlockEntity.StorageDataType.CAPACITY.getValue());
    }
}
