package com.iteale.industrialcase.core.block.comp;

import com.iteale.industrialcase.core.block.TileEntityBlock;

public class RedstoneEmitter extends BasicRedstoneComponent {
    public RedstoneEmitter(TileEntityBlock parent) {
        super(parent);
    }


    public void onChange() {
        this.parent.getLevel().updateNeighborsAt(this.parent.getBlockPos(), this.parent.getBlockType());
    }
}
