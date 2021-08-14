package com.iteale.industrialcase.core.gui;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.function.Supplier;

public abstract class GuiElement<T extends GuiElement<T>>
{
    protected static final int hoverColor = -2130706433;

    protected GuiElement(GuiIC<?> gui, int x, int y, int width, int height) {
        if (width < 0) throw new IllegalArgumentException("negative width");
        if (height < 0) throw new IllegalArgumentException("negative height");

        this.gui = gui;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public final boolean isEnabled() {
        return (this.enableHandler == null || this.enableHandler.isEnabled());
    }

    public boolean contains(int x, int y) {
        return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
    }

    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks){}

    public void renderLabels(PoseStack poseStack, int mouseX, int mouseY){}

    public void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY){}

    public void bindTexture(int textureId, ResourceLocation texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(textureId, texture);
    }

    private static final Method hasMethod(Class<?> cls, String name, Class<?>... params) {
        try {
            return !cls.getDeclaredMethod(name, params).isAnnotationPresent((Class)SkippedMethod.class) ? Method.PRESENT : Method.SKIPPED;
        } catch (NoSuchMethodException e) {
            return Method.MISSING;
        }
    }

    public final Subscriptions getSubscriptions() {
        Class<?> cls = getClass();
        Subscriptions subscriptions = SUBSCRIPTIONS.get(cls);

        if (subscriptions == null) {
            Method tick = Method.MISSING, background = Method.MISSING, mouseClick = Method.MISSING, mouseDrag = Method.MISSING, mouseRelease = Method.MISSING, mouseScroll = Method.MISSING, key = Method.MISSING;

            while (cls != GuiElement.class && (!tick.hasSeen() || !background.hasSeen() || !mouseClick.hasSeen() || !mouseDrag.hasSeen() || !mouseRelease.hasSeen() || !mouseScroll.hasSeen() || !key.hasSeen())) {
                if (!tick.hasSeen()) tick = hasMethod(cls, "tick");
                if (!background.hasSeen()) background = hasMethod(cls, "drawBackground", int.class, int.class);
                if (!mouseClick.hasSeen()) mouseClick = hasMethod(cls, "onMouseClick", int.class, int.class, GuiScreenEvent.MouseClickedEvent.class);
                if (!mouseClick.hasSeen()) mouseClick = hasMethod(cls, "onMouseClick", int.class, int.class, GuiScreenEvent.MouseClickedEvent.class, boolean.class);
                if (!mouseDrag.hasSeen()) mouseDrag = hasMethod(cls, "onMouseDrag", int.class, int.class, GuiScreenEvent.MouseDragEvent.class, long.class);
                if (!mouseDrag.hasSeen()) mouseDrag = hasMethod(cls, "onMouseDrag", int.class, int.class, GuiScreenEvent.MouseDragEvent.class, long.class, boolean.class);
                if (!mouseRelease.hasSeen()) mouseRelease = hasMethod(cls, "onMouseRelease", int.class, int.class, GuiScreenEvent.MouseReleasedEvent.class);
                if (!mouseRelease.hasSeen()) mouseRelease = hasMethod(cls, "onMouseRelease", int.class, int.class, GuiScreenEvent.MouseReleasedEvent.class, boolean.class);
                if (!mouseScroll.hasSeen()) mouseScroll = hasMethod(cls, "onMouseScroll", int.class, int.class, ScrollDirection.class);
                if (!key.hasSeen()) key = hasMethod(cls, "onKeyTyped", char.class, int.class);

                cls = cls.getSuperclass();
            }

            subscriptions = new Subscriptions(tick.isPresent(), background.isPresent(), mouseClick.isPresent(), mouseDrag.isPresent(), mouseRelease.isPresent(), mouseScroll.isPresent(), key.isPresent());

            SUBSCRIPTIONS.put(getClass(), subscriptions);
        }

        return subscriptions;
    }

    protected List<String> getToolTip() {
        return new ArrayList<>();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    protected static @interface SkippedMethod {}

    private enum Method
    {
        PRESENT, SKIPPED, MISSING;

        boolean hasSeen() {
            return (this != MISSING);
        }

        boolean isPresent() {
            return (this == PRESENT);
        } }
    public static final class Subscriptions { public final boolean tick; public final boolean background;
        public final boolean mouseClick;

        Subscriptions(boolean tick, boolean background, boolean mouseClick, boolean mouseDrag, boolean mouseRelease, boolean mouseScroll, boolean key) {
            this.tick = tick;
            this.background = background;
            this.mouseClick = mouseClick;
            this.mouseDrag = mouseDrag;
            this.mouseRelease = mouseRelease;
            this.mouseScroll = mouseScroll;
            this.key = key;
        }
        public final boolean mouseDrag; public final boolean mouseRelease; public final boolean mouseScroll; public final boolean key;

        public String toString() {
            return String.format("tick: %s, background: %s, mouseClick: %s, mouseDrag: %s, mouseRelease: %s, mouseScroll: %s, key: %s",
                    this.tick, this.background, this.mouseClick, this.mouseDrag, this.mouseRelease, this.mouseScroll, this.key);
        }
    }

    public static final ResourceLocation commonTexture = new ResourceLocation("ic2", "textures/gui/common.png");
    private static final Map<Class<?>, Subscriptions> SUBSCRIPTIONS = new HashMap<>();
    protected final GuiIC<?> gui;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private IEnableHandler enableHandler;
    private Supplier<String> tooltipProvider;
}
