package com.iteale.industrialcase.core.item;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.item.tool.ItemToolHammer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialCase.MODID);
    public static Item FORGE_HAMMER = register("tool/forge_hammer", new ItemToolHammer());
    public static Item TREETAP = register("tool/treetap", new ItemTreetap());

    public static Item register(String name, Item item) {
        ITEMS.register(name, () -> item);
        return item;
    }
}
