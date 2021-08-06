package com.iteale.industrialcase.core.item.tool;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemToolHammer extends ItemToolCrafting {
    public ItemToolHammer() {
        super(new Item.Properties()
                .durability(80 - 1)
                .setNoRepair()
                .tab(IndustrialCase.TAB_IC)
        );
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        if (stack.is(ItemRegistry.FORGE_HAMMER.get())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        if (!hasContainerItem(itemStack))
        {
            return ItemStack.EMPTY;
        }
        ItemStack newItemStack = itemStack.copy();
        int damage = newItemStack.getDamageValue();

        if (damage + 1 > newItemStack.getMaxDamage()) {
            return ItemStack.EMPTY;
        } else {
            newItemStack.setDamageValue(damage + 1);
            return newItemStack;
        }
    }
}
