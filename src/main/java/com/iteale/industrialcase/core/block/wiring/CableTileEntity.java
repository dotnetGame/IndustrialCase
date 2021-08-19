package com.iteale.industrialcase.core.block.wiring;

import com.iteale.industrialcase.api.energy.EnergyNet;
import com.iteale.industrialcase.api.energy.event.EnergyTileLoadEvent;
import com.iteale.industrialcase.api.energy.event.EnergyTileUnloadEvent;
import com.iteale.industrialcase.api.energy.tile.*;
import com.iteale.industrialcase.core.block.TileEntityBlock;
import com.iteale.industrialcase.core.block.BlockWall;
import com.iteale.industrialcase.core.block.comp.Obscuration;
import com.iteale.industrialcase.core.registries.BlockEntityRegistry;
import com.iteale.industrialcase.core.util.IcColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;

public class CableTileEntity extends TileEntityBlock implements IEnergyConductor, IColoredEnergyTile
{
    public static final float insulationThickness = 0.0625F;

    public CableTileEntity(BlockPos pos, BlockState state, CableType cableType, int insulation) {
        this(pos, state);

        this.cableType = cableType;
        this.insulation = insulation;
    }

    public CableTileEntity(BlockPos pos, BlockState state, CableType cableType, int insulation, IcColor color) {
        this(pos, state, cableType, insulation);

        if (canBeColored(color)) {
            this.color = color;
        }
    }

