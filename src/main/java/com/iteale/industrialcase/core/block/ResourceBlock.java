package com.iteale.industrialcase.core.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;

public class ResourceBlock extends Block{
    public static final Block COPPER_ORE = new ResourceBlock(3.0F, 5.0F, false).setRegistryName("copper_ore");
    public static final Block LEAD_ORE = new ResourceBlock(2.0F, 4.0F, false).setRegistryName("lead_ore");
    public static final Block TIN_ORE = new ResourceBlock(3.0F, 5.0F, false).setRegistryName("tin_ore");

    public static final Item ITEM_COPPER_ORE = new BlockItem(COPPER_ORE,new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("copper_ore");
    public static final Item ITEM_LEAD_ORE = new BlockItem(LEAD_ORE,new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("lead_ore");
    public static final Item ITEM_TIN_ORE = new BlockItem(TIN_ORE,new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("tin_ore");

    public ResourceBlock(float hardness, float explosionResistance, boolean metal) {
        super(
                AbstractBlock.Properties.of(Material.STONE)
                .strength(hardness, explosionResistance)
                .sound(metal?SoundType.METAL:SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
