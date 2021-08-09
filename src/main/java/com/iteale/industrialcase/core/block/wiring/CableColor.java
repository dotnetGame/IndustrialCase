package com.iteale.industrialcase.core.block.wiring;

import net.minecraft.util.StringRepresentable;

public enum CableColor implements StringRepresentable {
    black,
    blue,
    brown,
    cyan,

    gray,
    green,
    light_blue,
    light_gray,

    lime,
    magenta,
    orange,
    pink,

    purple,
    red,
    white,
    yellow;

    @Override
    public String getSerializedName() {
        return this.name();
    }
}
