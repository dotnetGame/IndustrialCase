package com.iteale.industrialcase.core.block.wiring.storage.gui;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.block.wiring.storage.menu.ElectricMenu;
import com.iteale.industrialcase.core.gui.GuiIC;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ElectricScreen<T extends AbstractContainerMenu> extends GuiIC<T> {
    private static final ResourceLocation ELECTRIC_TEXTURE = new ResourceLocation(IndustrialCase.MODID, "textures/gui/container/electric_block.png");

    public ElectricScreen(T container, Inventory inventory, Component component) {
        super(container, inventory, component);
    }

    public ElectricScreen(T container, Inventory inventory, Component component, int ySize) {
        super(container, inventory, component, ySize);
    }

    public ElectricScreen(T container, Inventory inventory, Component component, int xSize, int ySize) {
        super(container, inventory, component, xSize, ySize);
    }

    public ResourceLocation getBackgroundTexture() {
        return ELECTRIC_TEXTURE;
    }
}
