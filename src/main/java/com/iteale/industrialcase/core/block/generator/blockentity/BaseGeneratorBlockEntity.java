package com.iteale.industrialcase.core.block.generator.blockentity;

import com.iteale.industrialcase.core.block.BlockEntityInventory;
import com.iteale.industrialcase.core.block.comp.Energy;
import com.iteale.industrialcase.core.block.container.ChargeContainer;
import com.iteale.industrialcase.core.network.GuiSynced;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseGeneratorBlockEntity extends BlockEntityInventory {
    public final ChargeContainer chargeSlot;
    protected final Energy energy;
    @GuiSynced
    public int fuel;
    protected double production;

    protected BaseGeneratorBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, double production, int tier, int maxStorage) {
        super(blockEntityType, pos, state);
        this.fuel = 0;

        this.production = production;
        this.chargeSlot = new ChargeContainer(this, 1);
        this.energy = addComponent(Energy.asBasicSource(this, maxStorage, tier)
                .addManagedSlot(this.chargeSlot));
    }

}
