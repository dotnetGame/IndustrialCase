package com.iteale.industrialcase.core.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CableBase extends Block {
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
    protected final VoxelShape[] shapes;
    public CableBase(AbstractBlock.Properties properties, float thickness) {
        super(properties);
        // AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak()
        this.shapes = makeShapes(thickness/2);
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

    private VoxelShape[] makeShapes(float apothem) {
        float f = 0.5F - apothem;
        float g = 0.5F + apothem;
        VoxelShape voxelShapeCore = Block.box((f * 16.0F), (f * 16.0F), (f * 16.0F), (g * 16.0F), (g * 16.0F), (g * 16.0F));
        VoxelShape[] voxelShapeSide = new VoxelShape[Direction.values().length];

        for (int i = 0; i < Direction.values().length; i++) {
            Direction direction = Direction.values()[i];
            voxelShapeSide[i] = VoxelShapes.box(
                    0.5D + Math.min(-apothem, direction.getStepX() * 0.5D),
                    0.5D + Math.min(-apothem, direction.getStepY() * 0.5D),
                    0.5D + Math.min(-apothem, direction.getStepZ() * 0.5D),
                    0.5D + Math.max(apothem, direction.getStepX() * 0.5D),
                    0.5D + Math.max(apothem, direction.getStepY() * 0.5D),
                    0.5D + Math.max(apothem, direction.getStepZ() * 0.5D)
            );
        }

        VoxelShape[] voxelShapeAll = new VoxelShape[64];

        for (int k = 0; k < 64; k++) {
            VoxelShape voxelshape = voxelShapeCore;

            for (int j = 0; j < Direction.values().length; j++) {
                if ((k & 1 << j) != 0) {
                    voxelshape = VoxelShapes.or(voxelshape, voxelShapeSide[j]);
                }
            }

            voxelShapeAll[k] = voxelshape;
        }

        return voxelShapeAll;
    }
    @Override
    public VoxelShape getBlockSupportShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return this.shapes[getShapeIndex(state)];
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes[getShapeIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes[getShapeIndex(state)];
    }

    protected int getShapeIndex(BlockState state) {
        int i = 0;

        for (int j = 0; j < Direction.values().length; j++) {
            Direction d = Direction.values()[j];
            BooleanProperty p = FACING_TO_PROPERTY_MAP.get(d);
            if (state.getValue(p)) {
                i |= 1 << j;
            }
        }

        return i;
    }

    @Override
    public void onPlace(BlockState blockState, World world, BlockPos pos, BlockState oldBlockState, boolean p_220082_5_) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (Direction direction : Direction.values()) {
            blockpos$mutable.setWithOffset(pos, direction);
            BlockState neighborBlockstate = world.getBlockState(blockpos$mutable);

            BlockState newMyBlockState = blockState.setValue(FACING_TO_PROPERTY_MAP.get(direction), false);
            if (neighborBlockstate.is(BlockRegister.COPPER_CABLE)) {
                newMyBlockState = blockState.setValue(FACING_TO_PROPERTY_MAP.get(direction), true);
            }
        }
    }

    protected boolean canConnect(IWorld world, BlockPos pos, Direction direction) {
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = defaultBlockState();
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        for (Direction direction : Direction.values()) {
            boolean valid = canConnect(world, pos.relative(direction), direction);
            state = state.setValue(FACING_TO_PROPERTY_MAP.get(direction), valid);
        }

        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (facing == direction) {
                boolean valid = canConnect(world, pos, facing);
                state = state.setValue(FACING_TO_PROPERTY_MAP.get(direction),valid);
            }
        }
        return state;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }
}
