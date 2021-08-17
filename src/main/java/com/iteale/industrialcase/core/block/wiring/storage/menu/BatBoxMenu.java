package com.iteale.industrialcase.core.block.wiring.storage.menu;

import com.iteale.industrialcase.core.block.MenuBase;
import com.iteale.industrialcase.core.block.generator.blockentity.GeneratorBlockEntity;
import com.iteale.industrialcase.core.block.wiring.storage.blockentity.BatBoxBlockEntity;
import com.iteale.industrialcase.core.block.wiring.storage.blockentity.ElectricBlockEntity;
import com.iteale.industrialcase.core.registries.MenuTypeRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;

public class BatBoxMenu extends ElectricMenu {

    public BatBoxMenu(int containerId, Inventory inventory) {
        super(containerId, inventory);
    }

    public BatBoxMenu(int containerId, Inventory inventory, Container containerIn, ContainerData dataIn) {
        super(containerId, inventory, containerIn, dataIn);
    }
}
