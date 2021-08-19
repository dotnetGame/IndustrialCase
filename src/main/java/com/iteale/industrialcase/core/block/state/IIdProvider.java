package com.iteale.industrialcase.core.block.state;


public interface IIdProvider
{
    String getName();

    int getId();

    default int getColor() {
        return 16777215;
    }

    default String getModelName() {
        return getName();
    }
}

