package com.iteale.industrialcase.core.block.comp;

import com.iteale.industrialcase.core.block.TileEntityBlock;

public class ComparatorEmitter extends BasicRedstoneComponent {
    public ComparatorEmitter(TileEntityBlock parent) {
        super(parent);
    }

    public void onChange() {
        this.parent.getLevel().updateNeighbourForOutputSignal(
                        this.parent.getBlockPos(), this.parent.getBlockType());
    }
}