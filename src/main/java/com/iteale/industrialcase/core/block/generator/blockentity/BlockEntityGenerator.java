package com.iteale.industrialcase.core.block.generator.blockentity;

import com.iteale.industrialcase.core.block.invslot.InvSlotConsumableFuel;
import com.iteale.industrialcase.core.gui.dynamic.IGuiValueProvider;
import com.iteale.industrialcase.core.network.GuiSynced;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockEntityGenerator
        extends BlockEntityBaseGenerator
        implements IGuiValueProvider
{
    public final InvSlotConsumableFuel fuelSlot;
    @GuiSynced
    public int totalFuel;

    public BlockEntityGenerator(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.IRON_FURNACE.get(), pos, state, Math.round(10.0F * 1.0F), 1, 4000);

        this.totalFuel = 0;
        this.fuelSlot = new InvSlotConsumableFuel(this, "fuel", 1, false);
    }

    @OnlyIn(Dist.CLIENT)
    protected void updateEntityClient() {
        // super.updateEntityClient();
        // if (getActive())
        // TileEntityIronFurnace.showFlames(getWorld(), this.pos, getFacing());
    }

    public double getFuelRatio() {
        if (this.fuel <= 0)
            return 0.0D;
        return this.fuel / this.totalFuel;
    }

    public boolean gainFuel() {
        int fuelValue = this.fuelSlot.consumeFuel() / 4;
        if (fuelValue == 0)
            return false;
        this.fuel += fuelValue;
        this.totalFuel = fuelValue;
        return true;
    }

    public boolean isConverting() {
        return (this.fuel > 0);
    }

    public String getOperationSoundFile() {
        return "Generators/GeneratorLoop.ogg";
    }

    public double getGuiValue(String name) {
        if ("fuel".equals(name))
            return getFuelRatio();
        throw new IllegalArgumentException();
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.totalFuel = nbt.getInt("totalFuel");
    }

    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        nbt.putInt("totalFuel", this.totalFuel);
        return nbt;
    }
}