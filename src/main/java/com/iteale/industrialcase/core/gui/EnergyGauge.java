package com.iteale.industrialcase.core.gui;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.BlockEntityBase;
import com.iteale.industrialcase.core.block.comp.Energy;
import com.iteale.industrialcase.core.util.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.*;
import java.util.function.Supplier;


public class EnergyGauge extends Gauge<EnergyGauge>
{
    private static final boolean useCleanEnergyValues = false;
    private Supplier<Map<String, Float>> dataFn;

    public static EnergyGauge asBar(GuiIC<?> gui, int x, int y, Supplier<Map<String, Float>> data) {
        return new EnergyGauge(gui, x, y, data, EnergyGaugeStyle.Bar);
    }

    public static EnergyGauge asBolt(GuiIC<?> gui, int x, int y, Supplier<Map<String, Float>> data) {
        return new EnergyGauge(gui, x, y, data, EnergyGaugeStyle.Bolt);
    }

    public EnergyGauge(GuiIC<?> gui, int x, int y, Supplier<Map<String, Float>> data, EnergyGaugeStyle style) {
        super(gui, x, y, style.properties);
        dataFn = data;
    }

    @Override
    public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
        if (contains(mouseX, mouseY)) {
            Map<String, Float> data = dataFn.get();
            double amount = data.get("storage");
            double capacity = data.get("capacity");

            List<FormattedText> tooltips = new ArrayList<FormattedText>();
            tooltips.add(new TextComponent(Util.toSiString(amount, 4) + '/' + Util.toSiString(capacity, 4) + ' ' + "EU"));
            Font font = this.gui.getMinecraft().font;
            net.minecraftforge.fmlclient.gui.GuiUtils.drawHoveringText(poseStack, tooltips, mouseX, mouseY, this.gui.width, this.gui.height, -1, font);
        }
    }

    protected double getRatio() {
        Map<String, Float> data = dataFn.get();
        return data.get("fillRatio");
    }

    public enum EnergyGaugeStyle {
        Bar((new Gauge.GaugePropertyBuilder(132, 43, 24, 9, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-4, -11, 32, 32, 128, 0).build()),
        Bolt((new Gauge.GaugePropertyBuilder(116, 65, 7, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withBackground(-4, -1, 16, 16, 96, 64).build()),
        StirlingBar((new Gauge.GaugePropertyBuilder(176, 15, 58, 14, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withTexture(new ResourceLocation(IndustrialCase.MODID, "textures/gui/container/guistirlinggenerator")).withBackground(59, 33).build());
        private static final Map<String, EnergyGaugeStyle> map = getMap();
        public final String name;
        public final Gauge.GaugeProperties properties;

        EnergyGaugeStyle(Gauge.GaugeProperties properties) {
            this.name = name().toLowerCase(Locale.ENGLISH);
            this.properties = properties;
        }

        public static EnergyGaugeStyle get(String name) {
            return map.get(name);
        }

        private static Map<String, EnergyGaugeStyle> getMap() {
            EnergyGaugeStyle[] values = values();
            Map<String, EnergyGaugeStyle> ret = new HashMap<>(values.length);
            for (EnergyGaugeStyle style : values)
                ret.put(style.name, style);
            return ret;
        }
    }
}

