package com.iteale.industrialcase.core.block;


import com.iteale.industrialcase.api.recipe.IRecipeInput;
import com.iteale.industrialcase.api.recipe.Recipes;
import com.iteale.industrialcase.core.block.state.IIdProvider;
import com.iteale.industrialcase.core.block.type.IBlockSound;
import com.iteale.industrialcase.core.block.type.IExtBlockType;
import com.iteale.industrialcase.core.ref.BlockName;
import com.iteale.industrialcase.core.util.StackUtil;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;

public class BlockScaffold extends BlockBase {
    private BlockScaffold() {
        super(Properties.of(Material.WOOD).randomTicks());
    }


    @OnlyIn(Dist.CLIENT)
    public RenderType getBlockLayer() {
        return RenderType.cutout();
    }

    /*
    public Material getMaterial(BlockState state) {
        ScaffoldType type = getType(state);
        if (type == null) return super.getMaterial(state);

        switch (type) {
            case wood:
            case reinforced_wood:
                return Material.WOOD;

            case iron:
            case reinforced_iron:
                return Material.METAL;
        }
        throw new IllegalStateException("Invalid scaffold type: " + type);
    }
     */

    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    public boolean isNormalCube(BlockState state) {
        return false;
    }

    public boolean isLadder(BlockState state, BlockGetter world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    public void onEntityCollidedWithBlock(Level world, BlockPos pos, BlockState state, Entity rawEntity) {
        if (rawEntity instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) rawEntity;


            entity.fallDistance = 0.0F;

            double limit = 0.15D;

            entity.setDeltaMovement(
                    Util.limit(entity.getDeltaMovement().x, -limit, limit),
                    entity.getDeltaMovement().y,
                    Util.limit(entity.getDeltaMovement().z, -limit, limit));

            double motionY = 0.0D;
            if (entity.isShiftKeyDown() && entity instanceof Player) {
                if (entity.isInWater()) {
                    motionY = 0.02D;
                } else {
                    motionY = 0.08D;
                }
            } else if (entity.horizontalCollision) {
                motionY = 0.2D;
            } else {
                motionY = Math.max(entity.getDeltaMovement().y, -0.07D);
            }

            entity.setDeltaMovement(
                    entity.getDeltaMovement().x,
                    motionY,
                    entity.getDeltaMovement().z);
        }
    }

    public AABB getBoundingBox(BlockState state, BlockGetter world, BlockPos pos) {
        return aabb;
    }

    /*
    public AABB getSelectedBoundingBox(BlockState state, Level worldIn, BlockPos pos) {
        return FULL_BLOCK_AABB.offset(pos);
    }
     */

    public boolean isSideSolid(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return (side.getAxis() == Direction.Axis.Y);
    }

    /*
    public List<ItemStack> getDrops(BlockGetter world, BlockPos pos, BlockState state, int fortune) {
        if (state.getBlock() != this) return Collections.emptyList();

        List<ItemStack> ret = new ArrayList<>();
        ScaffoldType type = (ScaffoldType) state.getValue(this.typeProperty);

        switch (type) {
            case wood:
            case iron:
                ret.add(getItemStack(type));
                return ret;
            case reinforced_wood:
                ret.add(getItemStack(ScaffoldType.wood));
                ret.add(new ItemStack(Items.STICK, 2));
                return ret;
            case reinforced_iron:
                ret.add(getItemStack(ScaffoldType.iron));
                ret.add(BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron));
                return ret;
        }
        throw new IllegalStateException();
    }
     */

    private static final IRecipeInput stickInput = Recipes.inputFactory.forOreDict("stickWood");

