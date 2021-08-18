package com.iteale.industrialcase.core.block.comp;


import com.iteale.industrialcase.api.energy.EnergyNet;
import com.iteale.industrialcase.api.energy.event.EnergyTileLoadEvent;
import com.iteale.industrialcase.api.energy.event.EnergyTileUnloadEvent;
import com.iteale.industrialcase.api.energy.tile.*;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.BlockEntityBase;
import com.iteale.industrialcase.core.block.invslot.InvSlot;
import com.iteale.industrialcase.core.util.LogCategory;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;

import java.util.*;

public class Energy extends BlockEntityComponent {

    public static Energy asBasicSink(BlockEntityBase parent, double capacity) {
        return asBasicSink(parent, capacity, 1);
    }

    public static Energy asBasicSink(BlockEntityBase parent, double capacity, int tier) {
        return new Energy(parent, capacity, new HashSet<Direction>(Arrays.asList(Direction.values())), Collections.EMPTY_SET, tier);
    }

    public static Energy asBasicSource(BlockEntityBase parent, double capacity) {
        return asBasicSource(parent, capacity, 1);
    }

    public static Energy asBasicSource(BlockEntityBase parent, double capacity, int tier) {
        return new Energy(parent, capacity, Collections.EMPTY_SET, new HashSet<Direction>(Arrays.asList(Direction.values())), tier);
    }

    public Energy(BlockEntityBase parent, double capacity) {
        this(parent, capacity, Collections.EMPTY_SET, Collections.EMPTY_SET, 1);
    }

    public Energy(BlockEntityBase parent, double capacity, Set<Direction> sinkDirections, Set<Direction> sourceDirections, int tier) {
        this(parent, capacity, sinkDirections, sourceDirections, tier, tier, false);
    }

    public Energy(BlockEntityBase parent, double capacity, Set<Direction> sinkDirections, Set<Direction> sourceDirections, int sinkTier, int sourceTier, boolean fullEnergy) {
        super(parent);
        this.multiSource = false;
        this.sourcePackets = 1;
        this.capacity = capacity;
        this.sinkTier = sinkTier;
        this.sourceTier = sourceTier;
        this.sinkDirections = sinkDirections;
        this.sourceDirections = sourceDirections;
        this.fullEnergy = fullEnergy;
    }

    public Energy addManagedSlot(InvSlot slot) {
        if (slot instanceof IChargingSlot || slot instanceof IDischargingSlot) {
            if (this.managedSlots == null)
                this.managedSlots = new ArrayList<>(4);
            this.managedSlots.add(slot);
        } else {
            throw new IllegalArgumentException("No charge/discharge slot.");
        }
        return this;
    }

    public void load(CompoundTag nbt) {
        this.storage = nbt.getDouble("storage");
    }

    public CompoundTag save() {
        CompoundTag ret = new CompoundTag();
        ret.putDouble("storage", this.storage);
        return ret;
    }

    public boolean enableWorldTick() {
        return true;
    }

    public void onWorldTick() {
        for (InvSlot slot : this.managedSlots) {
            if (slot instanceof IChargingSlot) {
                if (this.storage > 0.0D)
                    this.storage -= ((IChargingSlot)slot).charge(this.storage);
                continue;
            }
            if (slot instanceof IDischargingSlot) {
                double space = this.capacity - this.storage;
                if (space > 0.0D)
                    this.storage += ((IDischargingSlot)slot).discharge(space, false);
            }
        }
    }

