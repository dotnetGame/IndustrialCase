package com.iteale.industrialcase.core.gui;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public abstract class Button<T extends Button<T>> extends GuiElement<T> {
    private static final int iconSize = 16;
    private final IClickHandler handler;

    protected Button(GuiIC<?> gui, int x, int y, int width, int height, IClickHandler handler) {
        super(gui, x, y, width, height);

        this.handler = handler;
    }
    private Supplier<String> textProvider; private Supplier<ItemStack> iconProvider;
    public T withText(final String text) {
        return withText(new Supplier<String>()
        {
            public String get() {
                return text;
            }
        });
    }


    public T withText(Supplier<String> textProvider) {
        this.textProvider = textProvider;

        return (T)this;
    }


    public T withIcon(Supplier<ItemStack> iconProvider) {
        this.iconProvider = iconProvider;

        return (T)this;
    }

    protected int getTextColor(int mouseX, int mouseY) {
        return 14540253;
    }

    public void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY){
        if (this.textProvider != null) {
            String text = (String)this.textProvider.get();

            if (text != null && !text.isEmpty()) {
                this.gui.drawXYCenteredString(poseStack, this.x + this.width / 2, this.y + this.height / 2, text, getTextColor(mouseX, mouseY), true);
            }
        } else if (this.iconProvider != null) {
            ItemStack stack = (ItemStack)this.iconProvider.get();

            if (stack != null && stack.getItem() != null) {
                // RenderHelper.enableGUIStandardItemLighting();
                this.gui.drawItem(this.x + (this.width - 16) / 2, this.y + (this.height - 16) / 2, stack);
                // RenderHelper.disableStandardItemLighting();
            }
        }
    }

    protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
        this.gui.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));

        this.handler.onClick(button);

        return false;
    }
}