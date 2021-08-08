package com.iteale.industrialcase.core.registries;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID, value = Dist.CLIENT)
public class BlockColorRegistry {
    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event){
        BlockColor blockColorHandler = (blockState, blockDisplayReader, blockPos, i) -> {
            return FoliageColor.getEvergreenColor();
        };
        event.getBlockColors().register(blockColorHandler, BlockRegistry.RUBBER_LEAVES.get());
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event){
        ItemColor itemColorHandler = (itemStack, i) -> {
            return FoliageColor.getEvergreenColor();
        };
        event.getItemColors().register(itemColorHandler, BlockRegistry.RUBBER_LEAVES.get().asItem());
    }
}
