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
        add(BlockRegistry.COPPER_CABLE_INSULATED_BLUE.get(), "蓝色绝缘铜质导线");

        add(BlockRegistry.LEAD_ORE.get(), "铅矿");
        add(BlockRegistry.TIN_ORE.get(), "锡矿");
        add(BlockRegistry.SILVER_ORE.get(), "银矿");

        add(BlockRegistry.LEAD_BLOCK.get(), "铅块");
        add(BlockRegistry.TIN_BLOCK.get(), "锡块");
        add(BlockRegistry.SILVER_BLOCK.get(), "银块");

        add(BlockRegistry.RUBBER_PLANKS.get(), "橡胶木板");
        add(BlockRegistry.RUBBER_LOG.get(), "橡胶木");
        add(BlockRegistry.RUBBER_SAPLING.get(), "橡胶树苗");
        add(BlockRegistry.RUBBER_LEAVES.get(), "橡胶树叶");

        add(BlockRegistry.NUKE.get(), "核弹");
        add(BlockRegistry.ITNT.get(), "工业TNT");

        // items
        add(ItemRegistry.IRON_PLATE.get(), "铁板");
        add(ItemRegistry.GOLD_PLATE.get(), "金板");
        add(ItemRegistry.COPPER_PLATE.get(), "铜板");
        add(ItemRegistry.TIN_PLATE.get(), "锡板");
        add(ItemRegistry.LEAD_PLATE.get(), "铅板");

        
        add(ItemRegistry.BRONZE_DUST.get(), "青铜粉");
        add(ItemRegistry.CLAY_DUST.get(), "粘土粉");
        add(ItemRegistry.COAL_DUST.get(), "煤粉");
        add(ItemRegistry.COPPER_DUST.get(), "铜粉");
        add(ItemRegistry.DIAMOND_DUST.get(), "钻石粉");
        add(ItemRegistry.EMERALD_DUST.get(), "绿宝石粉");
        add(ItemRegistry.GOLD_DUST.get(), "金粉");
        add(ItemRegistry.LEAD_DUST.get(), "铅粉");
        add(ItemRegistry.LAPIS_DUST.get(), "青金石粉");
        add(ItemRegistry.TIN_DUST.get(), "锡粉");
        add(ItemRegistry.OBSIDIAN_DUST.get(), "黑曜石粉");
        add(ItemRegistry.SILVER_DUST.get(), "银粉");

        add(ItemRegistry.CASING_BRONZE.get(), "青铜外壳");
        add(ItemRegistry.CASING_COPPER.get(), "铜外壳");
        add(ItemRegistry.CASING_GOLD.get(), "金外壳");
        add(ItemRegistry.CASING_IRON.get(), "铁外壳");
        add(ItemRegistry.CASING_LEAD.get(), "铅外壳");
        add(ItemRegistry.CASING_STEEL.get(), "钢外壳");
        add(ItemRegistry.CASING_TIN.get(), "锡外壳");

        add(ItemRegistry.CRUSHED_COPPER.get(), "粉碎铜矿石");
        add(ItemRegistry.CRUSHED_GOLD.get(), "粉碎金矿石");
        add(ItemRegistry.CRUSHED_IRON.get(), "粉碎铁矿石");
        add(ItemRegistry.CRUSHED_LEAD.get(), "粉碎铅矿石");
        add(ItemRegistry.CRUSHED_SILVER.get(), "粉碎银矿石");
        add(ItemRegistry.CRUSHED_TIN.get(), "粉碎锡矿石");
        add(ItemRegistry.CRUSHED_URANIUM.get(), "粉碎铀矿石");

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
