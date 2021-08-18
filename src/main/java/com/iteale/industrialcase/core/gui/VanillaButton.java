package com.iteale.industrialcase.core.gui;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

public class VanillaButton
        extends Button<VanillaButton> {
    public VanillaButton(GuiIC<?> gui, int x, int y, int width, int height, IClickHandler handler) {
        super(gui, x, y, width, height, handler);
    }
    protected IEnableHandler disableHandler;
    public VanillaButton withDisableHandler(IEnableHandler handler) {
        this.disableHandler = handler;

        return this;
    }

    public boolean isDisabled() {
        return (this.disableHandler != null && !this.disableHandler.isEnabled());
    }

    @Override
    public void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        int u, v;
        bindTexture(0, texture);

        if (isDisabled()) {
            u = 0;
            v = 46;
        } else if (!isActive(mouseX, mouseY)) {
            u = 0;
            v = 66;
        } else {
            u = 0;
            v = 86;
        }

        int minLeft = 2;
        int minRight = 2;

        while (this.width < minLeft + minRight) {
            if (minLeft > minRight) {
                minLeft--; continue;
            }
            minRight--;
        }


        int cx = this.x;

        int remainingWidth = this.width;
        int cWidth = Math.min(remainingWidth, 200) - minRight;


        drawVerticalPiece(this.gui, cx, this.y, cWidth, this.height, u, v);
        cx += cWidth;
        remainingWidth -= cWidth;


        while (remainingWidth > 200 - minLeft) {
            cWidth = Math.min(remainingWidth, 200 - minLeft) - minRight;

            drawVerticalPiece(this.gui, cx, this.y, cWidth, this.height, u + minLeft, v);
            cx += cWidth;
            remainingWidth -= cWidth;
        }


        drawVerticalPiece(this.gui, cx, this.y, remainingWidth, this.height, u + 200 - remainingWidth, v);

        super.renderBg(poseStack, partialTicks, mouseX, mouseY);
    }

    private static void drawVerticalPiece(GuiIC<?> gui, int x, int y, int width, int height, int u, int v) {
        int minTop = 2;
        int minBottom = 3;

        while (height < minTop + minBottom) {
            if (minTop > minBottom) {
                minTop--; continue;
            }
            minBottom--;
        }



        int cHeight = Math.min(height, 20) - minBottom;

        gui.drawTexturedRect(x, y, width, cHeight, u, v);
        y += cHeight;
        height -= cHeight;


        while (height > 20 - minTop) {
            cHeight = Math.min(height, 20 - minTop) - minBottom;

            gui.drawTexturedRect(x, y, width, cHeight, u, (v + minTop));
            y += cHeight;
            height -= cHeight;
        }


        gui.drawTexturedRect(x, y, width, height, u, (v + 20 - height));
    }

    protected boolean isActive(int mouseX, int mouseY) {
        return contains(mouseX, mouseY);
    }


    protected int getTextColor(int mouseX, int mouseY) {
        return isDisabled() ? 10526880 : (isActive(mouseX, mouseY) ? 16777120 : 14737632);
    }


    protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
        return isDisabled() ? false : super.onMouseClick(mouseX, mouseY, button);
    }

    private static final ResourceLocation texture = new ResourceLocation("textures/gui/widgets.png");
    private static final int uNormal = 0;
    private static final int vNormal = 66;
    private static final int uHover = 0;
    private static final int vHover = 86;
    private static final int uDisabled = 0;
    private static final int vDisabled = 46;
    private static final int rawWidth = 200;
    private static final int rawHeight = 20;
    private static final int minLeft = 2;
    private static final int minRight = 2;
    private static final int minTop = 2;
    private static final int minBottom = 3;
    private static final int colorNormal = 14737632;
    private static final int colorHover = 16777120;
    private static final int colorDisabled = 10526880;
}

