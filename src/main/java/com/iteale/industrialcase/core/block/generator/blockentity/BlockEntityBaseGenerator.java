package com.iteale.industrialcase.core.block.generator.blockentity;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.BlockEntityInventory;
import com.iteale.industrialcase.core.block.comp.Energy;
import com.iteale.industrialcase.core.block.invslot.InvSlotCharge;
import com.iteale.industrialcase.core.network.GuiSynced;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BlockEntityBaseGenerator
        extends BlockEntityInventory
{
    // public final InvSlotCharge chargeSlot;
    // protected final Energy energy;
    @GuiSynced
    public int fuel;
    protected double production;
    private int ticksSinceLastActiveUpdate;
    private int activityMeter;
    public SoundSource audioSource;

    public BlockEntityBaseGenerator(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, double production, int tier, int maxStorage) {
        super(blockEntityType, pos, state);
        this.fuel = 0;

        this.activityMeter = 0;
        this.production = production;
        this.ticksSinceLastActiveUpdate = IndustrialCase.random.nextInt(256);
        // this.chargeSlot = new InvSlotCharge(this, 1);
        // this.energy = addComponent(Energy.asBasicSource(this, maxStorage, tier).addManagedSlot(this.chargeSlot));
    }

    public void load(CompoundTag nbttagcompound) {
        super.load(nbttagcompound);
        this.fuel = nbttagcompound.getInt("fuel");
    }

    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        nbt.putInt("fuel", this.fuel);
        return nbt;
    }
}
