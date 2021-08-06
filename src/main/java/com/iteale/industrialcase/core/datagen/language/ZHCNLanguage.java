package com.iteale.industrialcase.core.datagen.language;

import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.data.DataGenerator;

public class ZHCNLanguage extends ICLanguageProvider {
    public ZHCNLanguage(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.industrialcase", "工业");

        add(BlockRegistry.LEAD_BLOCK.get(), "铅方块");
    }
}
