package com.iteale.industrialcase.core.datagen.blockstate;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.resource.RubberLog;
import com.iteale.industrialcase.core.block.wiring.CableBase;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
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

    protected void registerSimpleBlockState(Block block) {
        simpleBlock(
                block,
                new ModelFile.ExistingModelFile(
                        new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath()),
                        models().existingFileHelper
                )
        );
    }

    protected void registerHorizontalFaceBlockState(Block block) {
        horizontalFaceBlock(block,
                new ModelFile.ExistingModelFile(
                        new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath()),
                        models().existingFileHelper
                )
        );
    }

    protected void registerHorizontalBlockState(Block block) {
        horizontalBlock(block,
                new ModelFile.ExistingModelFile(
                        new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath()),
                        models().existingFileHelper
                )
        );
    }

    protected void registerDirectionalBlockState(Block block) {
        ModelFile.ExistingModelFile modelFile = new ModelFile.ExistingModelFile(
                new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath()),
                models().existingFileHelper
        );
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    ConfiguredModel.Builder<?> builder = ConfiguredModel.builder()
                            .modelFile(modelFile);

                    if (dir == Direction.DOWN) builder.rotationX(90);
                    else if (dir == Direction.UP) builder.rotationX(-90);
                    else if (dir == Direction.SOUTH) builder.rotationX(180);
                    else if (dir == Direction.EAST) builder.rotationY(90);
                    else if (dir == Direction.WEST) builder.rotationY(-90);

                    return builder.build();
                });
    }

    protected void registerMachineBlockState(Block block) {
        ModelFile empty = models().getExistingFile(new ResourceLocation("block/air"));
        this.getVariantBuilder(block).forAllStates(s->{
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            ResourceLocation blockModel = new ResourceLocation(IndustrialCase.MODID, blockModelname(block));
            if (s.getValue(BlockStateProperties.FACING) == Direction.NORTH) {
                return builder.modelFile(
                        new ModelFile.ExistingModelFile(blockModel, models().existingFileHelper)
                ).build();
            } else if (s.getValue(BlockStateProperties.FACING) == Direction.SOUTH) {
                return builder.modelFile(
                        new ModelFile.ExistingModelFile(blockModel, models().existingFileHelper)
                ).rotationY(180).build();
            } else if (s.getValue(BlockStateProperties.FACING) == Direction.EAST) {
                return builder.modelFile(
                        new ModelFile.ExistingModelFile(blockModel, models().existingFileHelper)
                ).rotationY(90).build();
            } else if (s.getValue(BlockStateProperties.FACING) == Direction.WEST) {
                return builder.modelFile(
                        new ModelFile.ExistingModelFile(blockModel, models().existingFileHelper)
                ).rotationY(270).build();
            } else {
                return builder.modelFile(empty).build();
            }
        });
        horizontalBlock(block,
                new ModelFile.ExistingModelFile(
                        new ResourceLocation(IndustrialCase.MODID, "block/" + block.getRegistryName().getPath()),
                        models().existingFileHelper
                )
        );
    }

    public void registerCross(Block block) {
        registerSimpleBlockState(block);
        ModelFile blockModel = models().withExistingParent(
                        blockModelname(block),
                        new ResourceLocation("minecraft", "block/cross"))
                .texture("cross", blockModelname(block));
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerLeaves(Block block) {
        registerSimpleBlockState(block);
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
        registerSimpleBlockState(block);
        ModelFile blockModel = models().cubeAll(blockModelname(block), texture);
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerCubeBottomTop(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        registerSimpleBlockState(block);
        ModelFile blockModel = models().cubeBottomTop(blockModelname(block), side, bottom, top);
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerCubeBottomTop(Block block) {
        registerSimpleBlockState(block);
        ModelFile blockModel = models().cubeBottomTop(blockModelname(block), textureSideName(block), textureBottomName(block), textureTopName(block));
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerCube(Block block, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        registerSimpleBlockState(block);
        ModelFile blockModel = models().cube(blockModelname(block), down, up, north, south, east, west);
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerHorizontalFaceBlock(Block block, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        registerHorizontalFaceBlockState(block);
        ModelFile blockModel = models().cube(blockModelname(block), down, up, north, south, east, west).texture("particle", north);
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerHorizontalBlock(Block block, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        registerHorizontalBlockState(block);
        ModelFile blockModel = models().cube(blockModelname(block), down, up, north, south, east, west).texture("particle", north);
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerDirectionalBlock(Block block, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        registerDirectionalBlockState(block);
        ModelFile blockModel = models().cube(blockModelname(block), down, up, north, south, east, west).texture("particle", north);
        itemModels().withExistingParent(itemModelname(block), blockModel.getLocation());
    }

    public void registerMachine(Block block) {
        registerHorizontalBlock(
                block,
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_bottom"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_top"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_front"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_leftrightback"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_leftrightback"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_leftrightback")
        );
    }

    public void registerStorage(Block block) {
        registerDirectionalBlock(
                block,
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_bottom"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_top"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_front"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_back"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_leftright"),
                new ResourceLocation(IndustrialCase.MODID, blockModelname(block) + "_leftright")
        );
    }


    public void registerCable(Block block, float thickness) {
        ResourceLocation cableTexture = new ResourceLocation(IndustrialCase.MODID, blockModelname(block));

        float inner = 16 * (0.5F - thickness / 2);
        float outer = 16 * (0.5F + thickness / 2);
        ModelFile cableCoreModel = models().getBuilder(blockModelname(block) + "_core")
                .ao(true)
                .element().shade(true).from(inner, inner, inner).to(outer, outer, outer)
                .face(Direction.DOWN).texture("#line").end()
                .face(Direction.UP).texture("#line").end()
                .face(Direction.NORTH).texture("#line").end()
                .face(Direction.SOUTH).texture("#line").end()
                .face(Direction.EAST).texture("#line").end()
                .face(Direction.WEST).texture("#line").end()
                .end().texture("line", cableTexture).texture("particle", cableTexture);

        ModelFile cableSideModel = models().getBuilder(blockModelname(block) + "_side")
                .ao(true)
                .element().shade(true).from(inner, inner, 0).to(outer, outer, inner)
                .face(Direction.DOWN).texture("#line").end()
                .face(Direction.UP).texture("#line").end()
                .face(Direction.NORTH).texture("#line").end()
                .face(Direction.SOUTH).texture("#line").end()
                .face(Direction.EAST).texture("#line").end()
                .face(Direction.WEST).texture("#line").end()
                .end().texture("line", cableTexture).texture("particle", cableTexture);

        getMultipartBuilder(block)
                .part().modelFile(cableCoreModel).addModel().end()
                .part().modelFile(cableSideModel).rotationX(-90).uvLock(true).addModel()
                .condition(CableBase.UP, true).end()
                .part().modelFile(cableSideModel).rotationX(90).uvLock(true).addModel()
                .condition(CableBase.DOWN, true).end()
                .part().modelFile(cableSideModel).uvLock(true).addModel()
                .condition(CableBase.NORTH, true).end()
                .part().modelFile(cableSideModel).rotationY(180).uvLock(true).addModel()
                .condition(CableBase.SOUTH, true).end()
                .part().modelFile(cableSideModel).rotationY(90).uvLock(true).addModel()
                .condition(CableBase.EAST, true).end()
                .part().modelFile(cableSideModel).rotationY(270).uvLock(true).addModel()
                .condition(CableBase.WEST, true).end();


        itemModels().withExistingParent(itemModelname(block),
                new ResourceLocation("minecraft", "item/generated"))
                .texture("layer0", new ResourceLocation(IndustrialCase.MODID, itemModelname(block)));
    }
}
