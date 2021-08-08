package com.iteale.industrialcase.core.datagen.blockstate;

import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class WiringBlockState extends ICBlockStateProvider {
    public WiringBlockState(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerCable(BlockRegistry.COPPER_CABLE.get(), 0.25F);
        registerCable(BlockRegistry.TIN_CABLE.get(), 0.25F);
        registerCable(BlockRegistry.IRON_CABLE.get(), 0.375F);
        registerCable(BlockRegistry.GLASS_CABLE.get(), 0.25F);
        registerCable(BlockRegistry.GOLD_CABLE.get(), 0.1875F);

        registerCable(BlockRegistry.COPPER_CABLE_INSULATED.get(), 0.375F);
        registerCable(BlockRegistry.COPPER_CABLE_INSULATED_BLUE.get(), 0.375F);
    }
}
