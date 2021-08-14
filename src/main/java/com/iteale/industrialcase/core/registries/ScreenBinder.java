package com.iteale.industrialcase.core.registries;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.generator.gui.GeneratorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class ScreenBinder {
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MenuTypeRegistry.GENERATOR_MENU.get(), GeneratorScreen::new);
        });
    }
}
