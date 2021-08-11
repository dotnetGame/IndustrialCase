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
        registerMachine(BlockRegistry.IRON_FURNACE.get());
        registerMachine(BlockRegistry.GENERATOR.get());
    }
}