    /*
    public boolean onBlockActivated(Level world, BlockPos pos, BlockState state, Player player, InteractionHand hand, Direction side, float hitX, float hitY, float hitZ) {
        if (player.isShiftKeyDown()) return false;

        ItemStack stack = player.getItemInHand(hand);
        if (StackUtil.isEmpty(stack)) return false;

        ScaffoldType type = getType(state);
        if (type == null) return false;

        int stickCount = 2;
        int fenceCount = 1;

        switch (type) {
            case wood:
                if (!stickInput.matches(stack) || StackUtil.getSize(stack) < 2) return false;
                break;
            case iron:
                if (!StackUtil.checkItemEquality(stack, BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron)) || StackUtil.getSize(stack) < 1)
                    return false;
                break;
            case reinforced_wood:
            case reinforced_iron:
                return false;
            default:
                throw new IllegalStateException();
        }

        if (!isPillar(world, pos)) {
            return false;
        }

        switch (type) {
            case wood:
                StackUtil.consumeOrError(player, hand, StackUtil.recipeInput(stickInput), 2);
                type = ScaffoldType.reinforced_wood;

                world.setBlockState(pos, state.withProperty((IProperty) this.typeProperty, type));

                return true;
            case iron:
                StackUtil.consumeOrError(player, hand, StackUtil.sameStack(BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron)), 1);
                type = ScaffoldType.reinforced_iron;
                world.setBlockState(pos, state.withProperty((IProperty) this.typeProperty, type));
                return true;
        }
        throw new IllegalStateException();
    }

    public void onBlockClicked(Level world, BlockPos pos, Player player) {
        InteractionHand hand = InteractionHand.MAIN_HAND;
        ItemStack stack = player.getItemInHand(hand);
        if (StackUtil.isEmpty(stack))
            return;
        if (StackUtil.checkItemEquality(stack, Item.getItemFromBlock(this))) {
            while (world.getBlockState(pos).getBlock() == this) {
                pos = pos.above();
            }

            if (canPlaceBlockAt(world, pos) && pos.getY() < IC2.getWorldHeight(world)) {
                boolean isCreative = player.capabilities.isCreativeMode;
                ItemStack prev = isCreative ? StackUtil.copy(stack) : null;
                stack.onItemUse(player, world, pos.down(), hand, Direction.UP, 0.5F, 1.0F, 0.5F);

                if (!isCreative) {
                    StackUtil.clearEmpty(player, hand);
                } else {
                    StackUtil.set(player, hand, prev);
                }
            }
        }
    }

    public boolean canPlaceBlockAt(Level world, BlockPos pos) {
        return (super.canPlaceBlockAt(world, pos) && hasSupport((BlockGetter) world, pos, ScaffoldType.wood));
    }

    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        checkSupport(world, pos);
    }


    public void randomTick(Level world, BlockPos pos, BlockState state, Random random) {
        if (random.nextInt(8) == 0) {
            checkSupport(world, pos);
        }
    }


    private boolean isPillar(Level world, BlockPos pos) {
        for (; world.getBlockState(pos).getBlock() == this; pos = pos.below()) ;

        return world.isBlockNormalCube(pos, false);
    }

    public int getFireSpreadSpeed(BlockGetter world, BlockPos pos, Direction face) {
        ScaffoldType type = getType(world, pos);
        if (type == null) return 0;

        switch (type) {
            case wood:
            case reinforced_wood:
                return 8;
            case iron:
            case reinforced_iron:
                return 0;
        }
        throw new IllegalStateException();
    }


    public int getFlammability(BlockGetter world, BlockPos pos, Direction face) {
        ScaffoldType type = getType(world, pos);
        if (type == null) return 0;

        switch (type) {
            case wood:
            case reinforced_wood:
                return 20;
            case iron:
            case reinforced_iron:
                return 0;
        }
        throw new IllegalStateException();
    }

    private boolean hasSupport(BlockGetter world, BlockPos start, ScaffoldType type) {
        return (((Support) calculateSupport(world, start, type).get(start)).strength >= 0);
    }

    private void checkSupport(Level world, BlockPos start) {
        BlockState state = world.getBlockState(start);
        if (state.getBlock() != this)
            return;
        Map<BlockPos, Support> results = calculateSupport((BlockGetter) world, start, (ScaffoldType) state.getValue((IProperty) this.typeProperty));
        boolean droppedAny = false;

        for (Support support : results.values()) {
            if (support.strength >= 0) {
                continue;
            }
            world.setBlockState(support.pos, Blocks.AIR.defaultBlockState(), 2);
            dropBlockAsItem(world, support.pos, defaultBlockState().setValue(this.typeProperty, support.type), 0);
            droppedAny = true;
        }


        if (droppedAny) {
            for (Support support : results.values()) {
                if (support.strength < 0) world.notifyNeighborsRespectDebug(support.pos, this, true);

            }
        }
    }

    private Map<BlockPos, Support> calculateSupport(BlockGetter world, BlockPos start, ScaffoldType type) {
        Map<BlockPos, Support> results = new HashMap<>();
        Queue<Support> queue = new ArrayDeque<>();
        Set<BlockPos> groundSupports = new HashSet<>();

        Support support = new Support(start, type, -1);
        results.put(start, support);
        queue.add(support);

        while ((support = queue.poll()) != null) {
            for (Direction dir : Direction.values()) {
                BlockPos pos = support.pos.offset(dir);
                if (!results.containsKey(pos)) {

                    BlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();

                    if (block == this) {
                        type = (ScaffoldType) state.getValue((IProperty) this.typeProperty);
                        Support cSupport = new Support(pos, type, -1);
                        results.put(pos, cSupport);
                        queue.add(cSupport);
                    } else if (block.isNormalCube(state, world, pos)) {
                        groundSupports.add(pos);
                    }
                }
            }
        }
        label63:
        for (BlockPos groundPos : groundSupports) {
            BlockPos pos = groundPos.above();
            int propagatedStrength = 0;
            while (true) {
                int strength;
                support = results.get(pos);
                if (support == null) {
                    continue label63;
                }

                if (support.type.strength >= propagatedStrength) {
                    strength = support.type.strength;
                    propagatedStrength = strength - 1;
                } else {
                    strength = propagatedStrength;
                    propagatedStrength--;
                }

                if (support.strength < strength) {
                    support.strength = strength;

                    for (Direction dir : Direction.Plane.HORIZONTAL) {
                        BlockPos nPos = pos.offset(dir);
                        Support nSupport = results.get(nPos);
                        if (nSupport != null && nSupport.strength < strength) {

                            nSupport.strength = strength - 1;
                            queue.add(nSupport);
                        }
                    }
                }
                pos = pos.above();
            }
        }

        while ((support = queue.poll()) != null) {
            for (Direction dir : supportedFacings) {
                BlockPos pos = support.pos.offset(dir);
                Support nSupport = results.get(pos);
                if (nSupport != null && nSupport.strength < support.strength) {

                    support.strength--;

                    if (nSupport.strength > 0) queue.add(nSupport);
                }
            }
        }
        return results;
    }
     */

