package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.block.invslot.InvSlot;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IInventorySlotHolder<P extends BlockEntity & Container> {
    P getParent();

    InvSlot getInventorySlot(String paramString);

    void addInventorySlot(InvSlot paramInvSlot);

    int getBaseIndex(InvSlot paramInvSlot);
}
