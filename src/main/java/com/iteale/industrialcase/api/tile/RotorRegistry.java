package com.iteale.industrialcase.api.tile;

import com.iteale.industrialcase.api.info.Info;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.ModLoadingContext;

/**
 * Helper class for registering {@link IRotorProvider}s to provide the default windmill renderer
 */
@OnlyIn(Dist.CLIENT)
public class RotorRegistry {
	/**
	 * Method to register a tile entity with the default IC2 windmill TESH
	 * @param clazz {@link BlockEntity} that implements {@link IRotorProvider}
	 * @param <T> Type checking to ensure only {@link IRotorProvider} implementing classes are registered
	 */
	public static <T extends BlockEntity & IRotorProvider> void registerRotorProvider(Class<T> clazz) {
		if (INSTANCE != null) INSTANCE.registerRotorProvider(clazz);
	}

	/**
	 * Sets the internal Registry instance.
	 * ONLY IC2 CAN DO THIS!!!!!!!
	 */
	public static void setInstance(IRotorRegistry i) {
		ModContainer mc =ModLoadingContext.get().getActiveContainer();
		if (mc == null || !Info.MOD_ID.equals(mc.getModId())) {
			throw new IllegalAccessError("Only IC2 can set the instance");
		} else {
			INSTANCE = i;
		}
	}

	private static IRotorRegistry INSTANCE;

	public static interface IRotorRegistry {
		public <T extends BlockEntity & IRotorProvider> void registerRotorProvider(Class<T> clazz);
	}
}