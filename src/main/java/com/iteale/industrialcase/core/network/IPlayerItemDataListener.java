package com.iteale.industrialcase.core.network;

import net.minecraft.world.entity.player.Player;

public interface IPlayerItemDataListener {
    void onPlayerItemNetworkData(Player paramEntityPlayer, int paramInt, Object... paramVarArgs);
}