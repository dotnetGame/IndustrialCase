package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.block.inventory.InvSlot;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IInventorySlotHolder<P extends BlockEntity> {
    P getParent();

    InvSlot getInventorySlot(String paramString);

    void addInventorySlot(InvSlot paramInvSlot);

    int getBaseIndex(InvSlot paramInvSlot);
}
