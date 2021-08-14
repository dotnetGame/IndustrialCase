package com.iteale.industrialcase.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Cancelable;


@Cancelable
public class RetextureEvent extends WorldEvent {
	public RetextureEvent(Level world, BlockPos pos, BlockBehaviour.BlockStateBase state, Direction side, Player player,
						  BlockBehaviour.BlockStateBase refState, String refVariant, Direction refSide, int[] refColorMultipliers) {
		super(world);

		if (world == null) throw new NullPointerException("null world");
		if (world.isClientSide) throw new IllegalStateException("remote world");
		if (pos == null) throw new NullPointerException("null pos");
		if (state == null) throw new NullPointerException("null state");
		if (side == null) throw new NullPointerException("null side");
		if (refState == null) throw new NullPointerException("null refState");
		if (refVariant == null) throw new NullPointerException("null refVariant");
		if (refSide == null) throw new NullPointerException("null refSide");
		if (refColorMultipliers == null) throw new NullPointerException("null refColorMultipliers");

		this.pos = pos;
		this.state = state;
		this.side = side;
		this.player = player;
		this.refState = refState;
		this.refVariant = refVariant;
		this.refSide = refSide;
		this.refColorMultipliers = refColorMultipliers;
	}

	// target block
	public final BlockPos pos;
	public final BlockBehaviour.BlockStateBase state;
	public final Direction side;

	// player causing the action, may be null
	public final Player player;

	// referenced block (to grab the texture from)
	public final BlockBehaviour.BlockStateBase refState;
	public final String refVariant;
	public final Direction refSide;
	public final int[] refColorMultipliers;

	// set to true to confirm the operation
	public boolean applied = false;
}
