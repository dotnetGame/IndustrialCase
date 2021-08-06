package com.iteale.industrialcase.core.datagen.blockmodel;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MachineBlockModel extends ICBlockModelProvider  {
    public MachineBlockModel(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // processing/basic
        cube("block/machine/processing/basic/iron_furnace",
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_bottom"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_top"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_front"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_leftrightback"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_leftrightback"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_leftrightback")
        ).texture("particle", "block/machine/processing/basic/iron_furnace_leftrightback");

        cube("block/machine/processing/basic/iron_furnace_activate",
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_bottom"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_top"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_front_active"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_leftrightback"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_leftrightback"),
                new ResourceLocation(IndustrialCase.MODID, "block/machine/processing/basic/iron_furnace_leftrightback")
        ).texture("particle", "block/machine/processing/basic/iron_furnace_leftrightback");
    }
}
