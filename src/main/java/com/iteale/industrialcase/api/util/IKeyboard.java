package com.iteale.industrialcase.api.util;

import net.minecraft.world.entity.player.Player;

public interface IKeyboard {
	boolean isAltKeyDown(Player player);
	boolean isBoostKeyDown(Player player);
	boolean isForwardKeyDown(Player player);
	boolean isJumpKeyDown(Player player);
	boolean isModeSwitchKeyDown(Player player);
	boolean isSideinventoryKeyDown(Player player);
	boolean isHudModeKeyDown(Player player);
	boolean isSneakKeyDown(Player player);
}
