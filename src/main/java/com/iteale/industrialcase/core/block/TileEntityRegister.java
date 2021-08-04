package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.machine.IronFurnace;
import com.iteale.industrialcase.core.block.machine.TileEntityIronFurnace;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class TileEntityRegister {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, IndustrialCase.MODID);

    // machine
    public static final TileEntityType IRON_FURNACE = register("machine/processing/basic/iron_furnace", new TileEntityIronFurnace(), new IronFurnace());
    // register methods
    public static TileEntityType register(String name, TileEntity tileEntity, Block block) {
        TileEntityType tileEntityType = TileEntityType.Builder.of(() -> {
                return tileEntity;
            }, block).build(null);
        TILE_ENTITY_TYPES.register(name, () -> {
            return tileEntityType;
        });
        return tileEntityType;
    }
}
