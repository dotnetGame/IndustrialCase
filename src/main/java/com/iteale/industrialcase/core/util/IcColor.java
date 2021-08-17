package com.iteale.industrialcase.core.util;


import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.Map;

public enum IcColor implements StringRepresentable {
    black(DyeColor.BLACK, "dyeBlack"),
    blue(DyeColor.BLUE, "dyeBlue"),
    brown(DyeColor.BROWN, "dyeBrown"),
    cyan(DyeColor.CYAN, "dyeCyan"),
    gray(DyeColor.GRAY, "dyeGray"),
    green(DyeColor.GREEN, "dyeGreen"),
    light_blue(DyeColor.LIGHT_BLUE, "dyeLightBlue"),
    light_gray(DyeColor.LIGHT_GRAY, "dyeLightGray"),
    lime(DyeColor.LIME, "dyeLime"),
    magenta(DyeColor.MAGENTA, "dyeMagenta"),
    orange(DyeColor.ORANGE, "dyeOrange"),
    pink(DyeColor.PINK, "dyePink"),
    purple(DyeColor.PURPLE, "dyePurple"),
    red(DyeColor.RED, "dyeRed"),
    white(DyeColor.WHITE, "dyeWhite"),
    yellow(DyeColor.YELLOW, "dyeYellow");

    public static final IcColor[] values;
    private static final Map<DyeColor, IcColor> mcColorMap;

    IcColor(DyeColor mcColor, String oreDictDyeName) {
        this.mcColor = mcColor;
        this.oreDictDyeName = oreDictDyeName;
    }
    public final DyeColor mcColor;
    public final String oreDictDyeName;

    public String getName() {
        return name();
    }


    public int getId() {
        return ordinal();
    }

    public static IcColor get(DyeColor mcColor) {
        return mcColorMap.get(mcColor);
    }
    static {
        values = values();
        mcColorMap = new EnumMap<>(DyeColor.class);

        for (IcColor color : values)
            mcColorMap.put(color.mcColor, color);
    }

    @Override
    public String getSerializedName() {
        return name();
    }
}
