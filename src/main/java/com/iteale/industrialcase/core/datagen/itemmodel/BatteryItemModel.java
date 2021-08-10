package com.iteale.industrialcase.core.datagen.itemmodel;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BatteryItemModel extends ICItemModelProvider {
    public BatteryItemModel(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerBattery(ItemRegistry.RE_BATTERY.get());
        registerBattery(ItemRegistry.ADVANCED_RE_BATTERY.get());
    }
}
