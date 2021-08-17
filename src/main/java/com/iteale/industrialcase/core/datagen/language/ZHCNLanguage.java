package com.iteale.industrialcase.core.datagen.language;

import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.data.DataGenerator;

public class ZHCNLanguage extends ICLanguageProvider {
    public ZHCNLanguage(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.industrialcase", "工业");

        // blocks
        add(BlockRegistry.GENERATOR.get(), "火力发电机");
        add(BlockRegistry.IRON_FURNACE.get(), "铁炉");

        add(BlockRegistry.COPPER_CABLE.get(), "铜质导线");
        add(BlockRegistry.TIN_CABLE.get(), "锡质导线");
        add(BlockRegistry.GOLD_CABLE.get(), "金质导线");
        add(BlockRegistry.GLASS_CABLE.get(), "玻璃纤维导线");
        add(BlockRegistry.IRON_CABLE.get(), "高压导线");

        add(BlockRegistry.COPPER_CABLE_INSULATED.get(), "绝缘铜质导线");

        add(BlockRegistry.LEAD_BLOCK.get(), "铅块");
        add(BlockRegistry.TIN_BLOCK.get(), "锡块");
        add(BlockRegistry.SILVER_BLOCK.get(), "银块");

        add(BlockRegistry.RUBBER_PLANKS.get(), "橡胶木板");
        add(BlockRegistry.RUBBER_LOG.get(), "橡胶木");
        add(BlockRegistry.RUBBER_SAPLING.get(), "橡胶树苗");
        add(BlockRegistry.RUBBER_LEAVES.get(), "橡胶树叶");

        // items
        add(ItemRegistry.IRON_PLATE.get(), "铁板");
        add(ItemRegistry.GOLD_PLATE.get(), "金板");
        add(ItemRegistry.COPPER_PLATE.get(), "铜板");
        add(ItemRegistry.TIN_PLATE.get(), "锡板");
        add(ItemRegistry.LEAD_PLATE.get(), "铅板");

        add(ItemRegistry.FORGE_HAMMER.get(), "锻造锤");
        add(ItemRegistry.CUTTER.get(), "板材切割剪刀");
        add(ItemRegistry.TREETAP.get(), "木龙头");

        add(ItemRegistry.RESIN.get(), "树脂");
        add(ItemRegistry.RUBBER.get(), "橡胶");

        add(ItemRegistry.RE_BATTERY.get(), "充电电池");

        // ui
        add("gui.generator.electric.generator", "火力发电机");
        add("gui.wiring.storage.batbox", "储电箱");
    }
}
