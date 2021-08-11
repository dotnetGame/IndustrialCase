package com.iteale.industrialcase.core.util;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.*;

public class LiquidUtil
{
    public static List<Fluid> getAllFluids() {
        Set<Fluid> fluids = new HashSet(Registry.FLUID.entrySet());
        fluids.remove(null);
        List<Fluid> ret = new ArrayList<>(fluids);

        Collections.sort(ret, new Comparator<Fluid>()
        {
            public int compare(Fluid a, Fluid b) {
                ResourceLocation nameA = a.getRegistryName();
                ResourceLocation nameB = b.getRegistryName();

                if (nameA == null) {
                    if (nameB == null) {
                        return 0;
                    }
                    return 1;
                }
                if (nameB == null) {
                    return -1;
                }
                return nameA.compareTo(nameB);
            }
        });

        return ret;
    }

    public static LiquidData getLiquid(Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        Fluid liquid = null;
        boolean isSource = false;

        if (block instanceof IFluidBlock) {
            IFluidBlock fblock = (IFluidBlock)block;

            liquid = fblock.getFluid();
            isSource = fblock.canDrain(world, pos);
        } else if (block == Blocks.WATER) {
            liquid = Fluids.WATER;
            isSource = (state.getValue(LiquidBlock.LEVEL) == 0);
        } else if (block == Blocks.LAVA) {
            liquid = Fluids.LAVA;
            isSource = (state.getValue(LiquidBlock.LEVEL) == 0);
        }

        if (liquid != null) {
            return new LiquidData(liquid, isSource);
        }
        return null;
    }
    public static class LiquidData { public final Fluid liquid;

        LiquidData(Fluid liquid1, boolean isSource1) {
            this.liquid = liquid1;
            this.isSource = isSource1;
        }

        public final boolean isSource;
    }

    public static String toStringSafe(FluidStack fluidStack) {
        if (fluidStack.getFluid() == null) {
            return fluidStack.getAmount() + "(mb)x(null)@(unknown)";
        }
        return fluidStack.toString();
    }
}
