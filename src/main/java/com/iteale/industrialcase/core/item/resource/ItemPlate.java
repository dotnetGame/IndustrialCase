package com.iteale.industrialcase.core.item.resource;

import com.iteale.industrialcase.core.ICItemGroup;
import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class ItemPlate {
    public static Item create(String name) {
        return new Item(
                new Item.Properties().stacksTo(64).tab(IndustrialCase.TAB_IC)
        ).setRegistryName(name);
    }

    @SubscribeEvent
    public static void load(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(create("resource/plate/copper"));
        event.getRegistry().register(create("resource/plate/lead"));
        event.getRegistry().register(create("resource/plate/tin"));
    }
}
