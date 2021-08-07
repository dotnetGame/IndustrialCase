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
    }
}