    private static final Direction[] supportedFacings = new Direction[]{Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    private static final double border = 0.03125D;

    private static class Support {
        final BlockPos pos;
        final BlockScaffold.ScaffoldType type;
        int strength;

        Support(BlockPos pos, BlockScaffold.ScaffoldType type, int strength) {
            this.pos = pos;
            this.type = type;
            this.strength = strength;
        }
    }

    public enum ScaffoldType implements IIdProvider, IExtBlockType, IBlockSound {
        wood(2, 0.5F, 0.12F, SoundType.WOOD),
        reinforced_wood(5, 0.6F, 0.24F, SoundType.WOOD),
        iron(5, 0.8F, 6.0F, SoundType.METAL),
        reinforced_iron(12, 1.0F, 8.0F, SoundType.METAL);
        public final int strength;
        private final float hardness;
        private final float explosionResistance;
        private final SoundType sound;

        ScaffoldType(int strength, float hardness, float explosionResistance, SoundType sound) {
            if (strength < 1) throw new IllegalArgumentException();

            this.strength = strength;
            this.hardness = hardness;
            this.explosionResistance = explosionResistance;
            this.sound = sound;
        }

        public String getName() {
            return name();
        }

        public int getId() {
            return ordinal();
        }

        public float getHardness() {
            return this.hardness;
        }

        public float getExplosionResistance() {
            return this.explosionResistance;
        }

        public SoundType getSound() {
            return this.sound;
        }
    }

    private static final AABB aabb = new AABB(0.03125D, 0.0D, 0.03125D, 0.96875D, 1.0D, 0.96875D);
}