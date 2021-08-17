package com.iteale.industrialcase.core.datagen.tag;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class GeneratorTags extends ICTagsProvider {
    protected GeneratorTags(DataGenerator p_126546_, Registry registry, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126546_, registry, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }

    @Override
    protected Path getPath(ResourceLocation p_126561_) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
