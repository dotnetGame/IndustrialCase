package com.iteale.industrialcase.core.block.resource;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class RubberPlanks extends Block {
    public RubberPlanks() {
        super(BlockBehaviour.Properties.of(Material.WOOD)
                .strength(1.0F, 2.0F)
                .sound(SoundType.WOOD));
    }
}
