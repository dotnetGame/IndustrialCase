package com.iteale.industrialcase.core.block.container;

import com.iteale.industrialcase.api.energy.tile.IChargingSlot;

public class ChargeContainer extends ICContainer implements IChargingSlot {
    public ChargeContainer(int sizeIn) {
        super(sizeIn);
    }
    @Override
    public double charge(double amount) {
        return 0;
    }
}
