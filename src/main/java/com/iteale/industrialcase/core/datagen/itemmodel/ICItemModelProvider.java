package com.iteale.industrialcase.core.datagen.itemmodel;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ICItemModelProvider extends ItemModelProvider {
    public ICItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    protected void registerItemBlock(Block block) {
        String name = "item/" + block.getRegistryName().getPath();
        String parent = "block/" + block.getRegistryName().getPath();
        withExistingParent(name, new ResourceLocation(IndustrialCase.MODID, parent));
    }
}
