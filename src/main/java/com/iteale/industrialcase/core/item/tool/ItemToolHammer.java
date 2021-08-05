package com.iteale.industrialcase.core.item.tool;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.world.item.Item;

public class ItemToolHammer extends ItemToolCrafting {
    public ItemToolHammer() {
        super(new Item.Properties()
                .durability(80 - 1)
                .setNoRepair()
                .tab(IndustrialCase.TAB_IC)
        );
    }
}
