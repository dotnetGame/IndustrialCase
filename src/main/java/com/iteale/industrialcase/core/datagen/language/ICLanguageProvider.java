package com.iteale.industrialcase.core.datagen.language;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class ICLanguageProvider extends LanguageProvider {
    public ICLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }
}
