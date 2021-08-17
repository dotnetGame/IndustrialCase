package com.iteale.industrialcase.core.registries;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.generator.Generator;
import com.iteale.industrialcase.core.block.machine.IronFurnace;
import com.iteale.industrialcase.core.block.resource.*;
import com.iteale.industrialcase.core.block.wiring.CableType;
import com.iteale.industrialcase.core.block.wiring.storage.BatBox;
import com.iteale.industrialcase.core.block.wiring.CableBlock;
import com.iteale.industrialcase.core.util.IcColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class BlockRegistry {
    // Deferred register
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrialCase.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialCase.MODID);

    // plants
    public static final RegistryObject<Block> RUBBER_LOG = register("resource/plant/rubber_log", new RubberLog());
    public static final RegistryObject<Block> RUBBER_LEAVES = register("resource/plant/rubber_leaves", new RubberLeaves());
    public static final RegistryObject<Block> RUBBER_SAPLING = register("resource/plant/rubber_sapling", new RubberSapling());
    public static final RegistryObject<Block> RUBBER_PLANKS = register("resource/plant/rubber_planks", new RubberPlanks());
    // ore
    // 1.17.1 copper
    // public static final RegistryObject<Block> COPPER_ORE = register("resource/ore/copper_ore", new ResourceBlock(3.0F, 5.0F, false));
    public static final RegistryObject<Block> LEAD_ORE = register("resource/ore/lead_ore", new ResourceBlock(2.0F, 4.0F, false));
    public static final RegistryObject<Block> TIN_ORE = register("resource/ore/tin_ore", new ResourceBlock(3.0F, 5.0F, false));
    public static final RegistryObject<Block> PLATINUM_ORE = register("resource/ore/platinum_ore", new ResourceBlock(3.0F, 5.0F, false));
    public static final RegistryObject<Block> SILVER_ORE = register("resource/ore/silver_ore", new ResourceBlock(3.0F, 5.0F, false));

    // block
    public static final RegistryObject<Block> LEAD_BLOCK = register("resource/block/lead_block", new ResourceBlock(2.0F, 4.0F, true));
    public static final RegistryObject<Block> TIN_BLOCK = register("resource/block/tin_block", new ResourceBlock(3.0F, 5.0F, true));
    public static final RegistryObject<Block> SILVER_BLOCK = register("resource/block/silver_block", new ResourceBlock(2.0F, 4.0F, true));
    public static final RegistryObject<Block> STEEL_BLOCK = register("resource/block/steel_block", new ResourceBlock(3.0F, 5.0F, true));

    // cable
    public static final RegistryObject<Block> COPPER_CABLE = register("wiring/cable/copper_cable", new CableBlock(CableType.copper, IcColor.black, 0));
    public static final RegistryObject<Block> GOLD_CABLE = register("wiring/cable/gold_cable", new CableBlock( CableType.gold, IcColor.black, 0));
    public static final RegistryObject<Block> IRON_CABLE = register("wiring/cable/iron_cable", new CableBlock(CableType.iron, IcColor.black, 0));
    public static final RegistryObject<Block> TIN_CABLE = register("wiring/cable/tin_cable", new CableBlock( CableType.tin, IcColor.black, 0));
    public static final RegistryObject<Block> GLASS_CABLE = register("wiring/cable/glass_cable", new CableBlock( CableType.glass, IcColor.black, 0));

    public static final RegistryObject<Block> COPPER_CABLE_INSULATED = register("wiring/cable/copper_cable_insulated", new CableBlock(CableType.copper, IcColor.black, 1));
    public static final RegistryObject<Block> COPPER_CABLE_INSULATED_BLUE = register("wiring/cable/copper_cable_insulated_blue", new CableBlock(CableType.copper, IcColor.blue, 1));

    // explosive
    public static final RegistryObject<Block> ITNT = register("explosive/itnt", new Block(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> NUKE = register("explosive/nuke", new Block(BlockBehaviour.Properties.of(Material.STONE)));

    // generator
    public static final RegistryObject<Block> GENERATOR = register("generator/electric/generator", new Generator());

    // machine
    public static final RegistryObject<Block> IRON_FURNACE = register("machine/processing/basic/iron_furnace", new IronFurnace());

    // storage
    public static final RegistryObject<Block> BATBOX = register("wiring/storage/batbox", new BatBox());


    // detector(0, 2147483647, 0.5F, 0.5D, 8192),
    // splitter(0, 2147483647, 0.5F, 0.5D, 8192);

    public static RegistryObject<Block> register(String name, Block block) {
        Item item = new BlockItem(
                block, new Item.Properties().tab(IndustrialCase.TAB_IC)
        );
        RegistryObject<Block> blockRegistry = BLOCKS.register(name, () -> block);
        RegistryObject<Item> itemBlockRegistry = ITEMS.register(name, () -> item);
        return blockRegistry;
    }

    public static RegistryObject<Block> register(String name, Block block, Item item) {
        RegistryObject<Block> blockRegistry = BLOCKS.register(name, () -> block);
        RegistryObject<Item> itemBlockRegistry = ITEMS.register(name, () -> item);
        return blockRegistry;
    }
}
