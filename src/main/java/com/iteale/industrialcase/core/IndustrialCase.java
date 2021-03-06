package com.iteale.industrialcase.core;

import com.iteale.industrialcase.api.energy.EnergyNet;
import com.iteale.industrialcase.api.energy.IEnergyNet;
import com.iteale.industrialcase.api.info.Info;
import com.iteale.industrialcase.api.item.ElectricItem;
import com.iteale.industrialcase.core.block.comp.Components;
import com.iteale.industrialcase.core.energy.grid.EnergyNetGlobal;
import com.iteale.industrialcase.core.init.ICConfig;
import com.iteale.industrialcase.core.item.ElectricItemManager;
import com.iteale.industrialcase.core.item.GatewayElectricItemManager;
import com.iteale.industrialcase.core.network.NetworkManager;
import com.iteale.industrialcase.core.network.NetworkManagerClient;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import com.iteale.industrialcase.core.registries.MenuTypeRegistry;
import com.iteale.industrialcase.core.util.ItemInfo;
import com.iteale.industrialcase.core.util.Log;
import com.iteale.industrialcase.core.util.PriorityExecutor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IndustrialCase.MODID)
public class IndustrialCase {
    public static final String MODID = "industrialcase";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Log log = new Log(LOGGER);

    private static IndustrialCase instance = null;

    public static Platform platform;
    public static NetworkManager network;
    public static final ICConfig MAIN_CONFIG = new ICConfig();
    public static final CreativeModeTab TAB_IC = new ICTab();
    public final PriorityExecutor threadPool;
    public static Random random = new Random();

    public IndustrialCase() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::load);

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

    public void load(FMLCommonSetupEvent event) {
        ElectricItem.manager = new GatewayElectricItemManager();
        ElectricItem.rawManager = new ElectricItemManager();
        Info.itemInfo = new ItemInfo();
        Components.init();
        EnergyNet.instance = EnergyNetGlobal.create();
        DistExecutor.safeRunWhenOn(Dist.CLIENT,
            (DistExecutor.SafeSupplier<DistExecutor.SafeRunnable>) () -> new DistExecutor.SafeRunnable() {
                @Override
                public void run() {
                    platform = new PlatformClient();
                    network = new NetworkManagerClient();
                }
            });

        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER,
            (DistExecutor.SafeSupplier<DistExecutor.SafeRunnable>) () -> new DistExecutor.SafeRunnable() {
                @Override
                public void run() {
                    platform = new Platform();
                    network = new NetworkManager();
                }
            });
    }

    public static ResourceLocation getIdentifier(String name) {
        return new ResourceLocation("industrialcase", name);
    }
}
