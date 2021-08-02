package com.iteale.industrialcase.core.item.resource;

import com.iteale.industrialcase.core.ICItemGroup;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.ref.ItemName;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class ItemIngot {
    public static Item create(String name) {
        return new Item(
                new Item.Properties().stacksTo(64).tab(ICItemGroup.TAB_IC)
        ).setRegistryName(name);
    }

    @SubscribeEvent
    public static void load(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(create("resource/ingot/copper"));
        event.getRegistry().register(create("resource/ingot/lead"));
        event.getRegistry().register(create("resource/ingot/tin"));
    }
}
