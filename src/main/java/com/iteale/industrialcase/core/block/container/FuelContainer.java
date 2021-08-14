package com.iteale.industrialcase.core.block.container;

import com.iteale.industrialcase.api.info.Info;
import com.iteale.industrialcase.core.block.IContainerHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import java.util.Optional;

public class FuelContainer extends ConsumableContainer {
    public final boolean allowLava;
    public FuelContainer(IContainerHolder<?> base, String name, int count, boolean allowLava1) {
        super(base, name, ICContainer.Access.I, count, ICContainer.InvSide.SIDE);

        this.allowLava = allowLava1;
    }

    public boolean accepts(ItemStack stack) {
        return getFuelValue(stack, this.allowLava) > 0;
    }

    public int consumeFuel() {
        ItemStack fuel = consume(1);
        if (fuel == null) return 0;

        return getFuelValue(fuel, this.allowLava);
    }

    public int getFuelValue(ItemStack fuel, boolean allowLava) {
        if (StackUtil.isEmpty(fuel))
            return 0;

        Optional<FluidStack> liquid = FluidUtil.getFluidContained(fuel);

        boolean isLava = liquid.isPresent() && liquid.get().getAmount() > 0
                        && liquid.get().getFluid() == Fluids.LAVA;

        if (isLava && !allowLava)
            return 0;

        int ret = ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);

        return isLava ? (ret / 10) : ret;
    }
}
