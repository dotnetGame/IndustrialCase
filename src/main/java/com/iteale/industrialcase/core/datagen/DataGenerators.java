package com.iteale.industrialcase.core.datagen;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.datagen.blockmodel.MachineBlockModel;
import com.iteale.industrialcase.core.datagen.blockmodel.WiringBlockModel;
import com.iteale.industrialcase.core.datagen.blockstate.MachineBlockState;
import com.iteale.industrialcase.core.datagen.blockstate.ResourceBlockState;
import com.iteale.industrialcase.core.datagen.blockmodel.ResourceBlockModel;
import com.iteale.industrialcase.core.datagen.blockstate.WiringBlockState;
import com.iteale.industrialcase.core.datagen.itemmodel.MachineItemModel;
import com.iteale.industrialcase.core.datagen.itemmodel.ResourceItemModel;
import com.iteale.industrialcase.core.datagen.itemmodel.WiringItemModel;
import com.iteale.industrialcase.core.datagen.language.ENUSLanguage;
import com.iteale.industrialcase.core.datagen.language.ZHCNLanguage;
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
            // translations
            generator.addProvider(new ENUSLanguage(generator, IndustrialCase.MODID, "en_us"));
            generator.addProvider(new ZHCNLanguage(generator, IndustrialCase.MODID, "zh_cn"));

            // block models
            generator.addProvider(new MachineBlockModel(generator, IndustrialCase.MODID, exfh));
            generator.addProvider(new ResourceBlockModel(generator, IndustrialCase.MODID, exfh));
            generator.addProvider(new WiringBlockModel(generator, IndustrialCase.MODID, exfh));

            // item models
            generator.addProvider(new MachineItemModel(generator, IndustrialCase.MODID, exfh));
            generator.addProvider(new ResourceItemModel(generator, IndustrialCase.MODID, exfh));
            generator.addProvider(new WiringItemModel(generator, IndustrialCase.MODID, exfh));

            // blockstates
            generator.addProvider(new MachineBlockState(generator, IndustrialCase.MODID, exfh));
            generator.addProvider(new ResourceBlockState(generator, IndustrialCase.MODID, exfh));
            generator.addProvider(new WiringBlockState(generator, IndustrialCase.MODID, exfh));
        }
    }
}
