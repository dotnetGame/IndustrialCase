package com.iteale.industrialcase.core.datagen.blockmodel;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ResourceBlockModel extends ICBlockModelProvider {

    public ResourceBlockModel(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // block
        registerSimple(BlockRegistry.LEAD_BLOCK.get());
        registerSimple(BlockRegistry.TIN_BLOCK.get());
        registerSimple(BlockRegistry.SILVER_BLOCK.get());
        registerSimple(BlockRegistry.STEEL_BLOCK.get());

        // ore
        registerSimple(BlockRegistry.LEAD_ORE.get());
        registerSimple(BlockRegistry.TIN_ORE.get());

        // plants
        registerSimple(BlockRegistry.RUBBER_LEAVES.get());
        registerSimple(BlockRegistry.RUBBER_PLANKS.get());
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
