package com.iteale.industrialcase.core.registries;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.resource.*;
import com.iteale.industrialcase.core.block.wiring.CableBlock;
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
    public static final RegistryObject<Block> COPPER_CABLE = register("wiring/cable/copper_cable", new CableBlock(0, 0.25F, 0.2D, 128));
    public static final RegistryObject<Block> GOLD_CABLE = register("wiring/cable/gold_cable", new CableBlock( 0, 0.1875F, 0.4D, 512));
    public static final RegistryObject<Block> IRON_CABLE = register("wiring/cable/iron_cable", new CableBlock( 0, 0.375F, 0.8D, 2048));
    public static final RegistryObject<Block> TIN_CABLE = register("wiring/cable/tin_cable", new CableBlock( 0, 0.25F, 0.2D, 32));
    public static final RegistryObject<Block> GLASS_CABLE = register("wiring/cable/glass_cable", new CableBlock( 0, 0.25F, 0.025D, 8192));

    public static final RegistryObject<Block> COPPER_CABLE_INSULATED = register("wiring/cable/copper_cable_insulated", new CableBlock(1, 0.375F, 0.2D, 128));
    public static final RegistryObject<Block> COPPER_CABLE_INSULATED_BLUE = register("wiring/cable/copper_cable_insulated_blue", new CableBlock(1, 0.375F, 0.2D, 128));

    // explosive
    public static final RegistryObject<Block> ITNT = register("explosive/itnt", new Block(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> NUKE = register("explosive/nuke", new Block(BlockBehaviour.Properties.of(Material.STONE)));

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
}
