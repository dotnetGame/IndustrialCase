package com.iteale.industrialcase.core.block.wiring;

import com.iteale.industrialcase.core.block.BlockTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class CableTileEntity extends BlockTileEntity {
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
    public CableBase(Properties properties, float thickness) {
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
            voxelShapeSide[i] = Shapes.box(
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
                    voxelshape = Shapes.or(voxelshape, voxelShapeSide[j]);
                }
            }

            voxelShapeAll[k] = voxelshape;
        }

        return voxelShapeAll;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(world, pos);
        return this.shapes[getShapeIndex(state)].move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
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

    protected boolean canConnect(LevelAccessor world, BlockPos pos, Direction direction) {
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        for (Direction direction : Direction.values()) {
            boolean valid = canConnect(world, pos.relative(direction), direction);
            state = state.setValue(FACING_TO_PROPERTY_MAP.get(direction), valid);
        }

        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (facing == direction) {
                boolean valid = canConnect(world, pos, facing);
                state = state.setValue(FACING_TO_PROPERTY_MAP.get(direction),valid);
            }
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }
}
