package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.init.BlocksItems;
import com.iteale.industrialcase.core.init.Localization;
import com.iteale.industrialcase.core.item.block.ItemBlockIC2;
import com.iteale.industrialcase.core.ref.BlockName;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;


public abstract class BlockBase extends Block
{
    protected BlockBase(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected String getUnlocalizedName() {
        return super.getRegistryName().toString();
    }

    public String getLocalizedName() {
        return Localization.translate(getUnlocalizedName());
    }

    public boolean canBeReplacedByLeaves(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    public Rarity getRarity(ItemStack stack) {
        return Rarity.COMMON;
    }
}

