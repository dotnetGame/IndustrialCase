package com.iteale.industrialcase.core.registries;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.item.ItemBattery;
import com.iteale.industrialcase.core.item.ItemTreetap;
import com.iteale.industrialcase.core.item.tool.ItemToolCutter;
import com.iteale.industrialcase.core.item.tool.ItemToolHammer;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialCase.MODID);

    public static RegistryObject<Item> FORGE_HAMMER = register("tool/forge_hammer", new ItemToolHammer());
    public static RegistryObject<Item> TREETAP = register("tool/treetap", new ItemTreetap());
    public static RegistryObject<Item> CUTTER = register("tool/cutter", new ItemToolCutter());

    public static RegistryObject<Item> RESIN = register("resource/resin");

    // ingot
    // 1.17.1 copper
    // public static Item COPPER_INGOT = register("resource/ingot/copper");
    public static RegistryObject<Item> LEAD_INGOT = register("resource/ingot/lead");
    public static RegistryObject<Item> TIN_INGOT = register("resource/ingot/tin");

    // plate
    public static RegistryObject<Item> IRON_PLATE = register("resource/plate/iron");
    public static RegistryObject<Item> GOLD_PLATE = register("resource/plate/gold");
    public static RegistryObject<Item> COPPER_PLATE = register("resource/plate/copper");
    public static RegistryObject<Item> LEAD_PLATE = register("resource/plate/lead");
    public static RegistryObject<Item> TIN_PLATE = register("resource/plate/tin");
    public static RegistryObject<Item> BRONZE_PLATE = register("resource/plate/bronze");

    // casing
    public static RegistryObject<Item> CASING_BRONZE = register("resource/casing/bronze");
    public static RegistryObject<Item> CASING_COPPER = register("resource/casing/copper");
    public static RegistryObject<Item> CASING_GOLD = register("resource/casing/gold");
    public static RegistryObject<Item> CASING_IRON = register("resource/casing/iron");
    public static RegistryObject<Item> CASING_LEAD = register("resource/casing/lead");
    public static RegistryObject<Item> CASING_STEEL = register("resource/casing/steel");
    public static RegistryObject<Item> CASING_TIN = register("resource/casing/tin");

    // crushed
    public static RegistryObject<Item> CRUSHED_COPPER = register("resource/crushed/copper");
    public static RegistryObject<Item> CRUSHED_GOLD = register("resource/crushed/gold");
    public static RegistryObject<Item> CRUSHED_IRON = register("resource/crushed/iron");
    public static RegistryObject<Item> CRUSHED_LEAD = register("resource/crushed/lead");
    public static RegistryObject<Item> CRUSHED_SILVER = register("resource/crushed/silver");
    public static RegistryObject<Item> CRUSHED_TIN = register("resource/crushed/tin");
    public static RegistryObject<Item> CRUSHED_URANIUM = register("resource/crushed/uranium");

    // dust
    public static RegistryObject<Item> BRONZE_DUST = register("resource/dust/bronze");
    public static RegistryObject<Item> CLAY_DUST = register("resource/dust/clay");
    public static RegistryObject<Item> COAL_DUST = register("resource/dust/coal");
    public static RegistryObject<Item> COPPER_DUST = register("resource/dust/copper");
    public static RegistryObject<Item> DIAMOND_DUST = register("resource/dust/diamond");
    public static RegistryObject<Item> EMERALD_DUST = register("resource/dust/emerald");
    public static RegistryObject<Item> GOLD_DUST = register("resource/dust/gold");
    public static RegistryObject<Item> LEAD_DUST = register("resource/dust/lead");
    public static RegistryObject<Item> LAPIS_DUST = register("resource/dust/lapis");
    public static RegistryObject<Item> TIN_DUST = register("resource/dust/tin");
    public static RegistryObject<Item> OBSIDIAN_DUST = register("resource/dust/obsidian");
    public static RegistryObject<Item> SILVER_DUST = register("resource/dust/silver");

    // crafting
    public static RegistryObject<Item> RUBBER = register("crafting/rubber");

    // battery
    public static RegistryObject<Item> RE_BATTERY = register("battery/re_battery", new ItemBattery(new Item.Properties(),10000.0D, 100.0D, 1));
    public static RegistryObject<Item> ADVANCED_RE_BATTERY = register("battery/advanced_re_battery", new ItemBattery(new Item.Properties(),100000.0D, 256.0D, 2));

    public static RegistryObject<Item> register(String name, Item item) {
        RegistryObject<Item> registry = ITEMS.register(name, () -> item);
        return registry;
    }

    public static RegistryObject<Item> register(String name) {
        Item item = new Item(new Item.Properties().tab(IndustrialCase.TAB_IC));
        return register(name, item);
    }
}
