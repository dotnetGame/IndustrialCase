package com.iteale.industrialcase.api.transport;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IPipe {
    BlockEntity getTile();

    /**
     * Check if the pipe is connected in the provided facing
     * @param facing the provided facing
     * @return true if there is a connection or false otherwise
     */
    boolean isConnected(Direction facing);

    /**
     * Flip the connection status in the provided facing
     * @param facing the provided facing
     */
    void flipConnection(Direction facing);
}
