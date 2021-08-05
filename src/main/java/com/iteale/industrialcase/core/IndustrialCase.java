package com.iteale.industrialcase.core;

import com.iteale.industrialcase.core.block.BlockRegister;
import com.iteale.industrialcase.core.block.TileEntityRegister;
import com.iteale.industrialcase.core.item.ItemRegister;
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

    public static final CreativeModeTab TAB_IC = new ICTab();

    public IndustrialCase() {
        BlockRegister.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegister.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityRegister.BLOCK_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegister.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
