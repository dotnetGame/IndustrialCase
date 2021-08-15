package com.iteale.industrialcase.core.gui;

import java.util.Map;
import java.util.function.Supplier;

public class FuelGauge extends Gauge<EnergyGauge> {
    private Supplier<Map<String, Float>> dataFn;

    public static FuelGauge create(GuiIC<?> gui, int x, int y, Supplier<Map<String, Float>> data) {
        return new FuelGauge(gui, x, y, data);
    }

    public FuelGauge(GuiIC<?> gui, int x, int y, Supplier<Map<String, Float>> data) {
        super(gui, x, y, GaugeStyle.Fuel.properties);
        dataFn = data;
    }

    @Override
    protected double getRatio() {
        Map<String, Float> data = dataFn.get();
        double fuel = data.get("fuel");
        double totalFuel = data.get("totalFuel");
        return fuel/totalFuel;
    }
}
