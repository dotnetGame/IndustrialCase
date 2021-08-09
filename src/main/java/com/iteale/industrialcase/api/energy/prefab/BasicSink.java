package com.iteale.industrialcase.api.energy.prefab;

import com.iteale.industrialcase.api.energy.tile.IEnergyEmitter;
import com.iteale.industrialcase.api.energy.tile.IEnergySink;
import com.iteale.industrialcase.api.info.ILocatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * BasicSink is a simple delegate to provide an ic2 energy sink.
 *
 * <p>It's designed to be attached to a tile entity as a delegate. Functionally BasicSink acts as a
 * one-time configurable input energy buffer, thus providing a common use case for machines.
 *
 * <p>A simpler alternative is to use link BasicSinkTe.Sink, which requires injecting into the
 * type hierarchy (using it as a super class). BasicSink however can be added to any class without
 * inheritance, but needs some forwards for keeping its state and registering with the energy net.
 *
 * <p>The constraints set by BasicSink like the strict tank-like energy buffering should provide a
 * more easy to use and stable interface than using IEnergySink directly while aiming for
 * optimal performance.
 *
 * <p>Using BasicSink involves the following steps:<ul>
 * <li>create a BasicSink instance in your TileEntity, typically in a field
 * <li>forward onLoad (or update), invalidate, onChunkUnload, readFromNBT and writeToNBT to the BasicSink
 *   instance.
 * <li>call useEnergy whenever appropriate. canUseEnergy determines if enough energy is available
 *   without consuming the energy.
 * <li>optionally use getEnergyStored to display the output buffer charge level
 * <li>optionally use setEnergyStored to sync the stored energy to the client (e.g. in the Container)</ul>
 *
 * <p>Example implementation code:
 * <pre><code>
 * public class SomeTileEntity extends TileEntity {
 *     // new basic energy sink, 1000 EU buffer, tier 1 (32 EU/t, LV)
 *     private BasicSink ic2EnergySink = new BasicSink(this, 1000, 1);
 *
 *     {@literal @}Override
 *     public void onLoad() {
 *         ic2EnergySink.onLoad(); // notify the energy sink
 *         ...
 *     }
 *
 *     {@literal @}Override
 *     public void invalidate() {
 *         ic2EnergySink.invalidate(); // notify the energy sink
 *         ...
 *         super.invalidate(); // this is important for mc!
 *     }
 *
 *     {@literal @}Override
 *     public void onChunkUnload() {
 *         ic2EnergySink.onChunkUnload(); // notify the energy sink
 *         ...
 *     }
 *
 *     {@literal @}Override
 *     public void readFromNBT(NBTTagCompound tag) {
 *         super.readFromNBT(tag);
 *
 *         ic2EnergySink.readFromNBT(tag);
 *         ...
 *     }
 *
 *     {@literal @}Override
 *     public void writeToNBT(NBTTagCompound tag) {
 *         super.writeToNBT(tag);
 *
 *         ic2EnergySink.writeToNBT(tag);
 *         ...
 *     }
 *
 *     {@literal @}Override
 *     public void update() {
 *         if (ic2EnergySink.useEnergy(5)) { // use 5 eu from the sink's buffer this tick
 *             ... // do something with the energy
 *         }
 *         ...
 *     }
 *
 *     ...
 * }
 * </code></pre>
 */
public class BasicSink extends BasicEnergyTile implements IEnergySink {
	/**
	 * Constructor for a new BasicSink delegate.
	 *
	 * @param parent TileEntity represented by this energy sink.
	 * @param capacity Maximum amount of eu to store.
	 * @param tier IC2 tier, 1 = LV, 2 = MV, ...
	 */
	public BasicSink(BlockEntity parent, double capacity, int tier) {
		super(parent, capacity);

		if (tier < 0) throw new IllegalArgumentException("invalid tier: "+tier);

		this.tier = tier;
	}

	public BasicSink(ILocatable parent, double capacity, int tier) {
		super(parent, capacity);

		if (tier < 0) throw new IllegalArgumentException("invalid tier: "+tier);

		this.tier = tier;
	}

	public BasicSink(Level world, BlockPos pos, double capacity, int tier) {
		super(world, pos, capacity);

		if (tier < 0) throw new IllegalArgumentException("invalid tier: "+tier);

		this.tier = tier;
	}

	/**
	 * Set the IC2 energy tier for this sink.
	 *
	 * @param tier IC2 Tier.
	 */
	public void setSinkTier(int tier) {
		if (tier < 0) throw new IllegalArgumentException("invalid tier: "+tier);

		this.tier = tier;
	}

	// energy net interface >>

	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, Direction direction) {
		return true;
	}

	@Override
	public double getDemandedEnergy() {
		return Math.max(0, getCapacity() - getEnergyStored());
	}

	@Override
	public double injectEnergy(Direction directionFrom, double amount, double voltage) {
		setEnergyStored(getEnergyStored() + amount);

		return 0;
	}

	@Override
	public int getSinkTier() {
		return tier;
	}

	// << energy net interface

	@Override
	protected String getNbtTagName() {
		return "IC2BasicSink";
	}

	protected int tier;
}
