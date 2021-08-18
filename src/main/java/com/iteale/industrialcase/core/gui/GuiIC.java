package com.iteale.industrialcase.core.gui;


import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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


    public void drawTexturedRect(double x, double y, double width, double height, double texX, double texY) {
        drawTexturedRect(x, y, width, height, texX, texY, false);
    }

    public void drawTexturedRect(double x, double y, double width, double height, double texX, double texY, boolean mirrorX) {
        drawTexturedRect(x, y, width, height, texX / 256.0D, texY / 256.0D, (texX + width) / 256.0D, (texY + height) / 256.0D, mirrorX);
    }

    public void drawTexturedRect(double x, double y, double width, double height, double uS, double vS, double uE, double vE, boolean mirrorX) {
        x += this.leftPos;
        y += this.topPos;
        double xE = x + width;
        double yE = y + height;
        if (mirrorX) {
            double tmp = uS;
            uS = uE;
            uE = tmp;
        }
        /*
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        BufferBuilder tessellator = RenderSystem.renderThreadTesselator().getBuilder();

        tessellator.begin(VertexFormat.Mode.QUADS, new VertexFormat());
        tessellator.vertex(x, y, 0);
        GlStateManager.color();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos().tex(uS, vS).endVertex();
        worldrenderer.pos(x, yE, this.zLevel).tex(uS, vE).endVertex();
        worldrenderer.pos(xE, yE, this.zLevel).tex(uE, vE).endVertex();
        worldrenderer.pos(xE, y, this.zLevel).tex(uE, vS).endVertex();
        tessellator.draw();
         */
    }

    public void drawSprite(double x, double y, double width, double height, TextureAtlasSprite sprite, int color, double scale, boolean fixRight, boolean fixBottom) {
        // if (sprite == null)
        //     sprite = this.minecraft.getTextureMapBlocks().getMissingSprite();
        x += this.leftPos;
        y += this.topPos;
        scale *= 16.0D;
        double spriteUS = sprite.getU0();
        double spriteVS = sprite.getV0();
        double spriteWidth = sprite.getU1() - spriteUS;
        double spriteHeight = sprite.getV1() - spriteVS;
        int a = color >>> 24 & 0xFF;
        int r = color >>> 16 & 0xFF;
        int g = color >>> 8 & 0xFF;
        int b = color & 0xFF;
        /*
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        double xS;
        for (xS = x; xS < x + width; xS += maxWidth) {
            double uS, maxWidth;
            if (xS == x && fixRight && (maxWidth = width % scale) > 0.0D) {
                uS = spriteUS + spriteWidth * (1.0D - maxWidth / scale);
            } else {
                maxWidth = scale;
                uS = spriteUS;
            }
            double xE = Math.min(xS + maxWidth, x + width);
            double uE = uS + (xE - xS) / scale * spriteWidth;
            double yS;
            for (yS = y; yS < y + height; yS += maxHeight) {
                double vS, maxHeight;
                if (yS == y && fixBottom && (maxHeight = height % scale) > 0.0D) {
                    vS = spriteVS + spriteHeight * (1.0D - maxHeight / scale);
                } else {
                    maxHeight = scale;
                    vS = spriteVS;
                }
                double yE = Math.min(yS + maxHeight, y + height);
                double vE = vS + (yE - yS) / scale * spriteHeight;
                buffer.pos(xS, yS, this.zLevel).tex(uS, vS).color(r, g, b, a).endVertex();
                buffer.pos(xS, yE, this.zLevel).tex(uS, vE).color(r, g, b, a).endVertex();
                buffer.pos(xE, yE, this.zLevel).tex(uE, vE).color(r, g, b, a).endVertex();
                buffer.pos(xE, yS, this.zLevel).tex(uE, vS).color(r, g, b, a).endVertex();
            }
        }
        tessellator.draw();
         */
    }

    public void drawItem(int x, int y, ItemStack stack) {
        this.itemRenderer.renderGuiItem(stack, this.leftPos + x, this.topPos + y);
    }

    public void drawItemStack(int x, int y, ItemStack stack) {
        drawItem(x, y, stack);
        this.itemRenderer.renderGuiItemDecorations(this.font, stack, this.leftPos + x, this.topPos + y, null);
    }

    public void drawColoredRect(int x, int y, int width, int height, int color) {
        x += this.leftPos;
        y += this.topPos;
        // drawRect(x, y, x + width, y + height, color);
    }

    public int drawString(PoseStack poseStack, int x, int y, String text, int color, boolean shadow) {
        if (shadow)
            return this.font.drawShadow(poseStack, text, (this.leftPos + x), (this.topPos + y), color) - this.leftPos;
        else
            return this.font.draw(poseStack, text, (this.leftPos + x), (this.topPos + y), color) - this.leftPos;
    }

    public void drawXCenteredString(PoseStack poseStack, int x, int y, String text, int color, boolean shadow) {
        drawCenteredString(poseStack, x, y, text, color, shadow, true, false);
    }

    public void drawXYCenteredString(PoseStack poseStack, int x, int y, String text, int color, boolean shadow) {
        drawCenteredString(poseStack, x, y, text, color, shadow, true, true);
    }

    public void drawCenteredString(PoseStack poseStack, int x, int y, String text, int color, boolean shadow, boolean centerX, boolean centerY) {
        if (centerX)
            x -= getStringWidth(text) / 2;
        if (centerY)
            y -= 4;
        this.font.draw(poseStack, text, this.leftPos + x, this.topPos + y, color);
    }

    public int getStringWidth(String text) {
        return this.font.width(text);
    }

    protected IClickHandler createEventSender(final int event) {
        /*
        if ((this.container.base instanceof BlockEntity)
            return new IClickHandler() {
                public void onClick(MouseButton button) {
                    ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)((ContainerBase)GuiIC2.this.container).base, event);
                }
            };
         */
        throw new IllegalArgumentException("not applicable for " + this.container);
    }

    public T getContainer() {
        return this.container;
    }
}
