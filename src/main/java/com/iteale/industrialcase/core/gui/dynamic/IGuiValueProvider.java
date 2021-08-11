package com.iteale.industrialcase.core.gui.dynamic;

public interface IGuiValueProvider {
    double getGuiValue(String paramString);

    public static interface IActiveGuiValueProvider extends IGuiValueProvider {
        boolean isGuiValueActive(String param1String);
    }
}
