package com.iteale.industrialcase.core.block;


import com.iteale.industrialcase.core.block.state.IIdProvider;
import com.iteale.industrialcase.core.item.block.ItemBlockEntity;
import com.iteale.industrialcase.core.ref.TeBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;
import java.util.Set;

@MethodsReturnNonnullByDefault
public interface ITeBlock extends IIdProvider
{
    ResourceLocation getIdentifier();

    boolean hasItem();

    @Nullable
    Class<? extends TileEntityBlock> getTeClass();

    boolean hasActive();

    Set<Direction> getSupportedFacings();

    float getHardness();

    float getExplosionResistance();

    TeBlock.HarvestTool getHarvestTool();

    TeBlock.DefaultDrop getDefaultDrop();

    Rarity getRarity();

    boolean allowWrenchRotating();

    default Material getMaterial() {
        // return TeBlockRegistry.getInfo(getIdentifier()).getDefaultMaterial();
        return null;
    }

    default boolean isTransparent() {
        return false;
    }

    default void setPlaceHandler(TeBlock.ITePlaceHandler handler) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    default TeBlock.ITePlaceHandler getPlaceHandler() {
        return null;
    }

    @Nullable
    @Deprecated
    TileEntityBlock getDummyTe();

    public static interface ITeBlockCreativeRegisterer {
        void addSubBlocks(NonNullList<ItemStack> param1NonNullList, BlockTileEntity param1BlockTileEntity, ItemBlockEntity param1ItemBlockTileEntity, CreativeModeTab param1CreativeTabs);
    }
}