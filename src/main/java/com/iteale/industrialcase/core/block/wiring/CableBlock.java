package com.iteale.industrialcase.core.block.wiring;


import com.iteale.industrialcase.core.util.IcColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public class CableBlock extends CableBase {
    public final int insulation;
    public final float thickness;
    public final double loss;
    public final int capacity;
    public final CableType cableType;
    public final IcColor color;

    public CableBlock(CableType cableTypeIn, IcColor colorIn, int insulationIn) {
        super(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak(), cableTypeIn.thickness);
        this.insulation = insulationIn;
        this.thickness = cableTypeIn.thickness;
        this.loss = cableTypeIn.loss;
        this.capacity = cableTypeIn.capacity;
        this.cableType = cableTypeIn;
        this.color = colorIn;
    }

    protected boolean canConnect(LevelAccessor world, BlockPos pos, Direction direction) {
        BlockState neighbour = world.getBlockState(pos);
        Tag<Block> cablesTag = BlockTags.getAllTags().getTag(new ResourceLocation("industrialcase", "cables"));
        Tag<Block> generatorsTag = BlockTags.getAllTags().getTag(new ResourceLocation("industrialcase", "generators"));
        Tag<Block> storagesTag = BlockTags.getAllTags().getTag(new ResourceLocation("industrialcase", "storages"));
        if (cablesTag.contains(neighbour.getBlock()) ||
                generatorsTag.contains(neighbour.getBlock())||
                storagesTag.contains(neighbour.getBlock())) {
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state, this.cableType, this.insulation);
    }
}
