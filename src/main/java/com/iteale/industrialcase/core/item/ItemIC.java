package com.iteale.industrialcase.core.item;


import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.world.item.Item;

public class ItemIC extends Item {
    public ItemIC(Properties properties) {
        super(properties.tab(IndustrialCase.TAB_IC));
    }
}
