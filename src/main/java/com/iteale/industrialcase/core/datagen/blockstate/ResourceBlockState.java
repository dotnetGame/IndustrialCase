package com.iteale.industrialcase.core.datagen.blockstate;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.RubberLog;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Random;
import java.util.function.Function;

public class ResourceBlockState extends ICBlockStateProvider {

    public ResourceBlockState(DataGenerator generatorIn, String modid, ExistingFileHelper exFileHelper) {
        super(generatorIn, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile empty = models().getExistingFile(new ResourceLocation("block/air"));

        registerLeaves(BlockRegistry.RUBBER_LEAVES.get());
        registerCubeAll(BlockRegistry.RUBBER_PLANKS.get());
        registerCross(BlockRegistry.RUBBER_SAPLING.get());

        registerCubeBottomTop(BlockRegistry.ITNT.get());
        registerCubeBottomTop(BlockRegistry.NUKE.get());

        getVariantBuilder(BlockRegistry.RUBBER_LOG.get())
                .forAllStates(s -> {
                    ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
                    ResourceLocation log_horizontal = new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_log_horizontal");
                    ResourceLocation log = new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_log");
                    ResourceLocation log_wet = new ResourceLocation(IndustrialCase.MODID, "block/resource/plant/rubber_log_wet");
                    if (s.getValue(RubberLog.AXIS) == Direction.Axis.X) {
                        return builder.modelFile(
                                new ModelFile.ExistingModelFile(log_horizontal, models().existingFileHelper)
                        ).rotationX(90).rotationY(90).build();
                    } else if (s.getValue(RubberLog.AXIS) == Direction.Axis.Z) {
                        return builder.modelFile(
                                new ModelFile.ExistingModelFile(log_horizontal, models().existingFileHelper)
                        ).rotationX(90).build();
                    } else if (s.getValue(RubberLog.AXIS) == Direction.Axis.Y) {
                        if (s.getValue(RubberLog.STATE) == RubberLog.RubberLogState.plain) {
                            return builder.modelFile(
                                    new ModelFile.ExistingModelFile(log, models().existingFileHelper)
                            ).build();
                        } else if (s.getValue(RubberLog.STATE) == RubberLog.RubberLogState.wet_north) {
                            return builder.modelFile(
                                    new ModelFile.ExistingModelFile(log_wet, models().existingFileHelper)
                            ).build();
                        } else if (s.getValue(RubberLog.STATE) == RubberLog.RubberLogState.wet_south) {
                            return builder.modelFile(
                                    new ModelFile.ExistingModelFile(log_wet, models().existingFileHelper)
                            ).rotationY(180).build();
                        } else if (s.getValue(RubberLog.STATE) == RubberLog.RubberLogState.wet_east) {
                            return builder.modelFile(
                                    new ModelFile.ExistingModelFile(log_wet, models().existingFileHelper)
                            ).rotationY(90).build();
                        } else if (s.getValue(RubberLog.STATE) == RubberLog.RubberLogState.wet_west) {
                            return builder.modelFile(
                                    new ModelFile.ExistingModelFile(log_wet, models().existingFileHelper)
                            ).rotationY(270).build();
                        } else {
                            return builder.modelFile(
                                    new ModelFile.ExistingModelFile(log, models().existingFileHelper)
                            ).build();
                        }
                    }
                    return builder.modelFile(empty).build();
                });

        registerCubeAll(BlockRegistry.LEAD_ORE.get());
        registerCubeAll(BlockRegistry.TIN_ORE.get());
        registerCubeAll(BlockRegistry.PLATINUM_ORE.get());
        registerCubeAll(BlockRegistry.SILVER_ORE.get());

        registerCubeAll(BlockRegistry.LEAD_BLOCK.get());
        registerCubeAll(BlockRegistry.TIN_BLOCK.get());
        registerCubeAll(BlockRegistry.SILVER_BLOCK.get());
        registerCubeAll(BlockRegistry.STEEL_BLOCK.get());

        registerCable(BlockRegistry.COPPER_CABLE.get(), 0.25F);
        registerCable(BlockRegistry.TIN_CABLE.get(), 0.25F);
        registerCable(BlockRegistry.IRON_CABLE.get(), 0.375F);
        registerCable(BlockRegistry.GLASS_CABLE.get(), 0.25F);
        registerCable(BlockRegistry.GOLD_CABLE.get(), 0.1875F);

        registerCable(BlockRegistry.COPPER_CABLE_INSULATED.get(), 0.375F);
        registerCable(BlockRegistry.COPPER_CABLE_INSULATED_BLUE.get(), 0.375F);
    }
}
