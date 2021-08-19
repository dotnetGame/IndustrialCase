package com.iteale.industrialcase.core.block;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.comp.BlockEntityComponent;
import com.iteale.industrialcase.core.block.comp.Components;
import com.iteale.industrialcase.core.util.LogCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;

import java.util.*;

public abstract class TileEntityBlock extends BlockEntity {
        // implements INetworkDataProvider, INetworkUpdateListener
    public static final String teBlockName = "teBlk";
    public static final String oldMarker = "Old-";
    protected static final int lightOpacityTranslucent = 0;
    protected static final int lightOpacityOpaque = 255;

    public static <T extends TileEntityBlock> T instantiate(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public TileEntityBlock(BlockEntityType<?> t, BlockPos pos, BlockState state) {
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

    @Override
    public void clearRemoved() {
        super.clearRemoved();
        this.onLoaded();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        this.onUnloaded();
    }

    public final void validate() {
        // super.validate();
        Level world = getLevel();
        if (world == null || this.worldPosition == null)
            throw new IllegalStateException("no world/pos");
        if (this.loadState != 0 && this.loadState != 3)
            throw new IllegalStateException("invalid load state: " + this.loadState);
        this.loadState = 1;
    }

    protected void onLoaded() {
        this.validate();
        if (this.loadState != 1)
            throw new IllegalStateException("invalid load state: " + this.loadState);
        this.loadState = 2;
        this.enableWorldTick = requiresWorldTick();
        if (this.components != null)
            for (BlockEntityComponent component : this.components.values()) {
                component.onLoaded();
                if (component.enableWorldTick()) {
                    if (this.updatableComponents == null)
                        this.updatableComponents = new ArrayList<>(4);
                    this.updatableComponents.add(component);
                }
            }
        // if (!this.enableWorldTick && this.updatableComponents == null)
        // (getLevel()).tickableTileEntities.remove(this);
    }

    protected void onUnloaded() {
        if (this.loadState == 3)
            throw new IllegalStateException("invalid load state: " + this.loadState);
        this.loadState = 3;
        if (this.components != null)
            for (BlockEntityComponent component : this.components.values())
                component.onUnloaded();
    }

    public Direction getFacing() {
        return Direction.values()[this.facing];
    }

    protected Set<Direction> getSupportedFacings() {
        // return this.teBlock.getSupportedFacings();
        return new HashSet(Arrays.asList(Direction.values()));
    }

    protected void setFacing(Direction facing) {
        if (facing == null)
            throw new NullPointerException("null facing");
        if (this.facing == facing.ordinal())
            throw new IllegalArgumentException("unchanged facing");
        if (!getSupportedFacings().contains(facing))
            throw new IllegalArgumentException("invalid facing: " + facing + ", supported: " + getSupportedFacings());
        this.facing = (byte)facing.ordinal();
        // if (!(getActive()).isRemote)
        //     ((NetworkManager)IC2.network.get(true)).updateTileEntityField(this, "facing");
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        if (this.active == active)
            return;
        this.active = active;
        // ((NetworkManager)IC2.network.get(true)).updateTileEntityField(this, "active");
    }

    private final synchronized boolean requiresWorldTick() {
        Class<?> cls = getClass();
        TickSubscription subscription = tickSubscriptions.get(cls);
        if (subscription == null) {
            boolean hasUpdateClient = false;
            boolean hasUpdateServer = false;
            boolean isClient = this.level.isClientSide;
            while (cls != TileEntityBlock.class && ((!hasUpdateClient && isClient) || !hasUpdateServer)) {
                if (!hasUpdateClient && isClient) {
                    boolean found = true;
                    try {
                        cls.getDeclaredMethod("updateEntityClient", new Class[0]);
                    } catch (NoSuchMethodException e) {
                        found = false;
                    }
                    if (found)
                        hasUpdateClient = true;
                }
                if (!hasUpdateServer) {
                    boolean found = true;
                    try {
                        cls.getDeclaredMethod("updateEntityServer", new Class[0]);
                    } catch (NoSuchMethodException e) {
                        found = false;
                    }
                    if (found)
                        hasUpdateServer = true;
                }
                cls = cls.getSuperclass();
            }
            if (hasUpdateClient) {
                if (hasUpdateServer) {
                    subscription = TickSubscription.Both;
                } else {
                    subscription = TickSubscription.Client;
                }
            } else if (hasUpdateServer) {
                subscription = TickSubscription.Server;
            } else {
                subscription = TickSubscription.None;
            }
            tickSubscriptions.put(getClass(), subscription);
        }
        if (getLevel().isClientSide)
            return (subscription == TickSubscription.Both || subscription == TickSubscription.Client);
        return (subscription == TickSubscription.Both || subscription == TickSubscription.Server);
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

    public void onServerTick() {
        if (this.updatableComponents != null)
            for (BlockEntityComponent component : this.updatableComponents)
                component.onWorldTick();
    }

    private enum TickSubscription {
        None, Client, Server, Both;
    }

    // protected static final EnumPlantType noCrop = EnumPlantType.getPlantType("IC2_NO_CROP");
    private static final CompoundTag emptyNbt = new CompoundTag();
    private static final List<AABB> defaultAabbs = Arrays.asList(new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
    private static final List<BlockEntityComponent> emptyComponents = Collections.emptyList();
    private static final Map<Class<?>, TickSubscription> tickSubscriptions = new HashMap<>();
    private static final byte loadStateInitial = 0;
    private static final byte loadStateQueued = 1;
    private static final byte loadStateLoaded = 2;
    private static final byte loadStateUnloaded = 3;
    // protected final ITeBlock teBlock;
    private Map<Class<? extends BlockEntityComponent>, BlockEntityComponent> components;
    private Map<Capability<?>, BlockEntityComponent> capabilityComponents;
    private List<BlockEntityComponent> updatableComponents;
    private boolean active;
    private byte facing;
    private byte loadState;
    private boolean enableWorldTick;
}
