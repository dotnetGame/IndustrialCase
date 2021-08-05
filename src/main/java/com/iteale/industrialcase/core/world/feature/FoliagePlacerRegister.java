package com.iteale.industrialcase.core.world.feature;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.machine.IronFurnace;
import com.iteale.industrialcase.core.block.machine.TileEntityIronFurnace;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class FoliagePlacerRegister {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, IndustrialCase.MODID);

    // rubber
    // public static final FoliagePlacerType RUBBER_FOLIAGE_PLACER = register("rubber_foliage_placer", new RubberFoliagePlacer(FeatureSpread.of(2, 1), FeatureSpread.of(0, 2), FeatureSpread.of(1, 1)));
    // register methods
    public static FoliagePlacerType register(String name, FoliagePlacer placer) {
        FoliagePlacerType placerType= new FoliagePlacerType(placer.CODEC);
        FOLIAGE_PLACERS.register(name, ()-> {
            return placerType;
        });
        return placerType;
    }
}
