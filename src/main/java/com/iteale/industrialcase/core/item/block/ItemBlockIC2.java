package com.iteale.industrialcase.core.item.block;


import com.iteale.industrialcase.core.block.BlockBase;
import com.iteale.industrialcase.core.init.Localization;
import com.iteale.industrialcase.core.ref.BlockName;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;

public class ItemBlockIC2 extends BlockItem
{
    public ItemBlockIC2(Block block, Properties properties) {
        super(block, properties);
    }

    public String getUnlocalizedName(ItemStack stack) {
        return super.getRegistryName().toString();
    }

    public String getItemStackDisplayName(ItemStack stack) {
        return Localization.translate(getUnlocalizedName(stack));
    }

    public boolean canHarvestBlock(BlockBehaviour.BlockStateBase block, ItemStack stack) {
        return (block.getBlock() == BlockName.scaffold.getInstance());
    }

    public int getItemBurnTime(ItemStack stack) {
        if (this.block == BlockName.scaffold.getInstance()) {
            BlockScaffold scaffold = (BlockScaffold)this.block;

            BlockState state = scaffold.getState(scaffold.getVariant(stack));
            return (state.getMaterial() == Material.WOOD) ? 300 : 0;
        }

        return -1;
    }

    public Rarity getRarity(ItemStack stack) {
        if (this.block instanceof BlockBase) {
            return ((BlockBase)this.block).getRarity(stack);
        }

        return super.getRarity(stack);
    }

    public static Function<Block, Item> supplier = ItemBlockIC2::new;
}

