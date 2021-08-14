package com.iteale.industrialcase.core.gui;

import com.iteale.industrialcase.core.block.BlockEntityBase;
import com.iteale.industrialcase.core.block.comp.Energy;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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


    protected List<String> getToolTip() {
        List<String> ret = super.getToolTip();

        Map<String, Float> data = dataFn.get();
        double amount = data.get("storage");
        double capacity = data.get("capacity");

        ret.add(Util.toSiString(amount, 4) + '/' +
                Util.toSiString(capacity, 4) + ' ' + "EU");

        return ret;
    }


    protected double getRatio() {
        Map<String, Float> data = dataFn.get();
        return data.get("fillRatio");
    }

    public enum EnergyGaugeStyle {
        Bar((new Gauge.GaugePropertyBuilder(132, 43, 24, 9, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-4, -11, 32, 32, 128, 0).build()),
        Bolt((new Gauge.GaugePropertyBuilder(116, 65, 7, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withBackground(-4, -1, 16, 16, 96, 64).build()),
        StirlingBar((new Gauge.GaugePropertyBuilder(176, 15, 58, 14, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withTexture(new ResourceLocation("ic2", "textures/gui/GUIStirlingGenerator.png")).withBackground(59, 33).build());
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

