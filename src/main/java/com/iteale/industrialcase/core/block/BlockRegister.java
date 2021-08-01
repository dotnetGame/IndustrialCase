package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class BlockRegister {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(ResourceBlock.COPPER_ORE);
        event.getRegistry().register(ResourceBlock.LEAD_ORE);
        event.getRegistry().register(ResourceBlock.TIN_ORE);
        event.getRegistry().register(ResourceBlock.PLATINUM_ORE);
        event.getRegistry().register(ResourceBlock.SILVER_ORE);
    }

    @SubscribeEvent
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ResourceBlock.ITEM_COPPER_ORE);
        event.getRegistry().register(ResourceBlock.ITEM_LEAD_ORE);
        event.getRegistry().register(ResourceBlock.ITEM_TIN_ORE);
        event.getRegistry().register(ResourceBlock.ITEM_PLATINUM_ORE);
        event.getRegistry().register(ResourceBlock.ITEM_SILVER_ORE);
    }
}
