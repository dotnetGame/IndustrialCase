package com.iteale.industrialcase.core.item.block;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.BlockTileEntity;
import com.iteale.industrialcase.core.block.TileEntityBlock;
import com.iteale.industrialcase.core.block.ITeBlock;
import com.iteale.industrialcase.core.network.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemBlockEntity extends ItemBlockIC2 {

    public final ResourceLocation identifier;

    public ItemBlockEntity(Block block, Properties properties, ResourceLocation identifier) {
        super(block, properties);
        // setHasSubtypes(true);
        this.identifier = identifier;
    }

    public String getUnlocalizedName(ItemStack stack) {
        ITeBlock teBlock = getTeBlock(stack);
        String name = (teBlock == null) ? "invalid" : teBlock.getName();

        return getRegistryName().toString() + "." + name;
    }

    public void getSubItems(CreativeModeTab tab, NonNullList<ItemStack> items) {
        this.getBlock().fillItemCategory(tab, items);
    }

    public boolean placeBlockAt(ItemStack stack, Player player, Level world, BlockPos pos, Direction side, float hitX, float hitY, float hitZ, BlockState newState) {
        assert newState.getBlock() == this.getBlock();

        if (!((BlockTileEntity)this.getBlock()).canReplace(world, pos, side, stack)) return false;

        ITeBlock teBlock = getTeBlock(stack);
        if (teBlock == null) return false;

        Class<? extends TileEntityBlock> teClass = teBlock.getTeClass();
        if (teClass == null) return false;

        TileEntityBlock te = TileEntityBlock.instantiate(teClass);
        if (!placeTeBlock(stack, player, world, pos, side, te)) return false;

        return true;
    }

    public static boolean placeTeBlock(ItemStack stack, LivingEntity placer, Level world, BlockPos pos, Direction side, TileEntityBlock te) {
        BlockState oldState = world.getBlockState(pos);
        BlockState newState = te.getBlockState();
        if (!world.setBlock(pos, newState, 0)) return false;

        world.setBlockEntity(te);
        te.onPlaced(stack, placer, side);

        world.markAndNotifyBlock(pos, world.getChunkAt(pos), oldState, newState, 3, 0);

        if (!world.isClientSide)
            IndustrialCase.network.sendInitialData(te);

        return true;
    }


    public Rarity getRarity(ItemStack stack) {
        ITeBlock teblock = getTeBlock(stack);

        return (teblock != null) ? teblock.getRarity() : Rarity.COMMON;
    }

    private ITeBlock getTeBlock(ItemStack stack) {
        if (stack == null) return null;

        return TeBlockRegistry.get(this.identifier, stack.getDamageValue());
    }
}
