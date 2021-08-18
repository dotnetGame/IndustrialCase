package com.iteale.industrialcase.core.ref;


import net.minecraft.world.level.block.Block;

public enum BlockName
{
    te,
    resource,
    leaves,
    rubber_wood,
    sapling,
    scaffold,
    foam,
    fence,
    sheet,
    glass,
    wall,
    mining_pipe,
    reinforced_door,
    dynamite,
    refractory_bricks;
    private Block instance;

    public boolean hasInstance() {
        return (this.instance != null);
    }
    public static final BlockName[] values;

    public <T extends Block> T getInstance() {
        if (this.instance == null) throw new IllegalStateException("the requested block instance for " + name() + " isn't set (yet)");

        return (T)this.instance;
    }

    public <T extends Block> void setInstance(T instance) {
        if (this.instance != null) throw new IllegalStateException("conflicting instance");

        this.instance = (Block)instance;
    }

    static {
        values = values();
    }
}

