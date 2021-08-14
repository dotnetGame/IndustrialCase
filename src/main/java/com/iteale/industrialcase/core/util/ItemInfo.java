package com.iteale.industrialcase.core.util;


import com.iteale.industrialcase.api.info.IInfoProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class ItemInfo implements IInfoProvider
{
    @Override
    public double getEnergyValue(ItemStack stack) {
        return 0;
    }

    @Override
    public int getFuelValue(ItemStack stack, boolean allowLava) {
        return 0;
    }
    /*
    public double getEnergyValue(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) return 0.0D;

        if (StackUtil.checkItemEquality(stack, ItemName.single_use_battery.getItemStack()))
            return 1200.0D;
        if (StackUtil.checkItemEquality(stack, Items.REDSTONE))
            return 800.0D;
        if (StackUtil.checkItemEquality(stack, ItemName.dust.getItemStack((Enum)DustResourceType.energium))) {
            return 16000.0D;
        }
        return 0.0D;
    }



    public int getFuelValue(ItemStack stack, boolean allowLava) {
        if (StackUtil.isEmpty(stack)) return 0;


        if ((StackUtil.checkItemEquality(stack, ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap)) ||
                StackUtil.checkItemEquality(stack, ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap_box))) &&
                !ConfigUtil.getBool(MainConfig.get(), "misc/allowBurningScrap")) {
            return 0;
        }


        FluidStack liquid = FluidUtil.getFluidContained(stack);


        boolean isLava = (liquid != null && liquid.amount > 0 && liquid.getFluid() == FluidRegistry.LAVA);
        if (isLava && !allowLava) return 0;

        int ret = TileEntityFurnace.getItemBurnTime(stack);

        return isLava ? (ret / 10) : ret;
    }
    */
}
