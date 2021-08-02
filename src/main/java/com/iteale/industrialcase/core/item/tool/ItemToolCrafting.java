package com.iteale.industrialcase.core.item.tool;

import com.iteale.industrialcase.core.ICItemGroup;
import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class ItemToolCrafting {
    public static Item FORGE_HAMMER = new Item(
            new Item.Properties()
                    .durability(80 - 1)
                    .setNoRepair()
                    .tab(ICItemGroup.TAB_IC)
    ).setRegistryName("tool/forge_hammer");

    @SubscribeEvent
    public static void load(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(FORGE_HAMMER);
    }
}
