package com.iteale.industrialcase.core.block.comp;

import com.iteale.industrialcase.core.block.BlockEntityBase;

public class RedstoneEmitter extends BasicRedstoneComponent {
    public RedstoneEmitter(BlockEntityBase parent) {
        super(parent);
    }


    public void onChange() {
        this.parent.getLevel().updateNeighborsAt(this.parent.getBlockPos(), this.parent.getBlockType());
    }
}
