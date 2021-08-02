package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.ICItemGroup;
import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class PlantsBlock {
    public static final  String BASE_PATH = "resource/plant/";

    public static final Block RUBBER_LOG = new RubberLog();
    public static final Item ITEM_RUBBER_LOG = new BlockItem(RUBBER_LOG, new Item.Properties().tab(ICItemGroup.TAB_IC)).setRegistryName(BASE_PATH + "rubber_log");

    public static final Block RUBBER_LEAVES = new RubberLeaves();
    public static final Item ITEM_RUBBER_LEAVES = new BlockItem(RUBBER_LEAVES, new Item.Properties().tab(ICItemGroup.TAB_IC)).setRegistryName(BASE_PATH + "rubber_leaves");

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(RUBBER_LOG);
        event.getRegistry().register(RUBBER_LEAVES);
    }

    @SubscribeEvent
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ITEM_RUBBER_LOG);
        event.getRegistry().register(ITEM_RUBBER_LEAVES);
    }
}
