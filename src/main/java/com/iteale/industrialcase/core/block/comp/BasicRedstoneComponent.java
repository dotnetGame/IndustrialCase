package com.iteale.industrialcase.core.block.comp;

import com.iteale.industrialcase.core.block.BlockEntityBase;

import java.util.function.IntSupplier;

public abstract class BasicRedstoneComponent
        extends BlockEntityComponent
{
    private int level;
    private IntSupplier update;

    public BasicRedstoneComponent(BlockEntityBase parent) {
        super(parent);
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int newLevel) {
        if (newLevel == this.level)
            return;  this.level = newLevel;
        onChange();
    }


    public abstract void onChange();

    public boolean enableWorldTick() {
        return (this.update != null);
    }

    public void onWorldTick() {
        assert this.update != null;

        setLevel(this.update.getAsInt());
    }

    public void setUpdate(IntSupplier update) {
        this.update = update;
    }
}
