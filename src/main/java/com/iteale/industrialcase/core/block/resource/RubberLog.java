package com.iteale.industrialcase.core.block.resource;


import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolType;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RubberLog extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<RubberLogState> STATE = EnumProperty.create("state", RubberLogState.class);

    public RubberLog() {
        super(
                BlockBehaviour.Properties.of(Material.WOOD)
                        .strength(1.0F, 2.0F)
                        .sound(SoundType.WOOD)
                        .harvestLevel(1)
                        .harvestTool(ToolType.AXE)
                        .randomTicks()
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
     * @deprecated call via @link BlockState#withRotation(Rotation) whenever possible. Implementing/overriding is
     * fine.
     */
    @Deprecated
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
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (random.nextInt(7) == 0) {
            RubberLogState rwState = state.getValue(STATE);
            if (!rwState.canRegenerate())
                return;

            world.setBlock(pos, state.setValue(STATE, rwState.getWet()), 2);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        RubberLogState logState = state.getValue(RubberLog.STATE);
        if (player.getItemInHand(hand).is(ItemRegistry.TREETAP.get())) {
            if (!logState.isPlain() && logState.wet) {
                Random random = new Random();
                int dropCount = ThreadLocalRandom.current().nextInt(1, 4);

                // change log state
                BlockPos dropPos = pos.relative(hit.getDirection());
                state = state.setValue(RubberLog.STATE, logState.getDry());
                level.setBlock(pos, state, 2);

                // drop resin
                ItemStack dropResin = ItemRegistry.RESIN.get().getDefaultInstance();
                dropResin.setCount(dropCount);
                ItemEntity resin = new ItemEntity(level,
                        dropPos.getX(), dropPos.getY(), dropPos.getZ(),
                        dropResin);

                level.addFreshEntity(resin);

                // set treetap damage
                player.getItemInHand(hand).setDamageValue(1);
            }
        }

        return InteractionResult.SUCCESS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, STATE);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
    }

    public enum RubberLogState implements StringRepresentable {
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