    public void onLoaded() {
        assert this.delegate == null;
        if (!this.parent.getLevel().isClientSide) {
            if (!this.sinkDirections.isEmpty() || !this.sourceDirections.isEmpty()) {
                if (debugLoad)
                    IndustrialCase.log.debug(LogCategory.Component, "Energy onLoaded for %s at %s.", this.parent, Util.formatPosition(this.parent));
                createDelegate();
                MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this.delegate));
            } else if (debugLoad) {
                IndustrialCase.log.debug(LogCategory.Component, "Skipping Energy onLoaded for %s at %s.", this.parent, Util.formatPosition(this.parent));
            }
            this.loaded = true;
        }
    }

    public void onUnloaded() {
        if (this.delegate != null) {
            if (debugLoad)
                IndustrialCase.log.debug(LogCategory.Component, "Energy onUnloaded for %s at %s.", this.parent, Util.formatPosition(this.parent));
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this.delegate));
            this.delegate = null;
        } else if (debugLoad) {
            IndustrialCase.log.debug(LogCategory.Component, "Skipping Energy onUnloaded for %s at %s.", this.parent, Util.formatPosition(this.parent));
        }
        this.loaded = false;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getStorage() {
        return this.storage;
    }

    public void setStorage(double value) {
        this.storage = value;
    }

    public double getFreeEnergy() {
        return Math.max(0.0D, this.capacity - this.storage);
    }

    public double getFillRatio() {
        return this.storage / this.capacity;
    }

    public int getComparatorValue() {
        return Math.min((int)(this.storage * 15.0D / this.capacity), 15);
    }

    public double addEnergy(double amount) {
        amount = Math.min(this.capacity - this.storage, amount);
        this.storage += amount;
        return amount;
    }

    private void createDelegate() {
        if (this.delegate != null)
            throw new IllegalStateException();
        assert !this.sinkDirections.isEmpty() || !this.sourceDirections.isEmpty();
        if (this.sinkDirections.isEmpty()) {
            this.delegate = new EnergyNetDelegateSource(this.parent.getType(), this.parent.getBlockPos(), this.parent.getBlockState());
        } else if (this.sourceDirections.isEmpty()) {
            this.delegate = new EnergyNetDelegateSink(this.parent.getType(), this.parent.getBlockPos(), this.parent.getBlockState());
        } else {
            this.delegate = new EnergyNetDelegateDual(this.parent.getType(), this.parent.getBlockPos(), this.parent.getBlockState());
        }
        this.delegate.setLevel(this.parent.getLevel());
    }

    public void setDirections(Set<Direction> sinkDirections, Set<Direction> sourceDirections) {
        if (sinkDirections.equals(this.sinkDirections) && sourceDirections.equals(this.sourceDirections)) {
            if (debugLoad)
                IndustrialCase.log.debug(LogCategory.Component, "Energy setDirections unchanged for %s at %s, sink: %s, source: %s.", this.parent, Util.formatPosition(this.parent), sinkDirections, sourceDirections);
            return;
        }
        if (this.delegate != null) {
            if (debugLoad)
                IndustrialCase.log.debug(LogCategory.Component, "Energy setDirections unload for %s at %s.", this.parent, Util.formatPosition(this.parent));
            assert !(this.parent.getLevel()).isClientSide;
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this.delegate));
        }
        this.sinkDirections = sinkDirections;
        this.sourceDirections = sourceDirections;
        if (sinkDirections.isEmpty() && sourceDirections.isEmpty()) {
            this.delegate = null;
        } else if (this.delegate == null && this.loaded) {
            createDelegate();
        }
        if (this.delegate != null) {
            if (debugLoad)
                IndustrialCase.log.debug(LogCategory.Component, "Energy setDirections load for %s at %s, sink: %s, source: %s.", this.parent, Util.formatPosition(this.parent), sinkDirections, sourceDirections);
            assert !(this.parent.getLevel()).isClientSide;
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this.delegate));
        } else if (debugLoad) {
            IndustrialCase.log.debug(LogCategory.Component, "Skipping Energy setDirections load for %s at %s, sink: %s, source: %s, loaded: %b.", this.parent, Util.formatPosition(this.parent), sinkDirections, sourceDirections, Boolean.valueOf(this.loaded));
        }
    }


    public Set<Direction> getSourceDirs() {
        return Collections.unmodifiableSet(this.sourceDirections);
    }

    public Set<Direction> getSinkDirs() {
        return Collections.unmodifiableSet(this.sinkDirections);
    }

    public IEnergyTile getDelegate() {
        return this.delegate;
    }

    private double getSourceEnergy() {
        if (this.fullEnergy)
            return (this.storage >= EnergyNet.instance.getPowerFromTier(this.sourceTier)) ? this.storage : 0.0D;
        return this.storage;
    }

    private int getPacketCount() {
        if (this.fullEnergy)
            return Math.min(this.sourcePackets, (int)Math.floor(this.storage / EnergyNet.instance.getPowerFromTier(this.sourceTier)));
        return this.sourcePackets;
    }

    private class EnergyNetDelegate extends BlockEntity implements IEnergyTile {
        public EnergyNetDelegate(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
            super(blockEntityType, pos, state);
        }
    }

    private class EnergyNetDelegateSource extends EnergyNetDelegate implements IMultiEnergySource {
        public EnergyNetDelegateSource(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
            super(blockEntityType, pos, state);
        }

        public int getSourceTier() {
            return Energy.this.sourceTier;
        }

        public boolean emitsEnergyTo(IEnergyAcceptor receiver, Direction dir) {
            return Energy.this.sourceDirections.contains(dir);
        }

        public double getOfferedEnergy() {
            assert !Energy.this.sourceDirections.isEmpty();
            return !Energy.this.sendingSidabled ? Energy.this.getSourceEnergy() : 0.0D;
        }

        public void drawEnergy(double amount) {
            assert amount <= Energy.this.storage;
            Energy.this.storage = Energy.this.storage - amount;
        }

        public boolean sendMultipleEnergyPackets() {
            return Energy.this.multiSource;
        }

        public int getMultipleEnergyPacketAmount() {
            return Energy.this.getPacketCount();
        }
    }

    private class EnergyNetDelegateSink extends EnergyNetDelegate implements IEnergySink {
        public EnergyNetDelegateSink(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
            super(blockEntityType, pos, state);
        }

        public int getSinkTier() {
            return Energy.this.sinkTier;
        }

        public boolean acceptsEnergyFrom(IEnergyEmitter emitter, Direction dir) {
            return Energy.this.sinkDirections.contains(dir);
        }

        public double getDemandedEnergy() {
            assert !Energy.this.sinkDirections.isEmpty();
            return (!Energy.this.receivingDisabled && Energy.this.storage < Energy.this.capacity) ? (Energy.this.capacity - Energy.this.storage) : 0.0D;
        }

        public double injectEnergy(Direction directionFrom, double amount, double voltage) {
            Energy.this.storage = Energy.this.storage + amount;
            return 0.0D;
        }
    }

    private class EnergyNetDelegateDual extends EnergyNetDelegate implements IEnergySink, IMultiEnergySource {
        public EnergyNetDelegateDual(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
            super(blockEntityType, pos, state);
        }

        public boolean acceptsEnergyFrom(IEnergyEmitter emitter, Direction dir) {
            return Energy.this.sinkDirections.contains(dir);
        }

        public boolean emitsEnergyTo(IEnergyAcceptor receiver, Direction dir) {
            return Energy.this.sourceDirections.contains(dir);
        }

        public double getDemandedEnergy() {
            return (!Energy.this.receivingDisabled && !Energy.this.sinkDirections.isEmpty() && Energy.this.storage < Energy.this.capacity) ? (Energy.this.capacity - Energy.this.storage) : 0.0D;
        }

        public double getOfferedEnergy() {
            return (!Energy.this.sendingSidabled && !Energy.this.sourceDirections.isEmpty()) ? Energy.this.getSourceEnergy() : 0.0D;
        }

        public int getSinkTier() {
            return Energy.this.sinkTier;
        }

        public int getSourceTier() {
            return Energy.this.sourceTier;
        }

        public double injectEnergy(Direction directionFrom, double amount, double voltage) {
            Energy.this.storage = Energy.this.storage + amount;
            return 0.0D;
        }

        public void drawEnergy(double amount) {
            assert amount <= Energy.this.storage;
            Energy.this.storage = Energy.this.storage - amount;
        }

        public boolean sendMultipleEnergyPackets() {
            return Energy.this.multiSource;
        }

        public int getMultipleEnergyPacketAmount() {
            return Energy.this.getPacketCount();
        }
    }

    private static final boolean debugLoad = false;
    private double capacity;
    private double storage;
    private int sinkTier;
    private int sourceTier;
    private Set<Direction> sinkDirections;
    private Set<Direction> sourceDirections;
    private List<InvSlot> managedSlots;
    private boolean multiSource;
    private int sourcePackets;
    private EnergyNetDelegate delegate;
    private boolean loaded;
    private boolean receivingDisabled;
    private boolean sendingSidabled;
    private final boolean fullEnergy;
}
