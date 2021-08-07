package com.iteale.industrialcase.core.datagen.blockstate;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.registries.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ICBlockStateProvider extends BlockStateProvider {
    public ICBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }
    protected String name(Block block) {
        return block.getRegistryName().getPath();
    }
    protected String blockModelname(Block block) {
        return "block/" + block.getRegistryName().getPath();
    }
    protected String itemModelname(Block block) {
        return "item/" + block.getRegistryName().getPath();
    }
    protected ResourceLocation textureSideName(Block block) {
        return new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath() + "_sides");
    }
    protected ResourceLocation textureBottomName(Block block) {
        return new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath() + "_bottom");
    }
    protected ResourceLocation textureTopName(Block block) {
        return new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath() + "_top");
    }

    protected void registerSimpleBlock(Block block) {
        simpleBlock(
                block,
                new ModelFile.ExistingModelFile(
                        new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath()),
                        models().existingFileHelper
                )
        );
    }

    public void registerCross(Block block) {
        registerSimpleBlock(block);
        ModelFile blockModel = models().withExistingParent(
                        blockModelname(block),
                        new ResourceLocation("minecraft", "block/cross"))
                .texture("cross", blockModelname(block));
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerLeaves(Block block) {
        registerSimpleBlock(block);
        ModelFile blockModel = models().withExistingParent(
                    blockModelname(block),
                    new ResourceLocation("minecraft", "block/leaves"))
                .texture("all", blockModelname(block));
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerCubeAll(Block block) {
        registerCubeAll(block, blockTexture(block));
    }

    public void registerCubeAll(Block block, ResourceLocation texture) {
        registerSimpleBlock(block);
        ModelFile blockModel = models().cubeAll(blockModelname(block), texture);
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerCubeBottomTop(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        registerSimpleBlock(block);
        ModelFile blockModel = models().cubeBottomTop(blockModelname(block), side, bottom, top);
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerCubeBottomTop(Block block) {
        registerSimpleBlock(block);
        ModelFile blockModel = models().cubeBottomTop(blockModelname(block), textureSideName(block), textureBottomName(block), textureTopName(block));
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }
}
