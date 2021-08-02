package com.iteale.industrialcase.core.item;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class ItemRegister {
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    }
}
