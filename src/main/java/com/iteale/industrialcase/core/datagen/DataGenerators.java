package com.iteale.industrialcase.core.datagen;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.datagen.blockstate.ResourceBlockStates;
import com.iteale.industrialcase.core.datagen.model.ResourceBlockModel;
import com.iteale.industrialcase.core.datagen.recipe.OresRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper exfh = event.getExistingFileHelper();

        if (event.includeServer()) {
            generator.addProvider(new OresRecipes(generator));
            // generator.addProvider(new LootTables(generator));
        }

        if (event.includeClient()) {
            // models
            generator.addProvider(new ResourceBlockModel(generator, IndustrialCase.MODID, exfh));

            // blockstates
            generator.addProvider(new ResourceBlockStates(generator, IndustrialCase.MODID, exfh));
        }
    }
}
