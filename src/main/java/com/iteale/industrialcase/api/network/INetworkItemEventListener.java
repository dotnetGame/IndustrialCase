package com.iteale.industrialcase.api.network;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Allows an item to receive network events received from the server.
 */
public interface INetworkItemEventListener {
	/**
	 * Called when a network event is received.
	 * 
	 * @param stack item stack
	 * @param player player containing the item
	 * @param event event ID
	 */
	void onNetworkEvent(ItemStack stack, Player player, int event);
}

