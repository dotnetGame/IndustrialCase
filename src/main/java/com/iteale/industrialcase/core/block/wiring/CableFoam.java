package com.iteale.industrialcase.core.block.wiring;

public enum CableFoam {
    None,
    Soft,
    Hardened;
    static {
        values = values();
    }

    public static final CableFoam[] values;
}
