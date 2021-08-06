package com.iteale.industrialcase.core.world.feature;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = IndustrialCase.MODID)
public class OreGen {
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        genOres(gen);
    }
    public static void genOres(BiomeGenerationSettingsBuilder gen) {
        List<Supplier<ConfiguredFeature<?, ?>>> ores = gen.getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        ores.add(() -> {
            return Feature.ORE.configured(
                    new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, BlockRegistry.TIN_ORE.get().defaultBlockState(), 10)
            ).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)
            ).count(20);
        });

        ores.add(() -> {
            return Feature.ORE.configured(
                    new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, BlockRegistry.LEAD_ORE.get().defaultBlockState(), 10)
            ).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)
            ).count(20);
        });
    }

    // BlockRegistry
}
