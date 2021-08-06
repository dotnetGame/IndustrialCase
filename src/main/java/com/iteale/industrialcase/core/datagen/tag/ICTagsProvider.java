package com.iteale.industrialcase.core.datagen.tag;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public abstract class ICTagsProvider extends TagsProvider {
    protected ICTagsProvider(DataGenerator p_126546_, Registry p_126547_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126546_, p_126547_, modId, existingFileHelper);
    }
}
