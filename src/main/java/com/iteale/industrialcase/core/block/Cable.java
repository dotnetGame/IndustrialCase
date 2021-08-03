package com.iteale.industrialcase.core.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.HashMap;
import java.util.Map;

public class Cable extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP;
    static {
        FACING_TO_PROPERTY_MAP = new HashMap<Direction, BooleanProperty>(){{
            put(Direction.NORTH, NORTH);
            put(Direction.EAST, EAST);
            put(Direction.SOUTH, SOUTH);
            put(Direction.WEST, WEST);
            put(Direction.UP, UP);
            put(Direction.DOWN, DOWN);
        }};
    }
    public Cable() {
        super(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak());
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(NORTH, false)
                        .setValue(EAST, false)
                        .setValue(SOUTH, false)
                        .setValue(WEST, false)
                        .setValue(UP, false)
                        .setValue(DOWN, false)
        );
    }

    @Override
    public void onPlace(BlockState blockState, World world, BlockPos pos, BlockState oldBlockState, boolean p_220082_5_) {

    }

    @Override
    public void neighborChanged(BlockState newBlockState, World world, BlockPos pos, Block oldBlock, BlockPos neighborPos, boolean p_220069_6_) {
        BlockState neighborBlockstate = world.getBlockState(neighborPos);
        Direction direction = getNearDirection(pos, neighborPos);
        if (direction != null) {
            BlockState myBlockState = world.getBlockState(pos);
            BlockState newMyBlockState = myBlockState.setValue(FACING_TO_PROPERTY_MAP.get(direction), false);
            if (neighborBlockstate.is(BlockRegister.COPPER_CABLE)) {
                newMyBlockState = myBlockState.setValue(FACING_TO_PROPERTY_MAP.get(direction), true);
            }
            world.setBlock(pos, newMyBlockState, 2);
        }
    }

    public static Direction getNearDirection(BlockPos from, BlockPos to) {
        BlockPos v = to.subtract(from);
        Direction direction = null;
        if (v.getX() == 0 && v.getY() == 0) {
            if (v.getZ() == 1) direction = Direction.NORTH;
            else if (v.getZ() == -1) direction = Direction.SOUTH;
        } else if (v.getX() == 0 && v.getZ() == 0) {
            if (v.getY() == 1) direction = Direction.UP;
            else if (v.getY() == -1) direction = Direction.DOWN;
        } else if (v.getY() == 0 && v.getZ() == 0) {
            if (v.getX() == 1) direction = Direction.EAST;
            else if (v.getX() == -1) direction = Direction.WEST;
        }
        return direction;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
        builder.add(UP);
        builder.add(DOWN);
    }
}
