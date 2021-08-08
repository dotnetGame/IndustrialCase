package com.iteale.industrialcase.core.item;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class ItemTreetap extends ItemIC {
    public ItemTreetap() {
        super(new Item.Properties()
                .durability(6)
                .tab(IndustrialCase.TAB_IC));
    }
}
