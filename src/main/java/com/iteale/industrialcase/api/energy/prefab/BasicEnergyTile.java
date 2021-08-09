package com.iteale.industrialcase.api.energy.prefab;

import com.iteale.industrialcase.api.energy.event.EnergyTileLoadEvent;
import com.iteale.industrialcase.api.energy.event.EnergyTileUnloadEvent;
import com.iteale.industrialcase.api.energy.tile.IEnergyTile;
import com.iteale.industrialcase.api.info.ILocatable;
import com.iteale.industrialcase.api.info.Info;
import com.iteale.industrialcase.api.item.ElectricItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;


/**
 * Common functionality for BasicSink and BasicSource.
 */
abstract class BasicEnergyTile implements ILocatable, IEnergyTile {
	// **********************************
	// *** Methods for use by the mod ***
	// **********************************

	/**
	 * Constructor for a new BasicSink delegate.
	 *
	 * @param parent TileEntity represented by this energy sink.
	 * @param capacity Maximum amount of eu to store.
	 */
	protected BasicEnergyTile(BlockEntity parent, double capacity) {
		this((Object) parent, capacity);
	}

	protected BasicEnergyTile(ILocatable parent, double capacity) {
		this((Object) parent, capacity);
	}

	private BasicEnergyTile(Object locationProvider, double capacity) {
		this.locationProvider = locationProvider;
		this.capacity = capacity;
	}

	protected BasicEnergyTile(Level world, BlockPos pos, double capacity) {
		if (world == null) throw new NullPointerException("null world");
		if (pos == null) throw new NullPointerException("null pos");

		this.locationProvider = null;
		this.world = world;
		this.pos = pos;
		this.capacity = capacity;
	}

	// in-world te forwards	>>

	/**
	 * Forward for the base TileEntity's update(), used for creating the energy net link.
	 * Either update or onLoad have to be used.
	 */
	public void update() {
		if (!addedToEnet) onLoad();
	}

	/**
	 * Notification that the base TileEntity is loaded, used for creating the energy net link.
	 * Either update or onLoad have to be used.
	 */
	public void onLoad() {
		if (!addedToEnet
				&& !getWorldObj().isClientSide
				&& Info.isIc2Available()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));

