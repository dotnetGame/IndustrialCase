package com.iteale.industrialcase.core.registries;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.item.ItemBattery;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID, value = Dist.CLIENT)
public class ItemPropertyRegistry {
    @SubscribeEvent
    public static void setup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            registerBattery(ItemRegistry.RE_BATTERY.get());
            registerBattery(ItemRegistry.ADVANCED_RE_BATTERY.get());
        });
    }

    protected static void registerBattery(Item item) {
        ItemProperties.register(item,
            new ResourceLocation(IndustrialCase.MODID, "level"),
            (stack, world, living, i) -> {
                if (living != null && living.isUsingItem() && living.getUseItem() == stack) {
                    int damage = stack.getDamageValue();
                    int maxDamage = stack.getMaxDamage() - 1;

                    int level = 0;
                    if (maxDamage > 0) {
                        level = Util.limit((damage * ItemBattery.maxLevel + maxDamage / 2) / maxDamage, 0, ItemBattery.maxLevel);
                    }
                    return (ItemBattery.maxLevel - level) / (float)ItemBattery.maxLevel;
                } else {
                    return 0.0F;
                }
            });
    }
}
