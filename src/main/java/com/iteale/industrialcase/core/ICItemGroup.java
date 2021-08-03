package com.iteale.industrialcase.core;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ICItemGroup extends ItemGroup {
    public ICItemGroup() {
        super("IC");
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack makeIcon() {
        return new ItemStack(Items.IRON_BLOCK);
    }
}
