package com.iteale.industrialcase.core;


import com.iteale.industrialcase.core.energy.grid.EnergyNetLocal;
import com.iteale.industrialcase.core.network.TeUpdateDataServer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class WorldData
{
    private WorldData(Level world) {
        if (!world.isClientSide) {
            this.energyNet = EnergyNetLocal.create(world);
            // this.tradeMarket = new TradingMarket(world);
            // this.windSim = new WindSim(world);
        } else {
            this.energyNet = null;
            // this.tradeMarket = null;
            // this.windSim = null;
        }
    }

    public static WorldData get(Level world) {
        return get(world, true);
    }

    public static WorldData get(Level world, boolean load) {
        if (world == null) throw new IllegalArgumentException("world is null");

        ConcurrentMap<String, WorldData> index = getIndex(!world.isClientSide);
        WorldData ret = index.get(world.dimension().location().toString());
        if (ret != null || !load) return ret;

        ret = new WorldData(world);

        WorldData prev = index.putIfAbsent(world.dimension().location().toString(), ret);
        if (prev != null) ret = prev;

        return ret;
    }

    public static void onWorldUnload(Level world) {
        getIndex(!world.isClientSide).remove(world.dimension().hashCode());
    }

    private static ConcurrentMap<String, WorldData> getIndex(boolean simulating) {
        return simulating ? idxServer : idxClient;
    }

    private static ConcurrentMap<String, WorldData> idxClient = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, WorldData> idxServer = new ConcurrentHashMap<>();

    final Queue<IWorldTickCallback> singleUpdates = new ConcurrentLinkedQueue<>();
    final Set<IWorldTickCallback> continuousUpdates = new HashSet<>();
    boolean continuousUpdatesInUse = false;
    final List<IWorldTickCallback> continuousUpdatesToAdd = new ArrayList<>();
    final List<IWorldTickCallback> continuousUpdatesToRemove = new ArrayList<>();


    public final EnergyNetLocal energyNet;


    public final Map<BlockEntity, TeUpdateDataServer> tesToUpdate = new IdentityHashMap<>();


    // public final TradingMarket tradeMarket;

    // public final WindSim windSim;

    public final Map<LevelChunk, CompoundTag> worldGenData = new IdentityHashMap<>();
    public final Set<LevelChunk> chunksToDecorate = Collections.newSetFromMap(new IdentityHashMap<>());
    public final Set<LevelChunk> pendingUnloadChunks = Collections.newSetFromMap(new IdentityHashMap<>());
}