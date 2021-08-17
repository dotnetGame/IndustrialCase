package com.iteale.industrialcase.core.registries;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.block.wiring.storage.menu.BatBoxMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MenuTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, IndustrialCase.MODID);
    public static final RegistryObject<MenuType<GeneratorMenu>> GENERATOR_MENU = register("generator/electric/generator", GeneratorMenu::new);
    public static final RegistryObject<MenuType<BatBoxMenu>> BATBOX_MENU = register("wiring/storage/batbox", BatBoxMenu::new);

    public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, MenuType.MenuSupplier<T> menuType) {
        RegistryObject<MenuType<T>> registry = MENUS.register(name, () -> {
            return new MenuType<>(menuType);
        });
        return registry;
    }
}
