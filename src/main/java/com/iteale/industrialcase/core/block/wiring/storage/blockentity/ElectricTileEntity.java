package com.iteale.industrialcase.core.block.wiring.storage.blockentity;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.MenuBase;
import com.iteale.industrialcase.core.block.TileEntityInventory;
import com.iteale.industrialcase.core.block.comp.Energy;
import com.iteale.industrialcase.core.block.comp.Redstone;
import com.iteale.industrialcase.core.block.comp.RedstoneEmitter;
import com.iteale.industrialcase.core.block.invslot.InvSlotCharge;
import com.iteale.industrialcase.core.block.invslot.InvSlotDischarge;
import com.iteale.industrialcase.core.block.invslot.InvSlot;
import com.iteale.industrialcase.core.block.wiring.storage.gui.ElectricScreen;
import com.iteale.industrialcase.core.block.wiring.storage.menu.ElectricMenu;
import com.iteale.industrialcase.core.init.Localization;
import com.iteale.industrialcase.core.util.ConfigUtil;
import com.iteale.industrialcase.core.util.StackUtil;
import javafx.stage.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumSet;

public abstract class ElectricTileEntity extends TileEntityInventory {
    protected double output;
    public byte redstoneMode;

    public final InvSlotCharge chargeSlot;
    public final InvSlotDischarge dischargeSlot;
    public final Energy energy;
    public final Redstone redstone;
    public final RedstoneEmitter rsEmitter;

    public static byte redstoneModes = 7;

    public ContainerData dataAccess = new ElectricContainerData(this);

    public ElectricTileEntity(BlockEntityType<?> t, BlockPos pos, BlockState state, int tier, int output, int maxStorage) {
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
        this.energy.setDirections(EnumSet.complementOf(EnumSet.of(getFacing())), EnumSet.of(getFacing()));
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


    public MenuBase<? extends ElectricTileEntity> getGuiContainer(Player player) {
        return (MenuBase<? extends ElectricTileEntity>)new ElectricMenu(player, this);
    }

    @OnlyIn(Dist.CLIENT)
    public Screen getGui(Player player, boolean isAdmin) {
        return (Screen)new ElectricScreen<>(new ContainerElectricBlock(player, this));
    }

    public void onGuiClosed(Player player) {}

    public void setFacing(Direction facing) {
        super.setFacing(facing);
        this.energy.setDirections(EnumSet.complementOf((EnumSet)EnumSet.of(getFacing())), EnumSet.of(getFacing()));
    }

    protected final void superSetFacing(Direction facing) {
        super.setFacing(facing);
    }

    protected boolean shouldEmitRedstone() {
        switch (this.redstoneMode) {
            case 1:
                return (this.energy.getEnergy() >= this.energy.getEnergy() - this.output * 20.0D);
            case 2:
                return (this.energy.getEnergy() > this.output && this.energy.getEnergy() < this.energy.getEnergy() - this.output);
            case 3:
                return (this.energy.getEnergy() < this.energy.getEnergy() - this.output);
            case 4:
                return (this.energy.getEnergy() < this.output);
        }
        return false;
    }

    protected boolean shouldEmitEnergy() {
        boolean redstone = this.redstone.hasRedstoneInput();
        if (this.redstoneMode == 5)
            return !redstone;
        if (this.redstoneMode == 6)
            return (!redstone || this.energy.getEnergy() > this.energy.getEnergy() - this.output * 20.0D);
        return true;
    }

    public void onNetworkEvent(Player player, int event) {
        this.redstoneMode = (byte)(this.redstoneMode + 1);
        if (this.redstoneMode >= redstoneModes)
            this.redstoneMode = 0;
        IndustrialCase.platform.messagePlayer(player, getRedstoneMode());
    }

    public String getRedstoneMode() {
        if (this.redstoneMode >= redstoneModes || this.redstoneMode < 0)
            return "";
        return Localization.translate("ic2.EUStorage.gui.mod.redstone" + this.redstoneMode);
    }

    public void onPlaced(ItemStack stack, LivingEntity placer, Direction facing) {
        super.onPlaced(stack, placer, facing);
        if (!getLevel().isClientSide) {
            CompoundTag nbt = StackUtil.getOrCreateNbtData(stack);
            this.energy.addEnergy(nbt.getDouble("energy"));
        }
    }

    public void onUpgraded() {
        rerender();
    }

    protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
        drop = super.adjustDrop(drop, wrench);
        if (wrench || this.teBlock.getDefaultDrop() == TeBlock.DefaultDrop.Self) {
            double retainedRatio = ConfigUtil.getDouble(MainConfig.get(), "balance/energyRetainedInStorageBlockDrops");
            double totalEnergy = this.energy.getEnergy();
            if (retainedRatio > 0.0D && totalEnergy > 0.0D) {
                CompoundTag nbt = StackUtil.getOrCreateNbtData(drop);
                nbt.putDouble("energy", totalEnergy * retainedRatio);
            }
        }
        return drop;
    }

    public int getOutput() {
        return (int)this.output;
    }

    public double getOutputEnergyUnitsPerTick() {
        return this.output;
    }

    public void setStored(int energy) {}

    public int addEnergy(int amount) {
        this.energy.addEnergy(amount);
        return amount;
    }

    public int getStored() {
        return (int)this.energy.getEnergy();
    }

    public int getCapacity() {
        return (int)this.energy.getEnergy();
    }

    public boolean isTeleporterCompatible(Direction side) {
        return true;
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