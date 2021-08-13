package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.block.container.ICContainer;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IContainerHolder<P extends BlockEntity & Container> {
    P getParent();

    ICContainer getContainer(String paramString);

    void addContainer(ICContainer paramContainer);

    int getBaseIndex(ICContainer paramContainer);
}
