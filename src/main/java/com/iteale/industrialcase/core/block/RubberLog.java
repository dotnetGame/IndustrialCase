package com.iteale.industrialcase.core.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class RubberLog extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<RubberLogState> STATE = EnumProperty.create("state", RubberLogState.class);

    public RubberLog() {
        super(
                AbstractBlock.Properties.of(Material.WOOD)
                        .strength(1.0F, 2.0F)
                        .sound(SoundType.WOOD)
                        .harvestLevel(1)
                        .harvestTool(ToolType.AXE)
        );
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(AXIS, Direction.Axis.Y)
                        .setValue(STATE, RubberLogState.plain)
        );
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via @link IBlockState#withRotation(Rotation) whenever possible. Implementing/overriding is
     * fine.
     */
    public BlockState rotate(BlockState state, Rotation rot) {
        switch(rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch((Direction.Axis)state.getValue(AXIS)) {
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(7) == 0) {
            RubberLogState rwState = state.getValue(STATE);
            if (!rwState.canRegenerate())
                return;
            state.setValue(STATE, rwState.getWet());
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS, STATE);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
    }

    public enum RubberLogState implements IStringSerializable {
        plain(null, false),
        dry_north(Direction.NORTH, false),
        dry_south(Direction.SOUTH, false),
        dry_west(Direction.WEST, false),
        dry_east(Direction.EAST, false),
        wet_north(Direction.NORTH, true),
        wet_south(Direction.SOUTH, true),
        wet_west(Direction.WEST, true),
        wet_east(Direction.EAST, true);

        public final Direction facing;
        public final boolean wet;
        RubberLogState(Direction facing, boolean wet) {
            this.facing = facing;
            this.wet = wet;
        }

        @Override
        public String getSerializedName() {
            return name();
        }

        public boolean isPlain() {
            return (this.facing == null);
        }

        public boolean canRegenerate() {
            return (!isPlain() && !this.wet);
        }

        public RubberLogState getWet() {
            if (isPlain())
                return null;
            if (this.wet)
                return this;
            return values()[ordinal() + 4];
        }

        public RubberLogState getDry() {
            if (isPlain() || !this.wet)
                return this;
            return values()[ordinal() - 4];
        }
    }
}
