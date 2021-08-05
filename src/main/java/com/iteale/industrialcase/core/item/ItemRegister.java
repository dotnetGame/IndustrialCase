package com.iteale.industrialcase.core.item;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.item.tool.ItemToolHammer;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialCase.MODID);

    public static Item FORGE_HAMMER = register("tool/forge_hammer", new ItemToolHammer());
    public static Item TREETAP = register("tool/treetap", new ItemTreetap());
    public static Item CUTTER = register("tool/cutter");

    public static Item RESIN = register("resource/resin");

    public static Item COPPER_INGOT = register("resource/ingot/copper");
    public static Item LEAD_INGOT = register("resource/ingot/lead");
    public static Item TIN_INGOT = register("resource/ingot/tin");

    public static Item IRON_PLATE = register("resource/plate/iron");
    public static Item GOLD_PLATE = register("resource/plate/gold");
    public static Item COPPER_PLATE = register("resource/plate/copper");
    public static Item LEAD_PLATE = register("resource/plate/lead");
    public static Item TIN_PLATE = register("resource/plate/tin");
    public static Item BRONZE_PLATE = register("resource/plate/bronze");

    public static Item RUBBER = register("crafting/rubber");

    public static Item register(String name, Item item) {
        ITEMS.register(name, () -> item);
        return item;
    }

    public static Item register(String name) {
        Item item = new Item(new Item.Properties().tab(IndustrialCase.TAB_IC));
        return register(name, item);
    }
}