    public CableTileEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CABLE.get(), pos, state);
        this.cableType = CableType.copper;

        this.color = IcColor.black;
        this.foam = CableFoam.None;
        this.foamColor = BlockWall.defaultColor;

        this.connectivity = 0;

        this.addedToEnergyNet = false;
        // this.continuousUpdate = null;
        this.obscuration = (Obscuration)addComponent(new Obscuration(this, new Runnable() {
            public void run() {
                // ((NetworkManager)IndustrialCase.network.get(true)).updateTileEntityField((BlockEntity)CableBlockEntity.this, "obscuration");
            }
        }));
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.cableType = CableType.values[nbt.getByte("cableType") & 0xFF];
        this.insulation = nbt.getByte("insulation") & 0xFF;
        this.color = IcColor.values[nbt.getByte("color") & 0xFF];
        this.foam = CableFoam.values[nbt.getByte("foam") & 0xFF];
        this.foamColor = IcColor.values[nbt.getByte("foamColor") & 0xFF];
    }

    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        nbt.putByte("cableType", (byte)this.cableType.ordinal());
        nbt.putByte("insulation", (byte)this.insulation);
        nbt.putByte("color", (byte)this.color.ordinal());
        nbt.putByte("foam", (byte)this.foam.ordinal());
        nbt.putByte("foamColor", (byte)this.foamColor.ordinal());
        return nbt;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if ((getLevel()).isClientSide) {
            // updateRenderState();
        } else {
            if (getClass() == CableTileEntity.class && (this.cableType == CableType.detector || this.cableType == CableType.splitter)) {
                /*
                IndustrialCase.log.debug(LogCategory.Block, "Fixing incorrect cable TE %s.", Util.toString(this));
                CableBlockEntity newTe = (this.cableType == CableType.detector) ? new TileEntityCableDetector() : new TileEntityCableSplitter();
                CompoundTag nbt = new CompoundTag();
                save(nbt);
                this.level.setBlockEntity(newTe);
                newTe.load(nbt);
                 */
                return;
            }
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
            updateConnectivity();
            // if (this.foam == CableFoam.Soft)
            //     changeFoam(this.foam, true);
        }
    }

    @Override
    protected void onUnloaded() {
        if (this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile)this));
            this.addedToEnergyNet = false;
        }
        /*
        if (this.continuousUpdate != null) {
            IC2.tickHandler.removeContinuousWorldTick(getWorld(), this.continuousUpdate);
            this.continuousUpdate = null;
        }
         */
        super.onUnloaded();
    }

    protected int getLightOpacity() {
        return (this.foam == CableFoam.Hardened) ? 255 : 0;
    }

    private boolean canBeColored(IcColor newColor) {
        switch (this.foam) {
            case None:
                return (this.color != newColor && this.cableType.minColoredInsulation <= this.insulation);
            default:
                return false;
            case Hardened:
                break;
        }
        return (this.color != newColor);
    }

    private void updateConnectivity() {
        Level world = getLevel();
        byte newConnectivity = 0;
        int mask = 1;
        for (Direction dir : Direction.values()) {
            IEnergyTile tile = EnergyNet.instance.getSubTile(world, this.worldPosition.relative(dir));
            if (((tile instanceof IEnergyAcceptor && ((IEnergyAcceptor)tile).acceptsEnergyFrom((IEnergyEmitter)this, dir.getOpposite())) || (tile instanceof IEnergyEmitter && ((IEnergyEmitter)tile).emitsEnergyTo((IEnergyAcceptor)this, dir.getOpposite()))) && canInteractWith(tile, dir))
                newConnectivity = (byte)(newConnectivity | mask);
            mask *= 2;
        }
        if (this.connectivity != newConnectivity) {
            this.connectivity = newConnectivity;
            // ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "connectivity");
        }
    }

    public boolean canInteractWith(IEnergyTile tile, Direction side) {
        if (tile instanceof IColoredEnergyTile) {
            IColoredEnergyTile other = (IColoredEnergyTile)tile;
            DyeColor thisColor = getColor(side);
            DyeColor otherColor = other.getColor(side.getOpposite());
            return (thisColor == null || otherColor == null || thisColor == otherColor);
        }
        return true;
    }

    @Override
    public double getConductionLoss() {
        return this.cableType.loss;
    }

    public void onNeighborChange(Block neighbor, BlockPos neighborPos) {
        // super.onNeighborChange(neighbor, neighborPos);
        if (!(getLevel()).isClientSide)
            updateConnectivity();
    }

    /*

    public void onPlaced(ItemStack stack, LivingEntity placer, Direction facing) {
        updateRenderState();
        super.onPlaced(stack, placer, facing);
    }

    protected ItemStack getPickBlock(Player player, InteractionResult target) {
        return ItemCable.getCable(this.cableType, this.insulation);
    }

    protected List<AABB> getAabbs(boolean forCollision) {
        if (this.foam == CableFoam.Hardened || (this.foam == CableFoam.Soft && !forCollision))
            return super.getAabbs(forCollision);
        float th = this.cableType.thickness + (this.insulation * 2) * 0.0625F;
        float sp = (1.0F - th) / 2.0F;
        List<AABB> ret = new ArrayList<>(7);
        ret.add(new AABB(sp, sp, sp, (sp + th), (sp + th), (sp + th)));
        for (Direction facing : Direction.values()) {
            boolean hasConnection = ((this.connectivity & 1 << facing.ordinal()) != 0);
            if (hasConnection) {
                float zS = sp, yS = zS, xS = yS;
                float zE = sp + th, yE = zE, xE = yE;
                switch (facing) {
                    case Soft:
                        yS = 0.0F;
                        yE = sp;
                        break;
                    case Hardened:
                        yS = sp + th;
                        yE = 1.0F;
                        break;
                    case None:
                        zS = 0.0F;
                        zE = sp;
                        break;
                    case null:
                        zS = sp + th;
                        zE = 1.0F;
                        break;
                    case null:
                        xS = 0.0F;
                        xE = sp;
                        break;
                    case null:
                        xS = sp + th;
                        xE = 1.0F;
                        break;
                    default:
                        throw new RuntimeException();
                }
                ret.add(new AABB(xS, yS, zS, xE, yE, zE));
            }
        }
        return ret;
    }

    protected boolean isNormalCube() {
        return (this.foam == CableFoam.Hardened || this.foam == CableFoam.Soft);
    }

    protected boolean isSideSolid(Direction side) {
        return (this.foam == CableFoam.Hardened);
    }

    protected boolean doesSideBlockRendering(Direction side) {
        return (this.foam == CableFoam.Hardened);
    }

    public Ic2BlockState.Ic2BlockStateInstance getExtendedState(Ic2BlockState.Ic2BlockStateInstance state) {
        state = super.getExtendedState(state);
        CableRenderState cableRenderState = this.renderState;
        if (cableRenderState != null)
            state = state.withProperties(new Object[] { renderStateProperty, cableRenderState });
        TileEntityWall.WallRenderState wallRenderState = this.wallRenderState;
        if (wallRenderState != null)
            state = state.withProperties(new Object[] { TileEntityWall.renderStateProperty, wallRenderState });
        return state;
    }

    protected boolean onActivated(EntityPlayer player, EnumHand hand, Direction side, float hitX, float hitY, float hitZ) {
        if (this.foam == CableFoam.Soft && StackUtil.consume(player, hand, StackUtil.sameItem((Block)Blocks.SAND), 1)) {
            changeFoam(CableFoam.Hardened, false);
            return true;
        }
        if (this.foam == CableFoam.None && StackUtil.consume(player, hand, StackUtil.sameStack(BlockName.foam.getItemStack((Enum)BlockFoam.FoamType.normal)), 1)) {
            foam();
            return true;
        }
        return super.onActivated(player, hand, side, hitX, hitY, hitZ);
    }

    protected void onClicked(EntityPlayer player) {
        super.onClicked(player);
        ItemToolCutter cutter = (ItemToolCutter)ItemName.cutter.getInstance();
        if (!cutter.removeInsulation(player, EnumHand.MAIN_HAND, this))
            cutter.removeInsulation(player, EnumHand.OFF_HAND, this);
    }

    protected float getHardness() {
        switch (this.foam) {
            case Soft:
                return BlockName.foam.getInstance().getBlockHardness(null, null, null);
            case Hardened:
                return BlockName.wall.getInstance().getBlockHardness(null, null, null);
        }
        return super.getHardness();
    }

    protected float getExplosionResistance(Entity exploder, Explosion explosion) {
        switch (this.foam) {
            case Hardened:
                return BlockName.wall.getInstance().getExplosionResistance(getWorld(), this.pos, exploder, explosion);
        }
        return super.getHardness();
    }

    protected boolean recolor(Direction side, DyeColor mcColor) {
        IcColor newColor = IcColor.get(mcColor);
        if (!canBeColored(newColor))
            return false;
        if (!(getWorld()).isRemote) {
            if (this.foam == CableFoam.None) {
                if (this.addedToEnergyNet)
                    MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
                this.addedToEnergyNet = false;
                this.color = newColor;
                MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
                this.addedToEnergyNet = true;
                ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "color");
                updateConnectivity();
            } else {
                this.foamColor = newColor;
                ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "foamColor");
                this.obscuration.clear();
            }
            markDirty();
        }
        return true;
    }

    protected boolean onRemovedByPlayer(EntityPlayer player, boolean willHarvest) {
        if (changeFoam(CableFoam.None, false))
            return false;
        return super.onRemovedByPlayer(player, willHarvest);
    }

    public boolean isFoamed() {
        return (this.foam != CableFoam.None);
    }

    public boolean foam() {
        return changeFoam(CableFoam.Soft, false);
    }

    public boolean tryAddInsulation() {
        if (this.insulation >= this.cableType.maxInsulation)
            return false;
        this.insulation++;
        if (!(getWorld()).isRemote)
            ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "insulation");
        return true;
    }

    public boolean wrenchCanRemove(EntityPlayer player) {
        return false;
    }

    public List<String> getNetworkedFields() {
        List<String> ret = new ArrayList<>();
        ret.add("cableType");
        ret.add("insulation");
        ret.add("color");
        ret.add("foam");
        ret.add("connectivity");
        ret.add("obscuration");
        ret.addAll(super.getNetworkedFields());
        return ret;
    }

    public void onNetworkUpdate(String field) {
        updateRenderState();
        if (field.equals("foam") && (this.foam == CableFoam.None || this.foam == CableFoam.Hardened))
            relight();
        rerender();
        super.onNetworkUpdate(field);
    }

    private void relight() {}

    public void onNetworkEvent(int event) {
        int l;
        World world = getWorld();
        switch (event) {
            case 0:
                world.playSound(null, this.pos, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                for (l = 0; l < 8; l++)
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX() + Math.random(), this.pos.getY() + 1.2D, this.pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
                return;
        }
        IC2.platform.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + event + ", tile entity below)\nT: " + this + " (" + this.pos + ")", new Object[0]);
    }

    private boolean changeFoam(CableFoam foam, boolean duringLoad) {
        if (this.foam == foam && !duringLoad)
            return false;
        World world = getWorld();
        if (world.isRemote)
            return true;
        this.foam = foam;
        if (this.continuousUpdate != null) {
            IC2.tickHandler.removeContinuousWorldTick(world, this.continuousUpdate);
            this.continuousUpdate = null;
        }
        if (foam != CableFoam.Hardened) {
            this.obscuration.clear();
            if (this.foamColor != BlockWall.defaultColor) {
                this.foamColor = BlockWall.defaultColor;
                if (!duringLoad)
                    ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "foamColor");
            }
        }
        if (foam == CableFoam.Soft) {
            this.continuousUpdate = new IWorldTickCallback() {
                public void onTick(World world) {
                    if (world.rand.nextFloat() < BlockFoam.getHardenChance(world, CableBlockEntity.this.pos, CableBlockEntity.this.getBlockType().getState((ITeBlock)TeBlock.cable), BlockFoam.FoamType.normal))
                        CableBlockEntity.this.changeFoam(CableFoam.Hardened, false);
                }
            };
            IC2.tickHandler.requestContinuousWorldTick(world, this.continuousUpdate);
        }
        if (!duringLoad) {
            ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "foam");
            world.notifyNeighborsOfStateChange(this.pos, (Block)getBlockType(), true);
            markDirty();
        }
        return true;
    }

    protected boolean clientNeedsExtraModelInfo() {
        return true;
    }

    public static class CableRenderState {
        public final CableType type;
        public final int insulation;
        public final IcColor color;
        public final CableFoam foam;
        public final int connectivity;
        public final boolean active;

        public CableRenderState(CableType type, int insulation, IcColor color, CableFoam foam, int connectivity, boolean active) {
            this.type = type;
            this.insulation = insulation;
            this.color = color;
            this.foam = foam;
            this.connectivity = connectivity;
            this.active = active;
        }

        public int hashCode() {
            int ret = this.type.hashCode();
            ret = ret * 31 + this.insulation;
            ret = ret * 31 + this.color.hashCode();
            ret = ret * 31 + this.foam.hashCode();
            ret = ret * 31 + this.connectivity;
            ret = ret << 1 | (this.active ? 1 : 0);
            return ret;
        }

        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            if (!(obj instanceof CableRenderState))
                return false;
            CableRenderState o = (CableRenderState)obj;
            return (o.type == this.type && o.insulation == this.insulation && o.color == this.color && o.foam == this.foam && o.connectivity == this.connectivity && o.active == this.active);
        }

        public String toString() {
            return "CableState<" + this.type + ", " + this.insulation + ", " + this.color + ", " + this.foam + ", " + this.connectivity + ", " + this.active + '>';
        }
    }
    */
    // public static final IUnlistedProperty<CableRenderState> renderStateProperty = (IUnlistedProperty<CableRenderState>)new UnlistedProperty("renderstate", CableRenderState.class);
    protected CableType cableType;
    protected int insulation;
    private IcColor color;
    private CableFoam foam;
    private IcColor foamColor;
    private final Obscuration obscuration;
    private byte connectivity;
    // private volatile CableRenderState renderState;
    // private volatile TileEntityWall.WallRenderState wallRenderState;
    public boolean addedToEnergyNet;
    // private IWorldTickCallback continuousUpdate;
    private static final int EventRemoveConductor = 0;

    public boolean tryRemoveInsulation(boolean simulate) {
        if (this.insulation <= 0)
            return false;
        if (simulate)
            return true;
        if (this.insulation == this.cableType.minColoredInsulation) {
            CableFoam foam = this.foam;
            this.foam = CableFoam.None;
            // recolor(getFacing(), DyeColor.BLACK);
            this.foam = foam;
        }
        this.insulation--;
        // if (!(getWorld()).isRemote)
        // ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "insulation");
        return true;
    }

    @Override
    public DyeColor getColor(Direction side) {
        return (this.color == IcColor.black) ? null : this.color.mcColor;
    }

    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter emitter, Direction direction) {
        return canInteractWith(emitter, direction);
    }

    @Override
    public double getInsulationEnergyAbsorption() {
        if (this.cableType.maxInsulation == 0)
            return 2.147483647E9D;
        if (this.cableType == CableType.tin)
            return EnergyNet.instance.getPowerFromTier(this.insulation);
        return EnergyNet.instance.getPowerFromTier(this.insulation + 1);
    }

    @Override
    public double getInsulationBreakdownEnergy() {
        return 9001.0D;
    }

    @Override
    public double getConductorBreakdownEnergy() {
        return (this.cableType.capacity + 1);
    }

    @Override
    public void removeInsulation() {
        tryRemoveInsulation(false);
    }

    @Override
    public void removeConductor() {
        getLevel().setBlock(this.worldPosition, Blocks.AIR.defaultBlockState(), 2);
        // ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 0, true);
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor receiver, Direction direction) {
        return canInteractWith((IEnergyTile)receiver, direction);
    }
}