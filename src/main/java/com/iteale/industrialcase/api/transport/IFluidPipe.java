package com.iteale.industrialcase.api.transport;

import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IFluidPipe extends IPipe {
    int getTransferRate();

    FluidTank getTank();

    int getCurrentInnerCapacity();

    int getMaxInnerCapacity();
}
