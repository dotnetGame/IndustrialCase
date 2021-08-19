package com.iteale.industrialcase.core;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IHasGui extends Container {
    BaseContainerBlockEntity getGuiContainer(Player paramEntityPlayer);

    @OnlyIn(Dist.CLIENT)
    Screen getGui(Player paramEntityPlayer, boolean paramBoolean);

    void onGuiClosed(Player paramEntityPlayer);
}
