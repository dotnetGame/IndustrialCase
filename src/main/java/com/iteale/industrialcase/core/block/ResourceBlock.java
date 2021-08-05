package com.iteale.industrialcase.core.block;


import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class ResourceBlock extends OreBlock {
    public ResourceBlock(float hardness, float explosionResistance, boolean metal) {
        super(
                BlockBehaviour.Properties.of(Material.STONE)
                .strength(hardness, explosionResistance)
                .sound(metal? SoundType.METAL:SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
