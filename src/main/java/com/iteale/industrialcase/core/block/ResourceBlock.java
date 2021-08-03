package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.ICItemGroup;
import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class ResourceBlock extends OreBlock {
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
