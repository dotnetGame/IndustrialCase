package com.iteale.industrialcase.core.block.comp;


import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Energy {

    public static Energy asBasicSink(BlockEntity parent, double capacity) {
        return asBasicSink(parent, capacity, 1);
    }

    public static Energy asBasicSink(BlockEntity parent, double capacity, int tier) {
        return new Energy(parent, capacity, new HashSet<Direction>(Arrays.asList(Direction.values())), Collections.EMPTY_SET, tier);
    }

    public static Energy asBasicSource(BlockEntity parent, double capacity) {
        return asBasicSource(parent, capacity, 1);
    }

    public static Energy asBasicSource(BlockEntity parent, double capacity, int tier) {
        return new Energy(parent, capacity, Collections.EMPTY_SET, new HashSet<Direction>(Arrays.asList(Direction.values())), tier);
    }

    public Energy(BlockEntity parent, double capacity) {
        this(parent, capacity, Collections.EMPTY_SET, Collections.EMPTY_SET, 1);
    }

    public Energy(BlockEntity parent, double capacity, Set<Direction> sinkDirections, Set<Direction> sourceDirections, int tier) {
        this(parent, capacity, sinkDirections, sourceDirections, tier, tier, false);
    }

    public Energy(BlockEntity parent, double capacity, Set<Direction> sinkDirections, Set<Direction> sourceDirections, int sinkTier, int sourceTier, boolean fullEnergy) {
        this.multiSource = false;
        this.sourcePackets = 1;
        this.capacity = capacity;
        this.sinkTier = sinkTier;
        this.sourceTier = sourceTier;
        this.sinkDirections = sinkDirections;
        this.sourceDirections = sourceDirections;
        this.fullEnergy = fullEnergy;
    }

    public void load(CompoundTag nbt) {
        this.storage = nbt.getDouble("storage");
    }

    public CompoundTag writeToNbt() {
        CompoundTag ret = new CompoundTag();
        ret.putDouble("storage", this.storage);
        return ret;
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

    private double capacity;
    private double storage;
    private int sinkTier;
    private int sourceTier;
    private Set<Direction> sinkDirections;
    private Set<Direction> sourceDirections;
    private boolean multiSource;
    private int sourcePackets;
    private boolean loaded;
    private boolean receivingDisabled;
    private boolean sendingSidabled;
    private final boolean fullEnergy;
}
