package com.iteale.industrialcase.core.block.machine;


import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class IronFurnace extends Block {
    public IronFurnace() {
        super(Properties.of(Material.STONE));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            TileEntityIronFurnace tileEntity = (TileEntityIronFurnace) level.getBlockEntity(pos);
            // int counter = tileEntity.increase();
            // TranslationTextComponent translationTextComponent = new TranslationTextComponent("message.neutrino.counter", counter);
            // player.sendStatusMessage(translationTextComponent, false);
        }
        return InteractionResult.SUCCESS;
    }
}
