package com.iteale.industrialcase.core.datagen.model;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ResourceBlockModel extends BlockModelProvider {

    public ResourceBlockModel(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // block
        cubeAll("block/resource/block/lead_block", new ResourceLocation(IndustrialCase.MODID, "block/resource/block/lead_block"));
        cubeAll("block/resource/block/tin_block", new ResourceLocation(IndustrialCase.MODID, "block/resource/block/tin_block"));
        cubeAll("block/resource/block/silver_block", new ResourceLocation(IndustrialCase.MODID, "block/resource/block/silver_block"));
        cubeAll("block/resource/block/steel_block", new ResourceLocation(IndustrialCase.MODID, "block/resource/block/steel_block"));
        // ore
        cubeAll("block/resource/ore/lead_ore", new ResourceLocation(IndustrialCase.MODID, "block/resource/ore/lead_ore"));
        cubeAll("block/resource/ore/tin_ore", new ResourceLocation(IndustrialCase.MODID, "block/resource/ore/tin_ore"));

        // plants
        cubeAll("block/resource/plant/rubber_leaves", new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_leaves"));
        cubeAll("block/resource/plant/rubber_planks", new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_planks"));
        cubeColumn(
                "block/resource/plant/rubber_log",
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_log_sides"),
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_log_bottomtop")
        );

        cubeColumnHorizontal(
                "block/resource/plant/rubber_log_horizontal",
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_log_sides"),
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_log_bottomtop")
        );

        cube(
                "block/resource/plant/rubber_log_wet",
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_wood_wet_bottomtop"),
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_wood_wet_bottomtop"),
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_wood_wet_front"),
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_wood_wet_sides"),
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_wood_wet_sides"),
                new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_wood_wet_sides")
        ).texture("particle", "block/resource/plant/rubber_wood_wet_sides");

        cross("block/resource/plant/rubber_sapling", new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_sapling"));

        // explosive
        cubeBottomTop("block/explosive/itnt",
                new ResourceLocation(IndustrialCase.MODID, "block/explosive/itnt_sides"),
                new ResourceLocation(IndustrialCase.MODID, "block/explosive/itnt_bottom"),
                new ResourceLocation(IndustrialCase.MODID, "block/explosive/itnt_top")
                );
        cubeBottomTop("block/explosive/nuke",
                new ResourceLocation(IndustrialCase.MODID, "block/explosive/nuke_sides"),
                new ResourceLocation(IndustrialCase.MODID, "block/explosive/nuke_bottom"),
                new ResourceLocation(IndustrialCase.MODID, "block/explosive/nuke_top")
        );
    }
}
