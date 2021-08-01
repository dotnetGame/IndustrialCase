package com.iteale.industrialcase.core.item;

import com.iteale.industrialcase.core.ref.ItemName;
import net.minecraft.item.Item;

public class ItemIC extends Item {
    public ItemIC(ItemName name, Properties properties) {
        super(properties);
        if (name != null) {
            this.setRegistryName(name.getPath());
        }
    }
}
