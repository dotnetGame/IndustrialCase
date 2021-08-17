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
        add(BlockRegistry.COPPER_CABLE_INSULATED_BLUE.get(), "Blue Insulated Copper Cable");

        add(BlockRegistry.LEAD_ORE.get(), "Lead Ore");
        add(BlockRegistry.TIN_ORE.get(), "Tin Ore");
        add(BlockRegistry.SILVER_ORE.get(), "Silver Ore");

        add(BlockRegistry.LEAD_BLOCK.get(), "Lead Block");
        add(BlockRegistry.TIN_BLOCK.get(), "Tin Block");
        add(BlockRegistry.SILVER_BLOCK.get(), "Silver Block");

        add(BlockRegistry.RUBBER_PLANKS.get(), "Rubber Planks");
        add(BlockRegistry.RUBBER_LOG.get(), "Rubber Log");
        add(BlockRegistry.RUBBER_SAPLING.get(), "Rubber Sapling");
        add(BlockRegistry.RUBBER_LEAVES.get(), "Rubber Leaves");

        add(BlockRegistry.NUKE.get(), "Nuke");
        add(BlockRegistry.ITNT.get(), "Industrial TNT");

        // items
        add(ItemRegistry.IRON_PLATE.get(), "Iron Plate");
        add(ItemRegistry.GOLD_PLATE.get(), "Gold Plate");
        add(ItemRegistry.COPPER_PLATE.get(), "Copper Plate");
        add(ItemRegistry.TIN_PLATE.get(), "Tin Plate");
        add(ItemRegistry.LEAD_PLATE.get(), "Lead Plate");

        add(ItemRegistry.BRONZE_DUST.get(), "Bronze Dust");
        add(ItemRegistry.CLAY_DUST.get(), "Clay Dust");
        add(ItemRegistry.COAL_DUST.get(), "Coal Dust");
        add(ItemRegistry.COPPER_DUST.get(), "Copper Dust");
        add(ItemRegistry.DIAMOND_DUST.get(), "Diamond Dust");
        add(ItemRegistry.EMERALD_DUST.get(), "Emerald Dust");
        add(ItemRegistry.GOLD_DUST.get(), "Gold Dust");
        add(ItemRegistry.LEAD_DUST.get(), "Lead Dust");
        add(ItemRegistry.LAPIS_DUST.get(), "Lapis Dust");
        add(ItemRegistry.TIN_DUST.get(), "Tin Dust");
        add(ItemRegistry.OBSIDIAN_DUST.get(), "Obsidian Dust");
        add(ItemRegistry.SILVER_DUST.get(), "Silver Dust");

        add(ItemRegistry.CASING_BRONZE.get(), "Bronze Casing");
        add(ItemRegistry.CASING_COPPER.get(), "Copper Casing");
        add(ItemRegistry.CASING_GOLD.get(), "Gold Casing");
        add(ItemRegistry.CASING_IRON.get(), "Iron Casing");
        add(ItemRegistry.CASING_LEAD.get(), "Lead Casing");
        add(ItemRegistry.CASING_STEEL.get(), "Steel Casing");
        add(ItemRegistry.CASING_TIN.get(), "Tin Casing");

        add(ItemRegistry.CRUSHED_COPPER.get(), "Crushed Copper");
        add(ItemRegistry.CRUSHED_GOLD.get(), "Crushed Gold");
        add(ItemRegistry.CRUSHED_IRON.get(), "Crushed Iron");
        add(ItemRegistry.CRUSHED_LEAD.get(), "Crushed Lead");
        add(ItemRegistry.CRUSHED_SILVER.get(), "Crushed Silver");
        add(ItemRegistry.CRUSHED_TIN.get(), "Crushed Tin");
        add(ItemRegistry.CRUSHED_URANIUM.get(), "Crushed Uranium");

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
