package com.iteale.industrialcase.core.world.feature;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HeightmapConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.WaterDepthThresholdConfiguration;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = IndustrialCase.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TreeGen {
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder gen = event.getGeneration();

        if (event.getCategory() == Biome.BiomeCategory.FOREST ||
                event.getCategory() == Biome.BiomeCategory.PLAINS) {
            if (IndustrialCase.MAIN_CONFIG.rubberTree) {
                genRubberTree(gen);
            }
        }
    }

    public static void genRubberTree(BiomeGenerationSettingsBuilder gen) {
        List<Supplier<ConfiguredFeature<?, ?>>> vege = gen.getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION);
        vege.add(() -> Feature.TREE.configured(RubberTree.getTreeConfig())
                        .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.OCEAN_FLOOR))
                                .decorated(FeatureDecorator.WATER_DEPTH_THRESHOLD.configured(new WaterDepthThresholdConfiguration(0)))
                                .squared()
                        )
                        .decorated(FeatureDecorator.COUNT_EXTRA.configured(
                                new FrequencyWithExtraChanceDecoratorConfiguration(0, IndustrialCase.MAIN_CONFIG.treeDensityFactor, 1)
                        ))
        );
    }
}
