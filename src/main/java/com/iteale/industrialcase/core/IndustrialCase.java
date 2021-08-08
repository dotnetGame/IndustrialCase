package com.iteale.industrialcase.core;

import com.iteale.industrialcase.core.init.ICConfig;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.TileEntityRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IndustrialCase.MODID)
public class IndustrialCase
{
    public static final String MODID = "industrialcase";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final ICConfig MAIN_CONFIG = new ICConfig();
    public static final CreativeModeTab TAB_IC = new ICTab();

    public IndustrialCase() {
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityRegistry.BLOCK_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
