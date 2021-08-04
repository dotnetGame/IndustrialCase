package com.iteale.industrialcase.core.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class CableBlock extends CableBase {
    public final int insulation;
    public final float thickness;
    public final double loss;
    public final int capacity;

    public CableBlock(int insulation, float thickness, double loss, int capacity) {
        super(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak(), thickness);
        this.insulation = insulation;
        this.thickness = thickness;
        this.loss = loss;
        this.capacity = capacity;
    }

    protected boolean canConnect(IWorld world, BlockPos pos, Direction direction) {
        BlockState neighbour = world.getBlockState(pos);
        ITag<Block> cablesTag = BlockTags.getAllTags().getTag(new ResourceLocation("industrialcase", "cables"));
        if (cablesTag.contains(neighbour.getBlock())) {
            return true;
        } else {
            return false;
        }
    }
}
