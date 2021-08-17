package com.iteale.industrialcase.core.block;


import com.iteale.industrialcase.core.util.IcColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;

public class BlockWall extends BlockBase
{
    public static final EnumProperty<IcColor> COLOR = EnumProperty.create("color", IcColor.class);
    private BlockWall() {
        super(Properties.of(Material.STONE)
                .strength(3.0F, 30.0F)
                .sound(SoundType.STONE));

        registerDefaultState(this.stateDefinition.any().setValue(COLOR, defaultColor));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOR);
    }

    public static final IcColor defaultColor = IcColor.light_gray;
}