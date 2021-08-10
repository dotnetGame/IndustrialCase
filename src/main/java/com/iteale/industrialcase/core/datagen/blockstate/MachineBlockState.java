package com.iteale.industrialcase.core.datagen.blockstate;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MachineBlockState extends ICBlockStateProvider {
    public MachineBlockState(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerHorizontalBlock(
                BlockRegistry.IRON_FURNACE.get(),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_bottom"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_top"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_front"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_leftrightback"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_leftrightback"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_leftrightback")
                );
    }
}
