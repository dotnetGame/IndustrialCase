package com.iteale.industrialcase.core;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ICTab extends CreativeModeTab {
    public ICTab() {
        super("IC");
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack makeIcon() {
        return new ItemStack(Items.IRON_BLOCK);
    }
}
