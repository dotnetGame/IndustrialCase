package com.iteale.industrialcase.core.block.machine.blockentity;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityIronFurnace extends AbstractFurnaceBlockEntity {
    protected BlockEntityIronFurnace(BlockEntityType<?> t, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> p_154994_) {
        super(t, pos, state, p_154994_);
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return null;
    }
}
