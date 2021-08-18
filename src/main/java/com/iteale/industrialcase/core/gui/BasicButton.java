package com.iteale.industrialcase.core.gui;


import com.mojang.blaze3d.vertex.PoseStack;

public class BasicButton extends Button<BasicButton> {
    public static BasicButton create(GuiIC<?> gui, int x, int y, IClickHandler handler, ButtonStyle style) {
        return new BasicButton(gui, x, y, handler, style);
    }
    private final ButtonStyle style;
    protected BasicButton(GuiIC<?> gui, int x, int y, IClickHandler handler, ButtonStyle style) {
        super(gui, x, y, style.width, style.height, handler);

        this.style = style;
    }


    public void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY){
        // bindCommonTexture();

        this.gui.drawTexturedRect(this.x, this.y, this.style.width, this.style.height, this.style.u, this.style.v);

        super.renderBg(poseStack, partialTicks, mouseX, mouseY);
    }

    public enum ButtonStyle {
        AdvMinerReset(192, 32, 36, 15),
        AdvMinerMode(228, 32, 18, 15),
        AdvMinerSilkTouch(192, 47, 18, 15); final int u; final int v;

        ButtonStyle(int u, int v, int width, int height) {
            this.u = u;
            this.v = v;
            this.width = width;
            this.height = height;
        }

        final int width;
        final int height;
    }
}
