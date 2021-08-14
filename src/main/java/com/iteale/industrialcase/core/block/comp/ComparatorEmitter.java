package com.iteale.industrialcase.core.block.comp;

import com.iteale.industrialcase.core.block.BlockEntityBase;

public class ComparatorEmitter extends BasicRedstoneComponent {
    public ComparatorEmitter(BlockEntityBase parent) {
        super(parent);
    }

    public void onChange() {
        this.parent.getLevel().updateNeighbourForOutputSignal(
                        this.parent.getBlockPos(), this.parent.getBlockType());
    }
}