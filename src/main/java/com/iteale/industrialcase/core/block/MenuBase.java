package com.iteale.industrialcase.core.block;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nullable;

public abstract class MenuBase extends AbstractContainerMenu {
    protected MenuBase(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    protected void addInventorySlots(Inventory inventory, int x, int y) { // 8, 79
        for(int iy = 0; iy < 3; ++iy) {
            for(int ix = 0; ix < 9; ++ix) {
                this.addSlot(new Slot(inventory, ix + iy * 9 + 9, x + ix * 18, y + iy * 18));
            }
        }
    }

    protected void addHotbarSlots(Inventory inventory, int x, int y) { // 8, 137
        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, x + l * 18, y));
        }
    }
}
