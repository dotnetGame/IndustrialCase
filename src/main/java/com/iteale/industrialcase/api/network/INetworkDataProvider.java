package com.iteale.industrialcase.api.network;

import java.util.List;

/**
 * Tile entities which want to synchronized specific fields between client and server have to implement this.
 *
 * The fields don't update themselves, a field update must be sent every time a synchronized field changes.
 */
public interface INetworkDataProvider {
	/**
	 * Get the list of synchronized fields.
	 *
	 * If a name contains =, the name will be split into the actual name and the value, avoiding a
	 * field read.
	 *
	 * @return Names of the synchronized fields
	 */
	List<String> getNetworkedFields();
}

