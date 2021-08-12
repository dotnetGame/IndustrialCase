package com.iteale.industrialcase.core.registries;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.generator.blockentity.GeneratorBlockEntity;
import com.iteale.industrialcase.core.block.machine.blockentity.IronFurnaceBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCase.MODID)
public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, IndustrialCase.MODID);

    // machine
    public static final RegistryObject<BlockEntityType<IronFurnaceBlockEntity>> IRON_FURNACE = register("machine/processing/basic/iron_furnace", IronFurnaceBlockEntity::new, BlockRegistry.IRON_FURNACE);
    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR = register("generator/electric/generator", GeneratorBlockEntity::new, BlockRegistry.GENERATOR);

    // public static final RegistryObject<BlockEntityType<FurnaceBlockEntity>> FURNACE = register("furnace", BlockEntityType.Builder.of(FurnaceBlockEntity::new, Blocks.FURNACE));
    // register methods
    public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<? extends T> supplier, RegistryObject<Block> block) {
        RegistryObject<BlockEntityType<T>> registry = BLOCK_ENTITY_TYPES.register(name, () -> {
            BlockEntityType.Builder<T> builder = BlockEntityType.Builder.of(supplier, block.get());
            return builder.build(null);
        });
        return registry;
    }
}
