package com.iteale.industrialcase.api.network;

/**
 * Allows a tile entity to receive network events received from the server.
 */
public interface INetworkBlockEntityEventListener {
	/**
	 * Called when a network event is received.
	 * 
	 * @param event Event ID
	 */
	void onNetworkEvent(int event);
}

