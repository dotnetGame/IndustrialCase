package com.iteale.industrialcase.core.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;

public class RubberLeaves extends LeavesBlock {
    public RubberLeaves() {
        super(
                AbstractBlock.Properties.of(Material.LEAVES)
                        .strength(0.2F, 0.2F)
                        .sound(SoundType.GRASS)
                        .harvestLevel(1)
                        .harvestTool(ToolType.SHOVEL)
        );
        setRegistryName("resource/plant/rubber_leaves");
    }
}
