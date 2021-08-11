package com.iteale.industrialcase.api.network;


import net.minecraft.world.entity.player.Player;

/**
 * Allows a tile entity to receive network events received from clients.
 */
public interface INetworkClientBlockEntityEventListener {
	/**
	 * Called when a network event is received.
	 * 
	 * @param player client which sent the event
	 * @param event event ID
	 */
	void onNetworkEvent(Player player, int event);
}

