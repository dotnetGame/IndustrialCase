package com.iteale.industrialcase.core.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class CableBlock extends CableBase {
    public final int insulation;
    public final float thickness;
    public final double loss;
    public final int capacity;

    public CableBlock(int insulation, float thickness, double loss, int capacity) {
        super(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak(), thickness);
        this.insulation = insulation;
        this.thickness = thickness;
        this.loss = loss;
        this.capacity = capacity;
    }

    protected boolean canConnect(LevelAccessor world, BlockPos pos, Direction direction) {
        BlockState neighbour = world.getBlockState(pos);
        Tag<Block> cablesTag = BlockTags.getAllTags().getTag(new ResourceLocation("industrialcase", "cables"));
        if (cablesTag.contains(neighbour.getBlock())) {
            return true;
        } else {
            return false;
        }
    }
}
