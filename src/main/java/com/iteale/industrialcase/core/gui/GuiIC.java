package com.iteale.industrialcase.core.gui;


import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class GuiIC<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    protected final T container;
    protected final List<GuiElement<?>> elements;
    public static final int textHeight = 8;

    public GuiIC(T container, Inventory inventory, Component component) {
        this(container, inventory, component, 176, 166);
    }

    public GuiIC(T container, Inventory inventory, Component component, int ySize) {
        this(container, inventory, component, 176, ySize);
    }

    public GuiIC(T container, Inventory inventory, Component component, int xSize, int ySize) {
        super(container, inventory, component);

        this.elements = new ArrayList<GuiElement<?>>();
        this.container = container;
        this.imageHeight = ySize;
        this.imageWidth = xSize;

        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 92;
    }


    protected void addElement(GuiElement<?> element) {
        this.elements.add(element);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        super.renderLabels(poseStack, mouseX, mouseY);
        for (GuiElement ele : this.elements) {
            ele.renderLabels(poseStack, mouseX, mouseY);
        }
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
        super.renderTooltip(poseStack, mouseX, mouseY);
        for (GuiElement ele : this.elements) {
            ele.renderTooltip(poseStack, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        // my ui
        ResourceLocation bgTexture = getBackgroundTexture();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, bgTexture);

        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        for (GuiElement ele : this.elements) {
            ele.renderBg(poseStack, partialTicks, mouseX, mouseY);
        }
    }

    public abstract ResourceLocation getBackgroundTexture();

    public void drawTexturedRect(PoseStack poseStack, int x, int y, int w, int h, int u, int v) {
        this.blit(poseStack, leftPos + x, topPos + y, u, v, w, h);
    }

    public T getContainer() {
        return this.container;
    }
}
