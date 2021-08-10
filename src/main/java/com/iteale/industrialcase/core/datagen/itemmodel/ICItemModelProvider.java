package com.iteale.industrialcase.core.datagen.itemmodel;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.item.ItemBattery;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ICItemModelProvider extends ItemModelProvider {
    public ICItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    protected String itemName(Item item) {
        return "item/" + item.getRegistryName().getPath();
    }

    protected void registerItem(Item item) {
        withExistingParent(itemName(item),
                new ResourceLocation("minecraft", "item/generated"))
                .texture("layer0", new ResourceLocation(IndustrialCase.MODID, itemName(item)));
    }

    protected void registerItemBlock(Block block) {
        String name = "item/" + block.getRegistryName().getPath();
        String parent = "block/" + block.getRegistryName().getPath();
        withExistingParent(name, new ResourceLocation(IndustrialCase.MODID, parent));
    }

    protected void registerBattery(Item item) {
        // build all item models
        for (int i = 0; i <= ItemBattery.maxLevel; ++i) {
            String subName = itemName(item) + "_" + Integer.toString(i);
            withExistingParent(subName,
                    new ResourceLocation("minecraft", "item/generated"))
                    .texture("layer0", new ResourceLocation(IndustrialCase.MODID, subName));
        }
        // build core model
        ItemModelBuilder builder = withExistingParent(itemName(item),
                new ResourceLocation("minecraft", "item/generated"))
                .texture("layer0", new ResourceLocation(IndustrialCase.MODID, itemName(item) + "_0"));

        for (int i = 0; i <= ItemBattery.maxLevel; ++i) {
            String subName = itemName(item) + "_" + Integer.toString(i);
            builder.override()
                    .predicate(new ResourceLocation(IndustrialCase.MODID, "level"), (float)i/ItemBattery.maxLevel)
                    .model(
                            new ModelFile.ExistingModelFile(
                                    new ResourceLocation(IndustrialCase.MODID, subName),
                                    existingFileHelper
                            )
                    ).end();
        }
    }
}
