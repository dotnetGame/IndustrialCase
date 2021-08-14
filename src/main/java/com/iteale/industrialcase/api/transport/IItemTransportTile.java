package com.iteale.industrialcase.api.transport;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public interface IItemTransportTile extends IPipe {
    int putItems(ItemStack itemStack, Direction facing, boolean simulate);

    ItemStack getContents();

    void setContents(ItemStack stack);

    int getMaxStackSizeAllowed();

    int getTransferRate();
}
