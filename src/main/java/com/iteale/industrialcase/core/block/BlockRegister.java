package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.machine.IronFurnace;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.FoliageColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class BlockRegister {
    // Deferred register
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrialCase.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialCase.MODID);

    // plants
    public static final Block RUBBER_LOG = register("resource/plant/rubber_log", new RubberLog());
    public static final Block RUBBER_LEAVES = register("resource/plant/rubber_leaves", new RubberLeaves());
    public static final Block RUBBER_SAPLING = register("resource/plant/rubber_sapling", new RubberSapling());
    // ore
    public static final Block COPPER_ORE = register("resource/ore/copper_ore", new ResourceBlock(3.0F, 5.0F, false));
    public static final Block LEAD_ORE = register("resource/ore/lead_ore", new ResourceBlock(2.0F, 4.0F, false));
    public static final Block TIN_ORE = register("resource/ore/tin_ore", new ResourceBlock(3.0F, 5.0F, false));
    public static final Block PLATINUM_ORE = register("resource/ore/platinum_ore", new ResourceBlock(3.0F, 5.0F, false));
    public static final Block SILVER_ORE = register("resource/ore/silver_ore", new ResourceBlock(3.0F, 5.0F, false));

    // cable
    public static final Block COPPER_CABLE = register("wiring/cable/copper_cable", new CableBlock(0, 0.25F, 0.2D, 128));
    public static final Block GOLD_CABLE = register("wiring/cable/gold_cable", new CableBlock( 0, 0.1875F, 0.4D, 512));
    public static final Block IRON_CABLE = register("wiring/cable/iron_cable", new CableBlock( 0, 0.375F, 0.8D, 2048));
    public static final Block TIN_CABLE = register("wiring/cable/tin_cable", new CableBlock( 0, 0.25F, 0.2D, 32));
    public static final Block GLASS_CABLE = register("wiring/cable/glass_cable", new CableBlock( 0, 0.25F, 0.025D, 8192));

    public static final Block COPPER_CABLE_INSULATED = register("wiring/cable/copper_cable_insulated", new CableBlock(1, 0.375F, 0.2D, 128));
    public static final Block COPPER_CABLE_INSULATED_BLUE = register("wiring/cable/copper_cable_insulated_blue", new CableBlock(1, 0.375F, 0.2D, 128));

    // explosive
    public static final Block ITNT = register("explosive/itnt", new Block(AbstractBlock.Properties.of(Material.STONE)));
    public static final Block NUKE = register("explosive/nuke", new Block(AbstractBlock.Properties.of(Material.STONE)));

    // detector(0, 2147483647, 0.5F, 0.5D, 8192),
    // splitter(0, 2147483647, 0.5F, 0.5D, 8192);

    public static Block register(String name, Block block) {
        Item item = new BlockItem(
                block, new Item.Properties().tab(IndustrialCase.TAB_IC)
        );
        BLOCKS.register(name, () -> block);
        ITEMS.register(name, () -> item);
        return block;
    }

    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event){
        IBlockColor blockColorHandler = (blockState, blockDisplayReader, blockPos, i) -> {
            return FoliageColors.getEvergreenColor();
        };
        event.getBlockColors().register(blockColorHandler, RUBBER_LEAVES);
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event){
        IItemColor itemColorHandler = (itemStack, i) -> {
            return FoliageColors.getEvergreenColor();
        };
        event.getItemColors().register(itemColorHandler, RUBBER_LEAVES.asItem());
    }
}