			addedToEnet = true;
		}
	}

	/**
	 * Forward for the base TileEntity's invalidate(), used for destroying the energy net link.
	 * Both invalidate and onChunkUnload have to be used.
	 */
	public void invalidate() {
		onChunkUnload();
	}

	/**
	 * Forward for the base TileEntity's onChunkUnload(), used for destroying the energy net link.
	 * Both invalidate and onChunkUnload have to be used.
	 */
	public void onChunkUnload() {
		if (addedToEnet
				&& !getWorldObj().isClientSide
				&& Info.isIc2Available()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));

			addedToEnet = false;
		}
	}

	/**
	 * Forward for the base TileEntity's readFromNBT(), used for loading the state.
	 *
	 * @param tag Compound tag as supplied by TileEntity.readFromNBT()
	 */
	public void load(CompoundTag tag) {
		CompoundTag data = tag.getCompound(getNbtTagName());
		setEnergyStored(data.getDouble("energy"));
	}

	/**
	 * Forward for the base TileEntity's writeToNBT(), used for saving the state.
	 *
	 */
	public CompoundTag save(CompoundTag tag) {
		CompoundTag data = new CompoundTag();

		data.putDouble("energy", getEnergyStored());
		tag.put(getNbtTagName(), data);

		return tag;
	}

	// << in-world te forwards
	// methods for using this adapter >>

	/**
	 * Get the maximum amount of energy this sink can hold in its buffer.
	 *
	 * @return Capacity in EU.
	 */
	public double getCapacity() {
		return capacity;
	}

	/**
	 * Set the maximum amount of energy this sink can hold in its buffer.
	 *
	 * @param capacity Capacity in EU.
	 */
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	/**
	 * Determine the energy stored in this buffer.
	 *
	 * @return amount in EU, may be above capacity
	 */
	public double getEnergyStored() {
		return energyStored;
	}

	/**
	 * Set the stored energy to the specified amount.
	 *
	 * This is intended for server -> client synchronization, e.g. to display the stored energy in
	 * a GUI through getEnergyStored().
	 *
	 * @param amount
	 */
	public void setEnergyStored(double amount) {
		energyStored = amount;
	}

	/**
	 * Determine the free capacity in this buffer.
	 *
	 * @return amount in EU
	 */
	public double getFreeCapacity() {
		return getCapacity() - getEnergyStored();
	}

	/**
	 * Add some energy to the output buffer.
	 *
	 * @param amount maximum amount of energy to add
	 * @return amount added/used, NOT remaining
	 */
	public double addEnergy(double amount) {
		if (getWorldObj().isClientSide) return 0;

		double energyStored = getEnergyStored();
		double capacity = getCapacity();

		if (amount > capacity - energyStored) amount = capacity - energyStored;

		setEnergyStored(energyStored + amount);

		return amount;
	}

	/**
	 * Determine if the specified amount of energy is available.
	 *
	 * @param amount in EU
	 * @return true if the amount is available
	 */
	public boolean canUseEnergy(double amount) {
		return getEnergyStored() >= amount;
	}

	/**
	 * Use the specified amount of energy, if available.
	 *
	 * @param amount amount to use
	 * @return true if the amount was available
	 */
	public boolean useEnergy(double amount) {
		if (!canUseEnergy(amount) || getWorldObj().isClientSide) return false;

		setEnergyStored(getEnergyStored() - amount);

		return true;
	}

	/**
	 * Charge the supplied ItemStack from this energy buffer.
	 *
	 * @param stack ItemStack to charge (null is ignored)
	 * @return true if energy was transferred
	 */
	public boolean charge(ItemStack stack) {
		if (stack == null || !Info.isIc2Available() || getWorldObj().isClientSide) return false;

		double energyStored = getEnergyStored();
		double amount = ElectricItem.manager.charge(stack, energyStored, Math.max(getSinkTier(), getSourceTier()), false, false);

		setEnergyStored(energyStored - amount);

		return amount > 0;
	}

	/**
	 * Discharge the supplied ItemStack into this energy buffer.
	 *
	 * @param stack ItemStack to discharge (null is ignored)
	 * @param limit Transfer limit, values <= 0 will use the battery's limit
	 * @return true if energy was transferred
	 */
	public boolean discharge(ItemStack stack, double limit) {
		if (stack == null || !Info.isIc2Available() || getWorldObj().isClientSide) return false;

		double energyStored = getEnergyStored();
		double amount = getCapacity() - energyStored;
		if (amount <= 0) return false;

		if (limit > 0 && limit < amount) amount = limit;

		amount = ElectricItem.manager.discharge(stack, amount, Math.max(getSinkTier(), getSourceTier()), limit > 0, true, false);

		setEnergyStored(energyStored + amount);

		return amount > 0;
	}

	// << methods for using this adapter

	// ******************************
	// *** Methods for use by ic2 ***
	// ******************************

	// ILocatable >>

	@Override
	public Level getWorldObj() {
		if (world == null) initLocation();

		return world;
	}

	@Override
	public BlockPos getPosition() {
		if (pos == null) initLocation();

		return pos;
	}

	private void initLocation() {
		if (locationProvider instanceof ILocatable) {
			ILocatable provider = (ILocatable) locationProvider;
			world = provider.getWorldObj();
			pos = provider.getPosition();
		} else if (locationProvider instanceof BlockEntity) {
			BlockEntity provider = (BlockEntity) locationProvider;
			world = provider.getLevel();
			pos = provider.getBlockPos();
		} else {
			throw new IllegalStateException("no/incompatible location provider");
		}
	}

	// << ILocatable

	protected abstract String getNbtTagName();

	protected int getSinkTier() {
		return 0;
	}

	protected int getSourceTier() {
		return 0;
	}

	private final Object locationProvider;
	protected Level world;
	protected BlockPos pos;

	protected double capacity;
	protected double energyStored;
	protected boolean addedToEnet;
}
