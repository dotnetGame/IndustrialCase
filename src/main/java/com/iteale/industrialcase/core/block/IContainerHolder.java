package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.block.invslot.InvSlot;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IContainerHolder<P extends BlockEntity & Container> {
    P getParent();

    InvSlot getContainer(String paramString);

    void addContainer(InvSlot paramContainer);

    int getBaseIndex(InvSlot paramContainer);
}
