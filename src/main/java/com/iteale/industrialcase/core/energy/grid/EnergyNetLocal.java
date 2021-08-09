package com.iteale.industrialcase.core.energy.grid;


import com.iteale.industrialcase.api.energy.EnergyNet;
import com.iteale.industrialcase.api.energy.NodeStats;
import com.iteale.industrialcase.api.energy.tile.IEnergyTile;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.util.LogCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class EnergyNetLocal
{
  public static EnergyNetLocal create(Level world) {
    return new EnergyNetLocal(world);
  }

  private EnergyNetLocal(Level world) {
    this.gridChangesQueue = new ArrayDeque();
    this.gridAdditionsMap = new IdentityHashMap();
    this.positionsToNotify = new HashSet();
    this.updater = new GridUpdater(this);

    this.registeredIoTiles = new IdentityHashMap();
    this.registeredTiles = new HashMap();
    this.sources = Collections.newSetFromMap(new IdentityHashMap<>());
    this.grids = new ArrayList<>();
    this.world = world;
    for (int i = 0; i < 1; i++)
      this.gridChangesQueue.add(QUEUE_DELAY_CHANGE); 
  }
  
  IEnergyTile getIoTile(BlockPos pos) {
    Tile tile = getTile(pos);
    if (tile != null)
      return tile.getMainTile(); 
    IEnergyTile ret = null;
    for (GridChange change : this.gridChangesQueue) {
      if (change == QUEUE_DELAY_CHANGE)
        continue; 
      if (change.pos.equals(pos))
        ret = (change.type == GridChange.Type.REMOVAL) ? null : change.ioTile; 
    } 
    return ret;
  }
  
  IEnergyTile getSubTile(BlockPos pos) {
    Tile tile = getTile(pos);
    if (tile != null)
      return tile.getSubTileAt(pos); 
    IEnergyTile ret = null;
    for (GridChange change : this.gridChangesQueue) {
      if (change == QUEUE_DELAY_CHANGE)
        continue; 
      Iterable<IEnergyTile> subTiles = (change.subTiles != null) ? change.subTiles : Collections.<IEnergyTile>singletonList(change.ioTile);
      for (IEnergyTile subtile : subTiles) {
        if (EnergyNet.instance.getPos(subtile).equals(pos)) {
          if (change.type == GridChange.Type.REMOVAL);
          ret = change.ioTile;
        } 
      } 
    } 
    return ret;
  }
  
  public Tile getTile(BlockPos pos) {
    if (this.updater.isInChangeStep())
      this.updater.awaitCompletion(); 
    return this.registeredTiles.get(pos);
  }
  
  void addTile(IEnergyTile ioTile, BlockPos pos) {
    GridChange change = new GridChange(GridChange.Type.ADDITION, pos, ioTile);
    GridChange prev;
    if ((prev = this.gridAdditionsMap.put(ioTile, change)) != null) {
      this.gridAdditionsMap.put(ioTile, prev);
      if (EnergyNetSettings.logGridUpdateIssues)
        IndustrialCase.log.warn(LogCategory.EnergyNet, "Tile %s was attempted to be queued twice for addition.", new Object[] { Util.toString(ioTile, (IBlockAccess)getWorld(), pos) }); 
    } else {
      this.gridChangesQueue.add(change);
    } 
  }
  
  void removeTile(IEnergyTile ioTile, BlockPos pos) {
    GridChange addition = this.gridAdditionsMap.remove(ioTile);
    if (addition != null) {
      if (EnergyNetSettings.logGridUpdatesVerbose)
        IndustrialCase.log.debug(LogCategory.EnergyNet, "Removing tile %s by cancelling a pending addition.", new Object[] { Util.toString(ioTile, (IBlockAccess)getWorld(), pos) });
      this.gridChangesQueue.remove(addition);
    } else {
      this.gridChangesQueue.add(new GridChange(GridChange.Type.REMOVAL, pos, ioTile));
      Tile tile = this.registeredIoTiles.get(ioTile);
      if (tile != null) {
        tile.setDisabled();
        if (EnergyNetSettings.logGridUpdatesVerbose)
          IndustrialCase.log.debug(LogCategory.EnergyNet, "Disabled tile %s.", new Object[] { Util.toString(ioTile, (IBlockAccess)getWorld(), pos) }); 
      } else if (EnergyNetSettings.logGridUpdatesVerbose) {
        IndustrialCase.log.warn(LogCategory.EnergyNet, "Missing tile %s.", new Object[] { Util.toString(ioTile, (IBlockAccess)getWorld(), pos) });
      } 
    } 
  }
  
  public Collection<Tile> getSources() {
    return this.sources;
  }
  
  NodeStats getNodeStats(IEnergyTile ioTile) {
    this.updater.awaitCompletion();
    Tile tile = this.registeredIoTiles.get(ioTile);
    if (tile == null)
      return null; 
    return EnergyNetGlobal.getCalculator().getNodeStats(tile);
  }
  
  public Collection<GridInfo> getGridInfos() {
    if (this.updater.isInChangeStep())
      this.updater.awaitCompletion(); 
    List<GridInfo> ret = new ArrayList<>();
    for (Grid grid : this.grids)
      ret.add(grid.getInfo()); 
    return ret;
  }
  
  boolean dumpDebugInfo(BlockPos pos, PrintStream console, PrintStream chat) {
    this.updater.awaitCompletion();
    Tile tile = this.registeredTiles.get(pos);
    if (tile == null)
      return false; 
    chat.println("Tile " + tile + " info:");
    chat.println(" disabled: " + tile.isDisabled());
    chat.println(" main: " + tile.getMainTile());
    chat.println(" sub: " + tile.subTiles);
    chat.println(" nodes: " + tile.nodes.size());
    Set<Grid> processedGrids = new HashSet<>();
    for (Node node : tile.nodes) {
      Grid grid = node.getGrid();
      if (processedGrids.add(grid)) {
        grid.dumpNodeInfo(node, " ", console, chat);
        grid.dumpInfo(" ", console, chat);
        grid.dumpGraph();
      } 
    } 
    return true;
  }
  
  void onTickStart() {
    if (this.updater.isInChangeStep()) {
      this.updater.awaitCompletion();
      if (!this.positionsToNotify.isEmpty()) {
        Block block = BlockName.te.getInstance();
        for (BlockPos pos : this.positionsToNotify) {
          if (!this.world.isBlockLoaded(pos))
            continue; 
          this.world.getBlockState(pos).neighborChanged(this.world, pos, block, pos);
        } 
        this.positionsToNotify.clear();
      } 
      this.updater.startTransferCalc();
    } 
  }
  
  void onTickEnd() {
    this.updater.awaitCompletion();
    if (!this.gridChangesQueue.isEmpty() && this.gridChangesQueue.peek() != QUEUE_DELAY_CHANGE) {
      this.updater.startChangeCalc(this.gridChangesQueue, this.gridAdditionsMap);
    } else {
      this.gridChangesQueue.poll();
      this.updater.startTransferCalc();
    } 
    this.gridChangesQueue.add(QUEUE_DELAY_CHANGE);
    assert this.gridChangesQueue.size() >= 1;
  }
  
  public Level getWorld() {
    return this.world;
  }
  
  int allocateNodeId() {
    return this.nextNodeId++;
  }
  
  int allocateGridId() {
    return this.nextGridId++;
  }
  
  void addPositionToNotify(BlockPos pos) {
    this.positionsToNotify.add(pos);
    for (Direction facing : Direction.values())
      this.positionsToNotify.add(pos.offset(facing)); 
  }
  
  boolean hasGrid(Grid grid) {
    return this.grids.contains(grid);
  }
  
  boolean hasGrids() {
    return !this.grids.isEmpty();
  }
  
  Collection<Grid> getGrids() {
    return this.grids;
  }
  
  void addGrid(Grid grid) {
    assert !hasGrid(grid);
    this.grids.add(grid);
  }
  
  void removeGrid(Grid grid) {
    boolean removed = this.grids.remove(grid);
    assert removed;
  }
  
  void shuffleGrids() {
    Collections.shuffle(this.grids);
  }
  
  static final GridChange QUEUE_DELAY_CHANGE = new GridChange(null, null, null);
  private final Level world;
  private final Queue<GridChange> gridChangesQueue;
  private final Map<IEnergyTile, GridChange> gridAdditionsMap;
  private final Set<BlockPos> positionsToNotify;
  private final GridUpdater updater;
  int nextNodeId;
  int nextGridId;
  final Map<IEnergyTile, Tile> registeredIoTiles;
  final Map<BlockPos, Tile> registeredTiles;
  final Set<Tile> sources;
  private final List<Grid> grids;
}