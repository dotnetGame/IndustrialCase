package com.iteale.industrialcase.core.item.tool;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.ref.ItemName;
import net.minecraft.item.Item;

public class ItemToolHammer extends ItemToolCrafting {
    public ItemToolHammer() {
        super(new Item.Properties()
                .durability(80 - 1)
                .setNoRepair()
                .tab(IndustrialCase.TAB_IC)
        );
    }
}
