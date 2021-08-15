package com.iteale.industrialcase.core.block;


import com.iteale.industrialcase.api.info.ITeBlock;
import com.iteale.industrialcase.api.network.INetworkDataProvider;
import com.iteale.industrialcase.api.network.INetworkUpdateListener;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.comp.BlockEntityComponent;
import com.iteale.industrialcase.core.block.comp.Components;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import com.iteale.industrialcase.core.registries.BlockRegistry;
import com.iteale.industrialcase.core.util.LogCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;

import java.util.*;

public abstract class BlockEntityBase
        extends BaseContainerBlockEntity {
        // implements INetworkDataProvider, INetworkUpdateListener
    public static final String teBlockName = "teBlk";
    public static final String oldMarker = "Old-";
    protected static final int lightOpacityTranslucent = 0;
    protected static final int lightOpacityOpaque = 255;

    public static <T extends BlockEntityBase> T instantiate(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public BlockEntityBase(BlockEntityType<?> t, BlockPos pos, BlockState state) {
        super(t, pos, state);
        this.active = false;
        this.facing = (byte) Direction.DOWN.ordinal();

        this.loadState = 0;
    }

    public final Block getBlockType() {
        return super.getBlockState().getBlock();
    }

    /*
    public final BlockBehaviour.BlockStateBase getBlockState() {
        return this.getBlockType().defaultBlockState()
                .setValue(this.block.materialProperty, MaterialProperty.WrappedMaterial.get(this.teBlock.getMaterial()))
                .setValue(this.block.typeProperty, MetaTeBlockProperty.getState(this.teBlock, getActive()))
                .setValue(BlockTileEntity.facingProperty, (Comparable)getFacing())
                .setValue(BlockTileEntity.transparentProperty, Boolean.valueOf(this.teBlock.isTransparent()));
    }
     */


    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.active = nbt.getBoolean("active");
        if (this.components != null && nbt.contains("components", Tag.TAG_COMPOUND)) {
            CompoundTag componentsNbt = nbt.getCompound("components");
            for (String name : componentsNbt.getAllKeys()) {
                Class<? extends BlockEntityComponent> cls = Components.getClass(name);
                BlockEntityComponent component;
                if (cls == null || (component = getComponent((Class)cls)) == null) {
                    IndustrialCase.log.warn(LogCategory.Block, "Can't find component %s while loading %s.", name, this);
                    continue;
                }
                CompoundTag componentNbt = componentsNbt.getCompound(name);
                component.load(componentNbt);
            }
        }
    }

    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        nbt.putBoolean("active", this.active);
        if (this.components != null) {
            CompoundTag componentsNbt = null;
            for (BlockEntityComponent component : this.components.values()) {
                CompoundTag componentNbt = component.save();
                if (componentNbt == null)
                    continue;
                if (componentsNbt == null) {
                    componentsNbt = new CompoundTag();
                    nbt.put("components", componentsNbt);
                }
                componentsNbt.put(Components.getId(component.getClass()), componentNbt);
            }
        }
        return nbt;
    }

    protected final <T extends BlockEntityComponent> T addComponent(T component) {
        if (component == null)
            throw new NullPointerException("null component");
        if (this.components == null)
            this.components = new IdentityHashMap<>(4);
        BlockEntityComponent prev = this.components.put(component.getClass(), component);
        if (prev != null)
            throw new RuntimeException("conflicting component while adding " + component + ", already used by " + prev + ".");
        for (Capability<?> cap : component.getProvidedCapabilities(null))
            addComponentCapability(cap, component);
        return component;
    }

    public boolean hasComponent(Class<? extends BlockEntityComponent> cls) {
        if (this.components == null)
            return false;
        return this.components.containsKey(cls);
    }

    public <T extends BlockEntityComponent> T getComponent(Class<T> cls) {
        if (this.components == null)
            return null;
        return (T)this.components.get(cls);
    }

    public final Iterable<? extends BlockEntityComponent> getComponents() {
        if (this.components == null)
            return emptyComponents;
        return this.components.values();
    }

    private void addComponentCapability(Capability<?> cap, BlockEntityComponent component) {
        if (this.capabilityComponents == null)
            this.capabilityComponents = new IdentityHashMap<>();
        BlockEntityComponent prev = this.capabilityComponents.put(cap, component);
        assert prev == null;
    }

    private enum TickSubscription {
        None, Client, Server, Both;
    }

    // protected static final EnumPlantType noCrop = EnumPlantType.getPlantType("IC2_NO_CROP");
    private static final CompoundTag emptyNbt = new CompoundTag();
    private static final List<AABB> defaultAabbs = Arrays.asList(new AABB[] { new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) });
    private static final List<BlockEntityComponent> emptyComponents = Collections.emptyList();
    private static final Map<Class<?>, TickSubscription> tickSubscriptions = new HashMap<>();
    private static final byte loadStateInitial = 0;
    private static final byte loadStateQueued = 1;
    private static final byte loadStateLoaded = 2;
    private static final byte loadStateUnloaded = 3;
    private Map<Class<? extends BlockEntityComponent>, BlockEntityComponent> components;
    private Map<Capability<?>, BlockEntityComponent> capabilityComponents;
    private List<BlockEntityComponent> updatableComponents;
    private boolean active;
    private byte facing;
    private byte loadState;
    private boolean enableWorldTick;
}
