package com.iteale.industrialcase.core.block;


import com.iteale.industrialcase.api.info.ITeBlock;
import com.iteale.industrialcase.api.network.INetworkDataProvider;
import com.iteale.industrialcase.api.network.INetworkUpdateListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BlockEntityBlock
        extends BlockEntity
        implements INetworkDataProvider, INetworkUpdateListener
{
    public static final String teBlockName = "teBlk";
    public static final String oldMarker = "Old-";
    protected static final int lightOpacityTranslucent = 0;
    protected static final int lightOpacityOpaque = 255;

    public static <T extends BlockEntityBlock> T instantiate(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public BlockEntityBlock(BlockEntityType<?> t, BlockPos pos, BlockState state) {
        super(t, pos, state);
        this.active = false;
        this.facing = (byte) Direction.DOWN.ordinal();

        this.loadState = 0;
    }

    private boolean active;
    private byte facing;
    private byte loadState;
    private boolean enableWorldTick;
}
