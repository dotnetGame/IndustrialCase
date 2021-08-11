package com.iteale.industrialcase.api.network;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * This is just here for the API functions.
 * Do not implement by yourself.
 *
 * @see NetworkHelper
 *
 * @author Aroma1997
 *
 */
public interface INetworkManager {

	void updateTileEntityField(BlockEntity te, String field);

	void initiateTileEntityEvent(BlockEntity te, int event, boolean limitRange);

	void initiateItemEvent(Player player, ItemStack stack, int event, boolean limitRange);

	void initiateClientTileEntityEvent(BlockEntity te, int event);

	void initiateClientItemEvent(ItemStack stack, int event);

	void sendInitialData(BlockEntity te);

}
