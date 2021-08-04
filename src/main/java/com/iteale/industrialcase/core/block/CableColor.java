package com.iteale.industrialcase.core.block;

import net.minecraft.util.IStringSerializable;

public enum CableColor implements IStringSerializable {
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
