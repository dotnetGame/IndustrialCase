package com.iteale.industrialcase.core.datagen.language;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.data.DataGenerator;

public class ENUSLanguage extends ICLanguageProvider {
    public ENUSLanguage(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.industrialcase", "Industrial Case");

        add(BlockRegistry.LEAD_BLOCK.get(), "Lead Block");
    }
}
