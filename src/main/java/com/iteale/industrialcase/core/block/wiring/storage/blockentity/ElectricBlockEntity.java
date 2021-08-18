package com.iteale.industrialcase.core.block.wiring.storage.blockentity;

import com.iteale.industrialcase.core.block.BlockEntityInventory;
import com.iteale.industrialcase.core.block.comp.Energy;
import com.iteale.industrialcase.core.block.comp.Redstone;
import com.iteale.industrialcase.core.block.comp.RedstoneEmitter;
import com.iteale.industrialcase.core.block.invslot.InvSlotCharge;
import com.iteale.industrialcase.core.block.invslot.InvSlotDischarge;
import com.iteale.industrialcase.core.block.invslot.InvSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public abstract class ElectricBlockEntity extends BlockEntityInventory {
    protected double output;
    public byte redstoneMode;

    public final InvSlotCharge chargeSlot;
    public final InvSlotDischarge dischargeSlot;
    public final Energy energy;
    public final Redstone redstone;
    public final RedstoneEmitter rsEmitter;

    public static byte redstoneModes = 7;

    public ContainerData dataAccess = new ElectricContainerData(this);

    public ElectricBlockEntity(BlockEntityType<?> t, BlockPos pos, BlockState state, int tier, int output, int maxStorage) {
        super(t, pos, state);
        this.redstoneMode = 0;
        this.output = output;
        this.chargeSlot = new InvSlotCharge(this, tier);
        this.dischargeSlot = new InvSlotDischarge(this, InvSlot.Access.IO, tier, InvSlot.InvSide.BOTTOM);
        this.energy = (Energy)addComponent(
                (new Energy(this, maxStorage, EnumSet.complementOf(EnumSet.of(Direction.DOWN)), EnumSet.of(Direction.DOWN), tier, tier, true))
                        .addManagedSlot(this.chargeSlot).addManagedSlot(this.dischargeSlot));
        this.rsEmitter = (RedstoneEmitter)addComponent(new RedstoneEmitter(this));
        this.redstone = (Redstone)addComponent(new Redstone(this));
        this.comparator.setUpdate(this.energy::getComparatorValue);
    }


    public void load(CompoundTag nbt) {
        superLoad(nbt);
        // this.energy.setDirections(EnumSet.complementOf(EnumSet.of(getFacing())), EnumSet.of(getFacing()));
    }

    protected final void superLoad(CompoundTag nbt) {
        super.load(nbt);
        this.redstoneMode = nbt.getByte("redstoneMode");
    }

    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        nbt.putByte("redstoneMode", this.redstoneMode);
        return nbt;
    }


    public enum StorageDataType {
        STORAGE(0),
        CAPACITY(1);

        private final int value;
        private StorageDataType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}