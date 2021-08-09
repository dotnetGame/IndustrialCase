package com.iteale.industrialcase.api.energy.prefab;


import com.iteale.industrialcase.api.energy.EnergyNet;
import com.iteale.industrialcase.api.energy.tile.IEnergySink;
import com.iteale.industrialcase.api.energy.tile.IEnergySource;
import com.iteale.industrialcase.api.info.ILocatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Combination of BasicSink and BasicSource, see their respective documentation for details.
 *
 * <p>A subclass still has to implement acceptsEnergyFrom and emitsEnergyTo.
 */
public abstract class BasicSinkSource extends BasicEnergyTile implements IEnergySink, IEnergySource {
	public BasicSinkSource(BlockEntity parent, double capacity, int sinkTier, int sourceTier) {
		super(parent, capacity);

		if (sinkTier < 0) throw new IllegalArgumentException("invalid sink tier: "+sinkTier);
		if (sourceTier < 0) throw new IllegalArgumentException("invalid source tier: "+sourceTier);

		this.sinkTier = sinkTier;
		this.sourceTier = sourceTier;
		double power = EnergyNet.instance.getPowerFromTier(sourceTier);

		if (getCapacity() < power) setCapacity(power);
	}

	public BasicSinkSource(ILocatable parent, double capacity, int sinkTier, int sourceTier) {
		super(parent, capacity);

		if (sinkTier < 0) throw new IllegalArgumentException("invalid sink tier: "+sinkTier);
		if (sourceTier < 0) throw new IllegalArgumentException("invalid source tier: "+sourceTier);

		this.sinkTier = sinkTier;
		this.sourceTier = sourceTier;
		double power = EnergyNet.instance.getPowerFromTier(sourceTier);

		if (getCapacity() < power) setCapacity(power);
	}

	public BasicSinkSource(Level world, BlockPos pos, double capacity, int sinkTier, int sourceTier) {
		super(world, pos, capacity);

		if (sinkTier < 0) throw new IllegalArgumentException("invalid sink tier: "+sinkTier);
		if (sourceTier < 0) throw new IllegalArgumentException("invalid source tier: "+sourceTier);

		this.sinkTier = sinkTier;
		this.sourceTier = sourceTier;
		double power = EnergyNet.instance.getPowerFromTier(sourceTier);

		if (getCapacity() < power) setCapacity(power);
	}

	/**
	 * Set the IC2 energy tier for this sink.
	 *
	 * @param tier IC2 Tier.
	 */
	public void setSinkTier(int tier) {
		if (tier < 0) throw new IllegalArgumentException("invalid tier: "+tier);

		this.sinkTier = tier;
	}

	/**
	 * Set the IC2 energy tier for this source.
	 *
	 * @param tier IC2 Tier.
	 */
	public void setSourceTier(int tier) {
		if (tier < 0) throw new IllegalArgumentException("invalid tier: "+tier);

		double power = EnergyNet.instance.getPowerFromTier(tier);

		if (getCapacity() < power) setCapacity(power);

		this.sourceTier = tier;
	}

	// energy net interface >>

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
		return sinkTier;
	}

	@Override
	public double getOfferedEnergy() {
		return getEnergyStored();
	}

	@Override
	public void drawEnergy(double amount) {
		setEnergyStored(getEnergyStored() - amount);
	}

	@Override
	public int getSourceTier() {
		return sourceTier;
	}

	// << energy net interface

	@Override
	protected String getNbtTagName() {
		return "IC2BasicSinkSource";
	}

	protected int sinkTier;
	protected int sourceTier;
}
