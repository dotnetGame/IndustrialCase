package com.iteale.industrialcase.core.registries;

import com.iteale.industrialcase.core.IndustrialCase;
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

    public static RegistryObject<Item> RUBBER = register("crafting/rubber");

    public static RegistryObject<Item> register(String name, Item item) {
        RegistryObject<Item> registry = ITEMS.register(name, () -> item);
        return registry;
    }

    public static RegistryObject<Item> register(String name) {
        Item item = new Item(new Item.Properties().tab(IndustrialCase.TAB_IC));
        return register(name, item);
    }
}
