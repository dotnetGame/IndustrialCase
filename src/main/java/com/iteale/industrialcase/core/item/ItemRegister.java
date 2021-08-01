package com.iteale.industrialcase.core.item;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.ResourceBlock;
import com.iteale.industrialcase.core.item.tool.ItemToolHammer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class ItemRegister {
    public static final Item ITEM_TOOL_HAMMER = new ItemToolHammer();
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ITEM_TOOL_HAMMER);
    }
}
