package com.iteale.industrialcase.core.datagen.language;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.data.DataGenerator;

public class ENUSLanguage extends ICLanguageProvider {
    public ENUSLanguage(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.industrialcase", "Industrial Case");

        // blocks
        add(BlockRegistry.GENERATOR.get(), "Generator");
        add(BlockRegistry.IRON_FURNACE.get(), "Iron Furnace");

        add(BlockRegistry.COPPER_CABLE.get(), "Copper Cable");
        add(BlockRegistry.TIN_CABLE.get(), "Tin Cable");
        add(BlockRegistry.GOLD_CABLE.get(), "Gold Cable");
        add(BlockRegistry.GLASS_CABLE.get(), "Glass Fibre Cable");
        add(BlockRegistry.IRON_CABLE.get(), "HV Cable");

        add(BlockRegistry.COPPER_CABLE_INSULATED.get(), "Insulated Copper Cable");

        add(BlockRegistry.LEAD_BLOCK.get(), "Lead Block");
        add(BlockRegistry.TIN_BLOCK.get(), "Tin Block");
        add(BlockRegistry.SILVER_BLOCK.get(), "Silver Block");

        add(BlockRegistry.RUBBER_PLANKS.get(), "Rubber Planks");
        add(BlockRegistry.RUBBER_LOG.get(), "Rubber Log");
        add(BlockRegistry.RUBBER_SAPLING.get(), "Rubber Sapling");
        add(BlockRegistry.RUBBER_LEAVES.get(), "Rubber Leaves");

        // items
        add(ItemRegistry.IRON_PLATE.get(), "Iron Plate");
        add(ItemRegistry.GOLD_PLATE.get(), "Gold Plate");
        add(ItemRegistry.COPPER_PLATE.get(), "Copper Plate");
        add(ItemRegistry.TIN_PLATE.get(), "Tin Plate");
        add(ItemRegistry.LEAD_PLATE.get(), "Lead Plate");

        add(ItemRegistry.FORGE_HAMMER.get(), "Forge Hammer");
        add(ItemRegistry.CUTTER.get(), "Cutter");
        add(ItemRegistry.TREETAP.get(), "Treetap");

        add(ItemRegistry.RESIN.get(), "Resin");
        add(ItemRegistry.RUBBER.get(), "Rubber");

        add(ItemRegistry.RE_BATTERY.get(), "RE Battery");

        // ui
        add("gui.generator.electric.generator", "Generator");
        add("gui.wiring.storage.batbox", "BatBox");

    }
}
