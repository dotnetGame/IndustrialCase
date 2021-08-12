package com.iteale.industrialcase.core;

import com.iteale.industrialcase.core.init.ICConfig;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import com.iteale.industrialcase.core.registries.MenuTypeRegistry;
import com.iteale.industrialcase.core.util.Log;
import com.iteale.industrialcase.core.util.PriorityExecutor;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IndustrialCase.MODID)
public class IndustrialCase
{
    public static final String MODID = "industrialcase";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Log log = new Log(LOGGER);

    private static IndustrialCase instance = null;

    public static final ICConfig MAIN_CONFIG = new ICConfig();
    public static final CreativeModeTab TAB_IC = new ICTab();
    public final PriorityExecutor threadPool;
    public static Random random = new Random();

    public IndustrialCase() {

        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockEntityRegistry.BLOCK_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MenuTypeRegistry.MENUS.register(FMLJavaModLoadingContext.get().getModEventBus());

        threadPool = new PriorityExecutor(Math.max(Runtime.getRuntime().availableProcessors(), 2));
        instance = this;
    }

    public static IndustrialCase getInstance() {
        return instance;
    }
}
