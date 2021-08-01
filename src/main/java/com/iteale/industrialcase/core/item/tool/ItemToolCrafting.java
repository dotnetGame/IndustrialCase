package com.iteale.industrialcase.core.item.tool;

import com.iteale.industrialcase.core.ICItemGroup;
import com.iteale.industrialcase.core.item.ItemIC;
import com.iteale.industrialcase.core.ref.ItemName;

public abstract class ItemToolCrafting extends ItemIC {
    public ItemToolCrafting(ItemName name, int maximumUses) {
        super(name, new Properties().durability(maximumUses - 1).setNoRepair().tab(ICItemGroup.TAB_IC));
    }
}
