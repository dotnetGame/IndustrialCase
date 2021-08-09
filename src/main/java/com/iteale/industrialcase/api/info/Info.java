package com.iteale.industrialcase.api.info;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingStage;

public class Info {
	public static IInfoProvider itemInfo;
	public static Object ic2ModInstance;

	/**
	 * Damage Sources used by IC2.
	 * Getting assigned in preload.
	 */
	public static DamageSource DMG_ELECTRIC, DMG_NUKE_EXPLOSION, DMG_RADIATION;

	/**
	 * Potions used by IC2.
	 * Getting assigned in preload.
	 */
	public static Potion POTION_RADIATION;

	public static boolean isIc2Available() {
		if (ic2Available != null) return ic2Available;

		return ModList.get().isLoaded(IndustrialCase.MODID);
	}

	public static String MOD_ID = "industrialcase";
	private static Boolean ic2Available = null;
}