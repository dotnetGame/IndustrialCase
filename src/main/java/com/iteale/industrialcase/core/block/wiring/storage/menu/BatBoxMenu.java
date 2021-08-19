package com.iteale.industrialcase.core.block.wiring.storage.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class BatBoxMenu extends ElectricMenu {

    public BatBoxMenu(int containerId, Inventory inventory) {
        super(containerId, inventory);
    }

    public BatBoxMenu(int containerId, Inventory inventory, Container containerIn, ContainerData dataIn) {
        super(containerId, inventory, containerIn, dataIn);
    }
}
