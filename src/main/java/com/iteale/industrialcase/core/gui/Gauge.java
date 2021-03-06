package com.iteale.industrialcase.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class Gauge<T extends Gauge<T>> extends GuiElement<T> {
    protected final GaugeProperties properties;

    protected Gauge(GuiIC<?> gui, int x, int y, GaugeProperties properties) {
        super(gui, x + properties.hoverXOffset, y + properties.hoverYOffset, properties.hoverWidth, properties.hoverHeight);
        this.properties = properties;
    }

    protected abstract double getRatio();

    protected boolean isActive(double ratio) {
        return ratio > 0.0D;
    }

    public void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY){
        double ratio = getRatio();

        if (ratio <= 0.0D && this.properties.bgWidth <= 0) {
            return;
        }

        bindTexture(0, this.properties.texture);

        int x = (this.x - this.properties.hoverXOffset);
        int y = (this.y - this.properties.hoverYOffset);

        if (this.properties.bgWidth >= 0) {
            boolean active = isActive(ratio);

            this.gui.drawTexturedRect(
                    poseStack,
                    x + this.properties.bgXOffset,
                    y + this.properties.bgYOffset,
                    this.properties.bgWidth,
                    this.properties.bgHeight,
                    active ? this.properties.uBgActive : this.properties.uBgInactive,
                    active ? this.properties.vBgActive : this.properties.vBgInactive);

            if (ratio <= 0.0D) {
                return;
            }
        }

        ratio = Math.min(ratio, 1.0D);

        int u = this.properties.uInner;
        int v = this.properties.vInner;
        int width = this.properties.innerWidth;
        int height = this.properties.innerHeight;
        int size = this.properties.vertical ? height : width;
        double renderSize = ratio * size;

        if (!this.properties.smooth) renderSize = Math.round(renderSize);
        if (renderSize <= 0.0D)
            return;
        if (this.properties.vertical) {
            if (this.properties.reverse) {
                v += height - renderSize;
                y += height - renderSize;
            }

            height = (int)renderSize;
        } else {
            if (this.properties.reverse) {
                u += width - renderSize;
                x += width - renderSize;
            }

            width = (int)renderSize;
        }

        this.gui.drawTexturedRect(poseStack, x, y, width, height, u, v);
    }

    public static class GaugePropertyBuilder
    {
        private final short uInner;

        private final short vInner;

        private final short innerWidth;

        private final short innerHeight;

        private short hoverXOffset;

        private short hoverYOffset;

        private short hoverWidth;

        private short hoverHeight;

        private short bgXOffset;

        private short bgYOffset;

        private short bgWidth;

        private short bgHeight;

        private short uBgInactive;

        private short vBgInactive;

        private short uBgActive;
        private short vBgActive;
        private final boolean vertical;
        private final boolean reverse;
        private boolean smooth;
        private ResourceLocation texture;

        public GaugePropertyBuilder withHoverBorder(int border) {
            this.hoverXOffset = toShort(-border);
            this.hoverYOffset = toShort(-border);
            this.hoverWidth = toShort(this.innerWidth + 2 * border);
            this.hoverHeight = toShort(this.innerHeight + 2 * border);
            return this;
        }

        public GaugePropertyBuilder withHover(int hoverXOffset, int hoverYOffset, int hoverWidth, int hoverHeight) {
            this.hoverXOffset = toShort(hoverXOffset);
            this.hoverYOffset = toShort(hoverYOffset);
            this.hoverWidth = toShort(hoverWidth);
            this.hoverHeight = toShort(hoverHeight);
            return this;
        }

        public GaugePropertyBuilder withBackground(int uBg, int vBg) {
            return withBackground(0, 0, this.innerWidth, this.innerHeight, uBg, vBg);
        }

        public GaugePropertyBuilder withBackground(int bgXOffset, int bgYOffset, int bgWidth, int bgHeight, int uBg, int vBg) {
            return withBackground(bgXOffset, bgYOffset, bgWidth, bgHeight, uBg, vBg, uBg, vBg);
        }

        public GaugePropertyBuilder withBackground(int uBgInactive, int vBgInactive, int uBgActive, int vBgActive) {
            return withBackground(0, 0, this.innerWidth, this.innerHeight, uBgInactive, vBgInactive, uBgActive, vBgActive);
        }

        public GaugePropertyBuilder withBackground(int bgXOffset, int bgYOffset, int bgWidth, int bgHeight, int uBgInactive, int vBgInactive, int uBgActive, int vBgActive) {
            this.bgXOffset = toShort(bgXOffset);
            this.bgYOffset = toShort(bgYOffset);
            this.bgWidth = toShort(bgWidth);
            this.bgHeight = toShort(bgHeight);
            this.uBgInactive = toShort(uBgInactive);
            this.vBgInactive = toShort(vBgInactive);
            this.uBgActive = toShort(uBgActive);
            this.vBgActive = toShort(vBgActive);
            return this;
        }

        public GaugePropertyBuilder withSmooth(boolean smooth) {
            this.smooth = smooth;
            return this;
        }

        public GaugePropertyBuilder withTexture(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        public Gauge.GaugeProperties build() {
            return new Gauge.GaugeProperties(this.uInner, this.vInner, this.innerWidth, this.innerHeight, this.hoverXOffset, this.hoverYOffset, this.hoverWidth, this.hoverHeight, this.bgXOffset, this.bgYOffset, this.bgWidth, this.bgHeight, this.uBgInactive, this.vBgInactive, this.uBgActive, this.vBgActive, this.vertical, this.reverse, this.smooth, this.texture);
        }

        private static short toShort(int value) {
            return (short)value;
        }

        public enum GaugeOrientation
        {
            Up(true, true),
            Down(true, false),
            Left(false, true),
            Right(false, false);
            final boolean vertical;
            final boolean reverse;

            GaugeOrientation(boolean vertical, boolean reverse) {
                this.vertical = vertical;
                this.reverse = reverse;
            }
        }

        public GaugePropertyBuilder(int uInner, int vInner, int innerWidth, int innerHeight, GaugeOrientation dir) {
            this.smooth = true;
            this.texture = GuiElement.commonTexture;
            this.uInner = toShort(uInner);
            this.vInner = toShort(vInner);
            this.innerWidth = this.hoverWidth = toShort(innerWidth);
            this.innerHeight = this.hoverHeight = toShort(innerHeight);
            this.vertical = dir.vertical;
            this.reverse = dir.reverse;
        }
    }

    public static class GaugeProperties {
        public final short uInner;
        public final short vInner;
        public final short innerWidth;
        public final short innerHeight;
        public final short hoverXOffset;
        public final short hoverYOffset;
        public final short hoverWidth;
        public final short hoverHeight;
        public final short bgXOffset;
        public final short bgYOffset;

        public GaugeProperties(int uInner, int vInner, int innerWidth, int innerHeight, int hoverXOffset, int hoverYOffset, int hoverWidth, int hoverHeight, int bgXOffset, int bgYOffset, int bgWidth, int bgHeight, int uBgInactive, int vBgInactive, int uBgActive, int vBgActive, boolean vertical, boolean reverse, boolean smooth, ResourceLocation texture) {
            this.uInner = (short)uInner;
            this.vInner = (short)vInner;
            this.innerWidth = (short)innerWidth;
            this.innerHeight = (short)innerHeight;

            this.hoverXOffset = (short)hoverXOffset;
            this.hoverYOffset = (short)hoverYOffset;
            this.hoverWidth = (short)hoverWidth;
            this.hoverHeight = (short)hoverHeight;

            this.bgXOffset = (short)bgXOffset;
            this.bgYOffset = (short)bgYOffset;
            this.bgWidth = (short)bgWidth;
            this.bgHeight = (short)bgHeight;

            this.uBgInactive = (short)uBgInactive;
            this.vBgInactive = (short)vBgInactive;
            this.uBgActive = (short)uBgActive;
            this.vBgActive = (short)vBgActive;

            this.vertical = vertical;
            this.reverse = reverse;
            this.smooth = smooth;
            this.texture = texture;
        }


        public final short bgWidth;

        public final short bgHeight;

        public final short uBgInactive;
        public final short vBgInactive;
        public final short uBgActive;
        public final short vBgActive;
        public final boolean vertical;
        public final boolean reverse;
        public final boolean smooth;
        public final ResourceLocation texture;
    }


    public static interface IGaugeStyle
    {
        Gauge.GaugeProperties getProperties();
    }

    public enum GaugeStyle implements IGaugeStyle
    {
        Fuel((new Gauge.GaugePropertyBuilder(112, 80, 13, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withHover(0, 0, 14, 14).withBackground(0, 0, 16, 16, 96, 80).build()),
        Bucket((new Gauge.GaugePropertyBuilder(110, 111, 14, 16, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withBackground(96, 111).build()),
        ProgressWind((new Gauge.GaugePropertyBuilder(242, 91, 13, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withBackground(242, 63, 242, 77).build()),
        ProgressArrow((new Gauge.GaugePropertyBuilder(165, 16, 22, 15, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, 0, 32, 16, 160, 0).build()),
        ProgressArrowModern((new Gauge.GaugePropertyBuilder(86, 234, 16, 10, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(0, 0, 16, 10, 70, 234).build()),
        ProgressArrowModernReversed((new Gauge.GaugePropertyBuilder(70, 244, 16, 10, Gauge.GaugePropertyBuilder.GaugeOrientation.Left)).withBackground(0, 0, 16, 10, 86, 244).build()),
        ProgressCrush((new Gauge.GaugePropertyBuilder(165, 52, 21, 11, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, -3, 32, 16, 160, 32).build()),
        ProgressTriangle((new Gauge.GaugePropertyBuilder(165, 80, 22, 15, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, 0, 32, 16, 160, 64).build()),
        ProgressDrop((new Gauge.GaugePropertyBuilder(165, 112, 22, 15, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, 0, 32, 16, 160, 96).build()),
        ProgressRecycler((new Gauge.GaugePropertyBuilder(133, 80, 18, 15, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, 0, 32, 16, 128, 64).build()),
        ProgressMetalFormer((new Gauge.GaugePropertyBuilder(200, 19, 46, 9, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-8, -3, 64, 16, 192, 0).build()),
        ProgressCentrifuge((new Gauge.GaugePropertyBuilder(252, 33, 3, 28, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withBackground(-1, -1, 5, 30, 246, 32).build()),
        HeatCentrifuge((new Gauge.GaugePropertyBuilder(225, 54, 20, 4, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-1, -1, 22, 6, 224, 47).build()),
        HeatNuclearReactor((new Gauge.GaugePropertyBuilder(0, 243, 100, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/container/nuclear_reactor.png")).build()),
        HeatSteamGenerator((new Gauge.GaugePropertyBuilder(177, 1, 7, 76, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/container/steam_generator.png")).build()),
        CalcificationSteamGenerator((new Gauge.GaugePropertyBuilder(187, 1, 7, 58, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/container/steam_generator.png")).build()),
        ProgressCondenser((new Gauge.GaugePropertyBuilder(1, 185, 82, 7, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/container/condenser.png")).build()),
        HeatFermenter((new Gauge.GaugePropertyBuilder(177, 10, 40, 3, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/container/fermenter.png")).build()),
        ProgressFermenter((new Gauge.GaugePropertyBuilder(177, 1, 40, 7, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/container/fermenter.png")).build()),
        ProgressOreWasher((new Gauge.GaugePropertyBuilder(177, 118, 18, 18, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withTexture(new ResourceLocation("ic2", "textures/gui/container/ore_washing_plant.png")).withBackground(-1, -1, 20, 19, 102, 38).build()),
        ProgressBlockCutter((new Gauge.GaugePropertyBuilder(176, 15, 46, 17, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withTexture(new ResourceLocation("ic2", "textures/gui/container/block_cutter.png")).withBackground(55, 33).build()),
        ProgressLongArrow((new Gauge.GaugePropertyBuilder(176, 15, 34, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withTexture(new ResourceLocation("ic2", "textures/gui/container/canner_classic.png")).withBackground(74, 36).build());

        private static final Map<String, IGaugeStyle> map = getMap();
        private final String name;
        public final Gauge.GaugeProperties properties;

        GaugeStyle(Gauge.GaugeProperties properties) {
            this.name = name().toLowerCase(Locale.ENGLISH);
            this.properties = properties;
        }

        public Gauge.GaugeProperties getProperties() {
            return this.properties;
        }

        public static void addStyle(String name, Gauge.IGaugeStyle style) {
            assert name != null : "Cannot add null name";
            assert style != null : "Cannot add null style";
            if (map.containsKey(name))
                throw new RuntimeException("Duplicate style name for " + name + '!');
            map.put(name, style);
        }

        public static Gauge.IGaugeStyle get(String name) {
            return map.get(name);
        }

        private static Map<String, Gauge.IGaugeStyle> getMap() {
            GaugeStyle[] values = values();
            Map<String, Gauge.IGaugeStyle> ret = new HashMap<>(values.length);
            for (GaugeStyle style : values)
                ret.put(style.name, style);
            return ret;
        }
    }
    public enum Direction
    {
        VERTICAL,
        HORIZONTAL;
    }
}
