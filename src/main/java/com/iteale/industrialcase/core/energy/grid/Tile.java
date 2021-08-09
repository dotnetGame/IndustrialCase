package com.iteale.industrialcase.core.energy.grid;

import com.iteale.industrialcase.api.energy.EnergyNet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iteale.industrialcase.api.energy.tile.IEnergyConductor;
import com.iteale.industrialcase.api.energy.tile.IEnergySink;
import com.iteale.industrialcase.api.energy.tile.IEnergySource;
import com.iteale.industrialcase.api.energy.tile.IEnergyTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class Tile
{
  private final IEnergyTile mainTile;
  final List<IEnergyTile> subTiles;
  final List<Node> nodes;
  private boolean disabled;
  private double amount;
  private int packetCount;
  
  Tile(EnergyNetLocal enet, IEnergyTile mainTile, List<IEnergyTile> subTiles) {
    this.nodes = new ArrayList<>();
    this.mainTile = mainTile;
    this.subTiles = subTiles;
    if (mainTile instanceof IEnergySource)
      this.nodes.add(new Node(enet.allocateNodeId(), this, NodeType.Source)); 
    if (mainTile instanceof IEnergySink)
      this.nodes.add(new Node(enet.allocateNodeId(), this, NodeType.Sink)); 
    if (mainTile instanceof IEnergyConductor)
      this.nodes.add(new Node(enet.allocateNodeId(), this, NodeType.Conductor)); 
  }
  
  public IEnergyTile getMainTile() {
    return this.mainTile;
  }
  
  public Collection<Node> getNodes() {
    return this.nodes;
  }
  
  void addExtraNode(Node node) {
    node.setExtraNode(true);
    this.nodes.add(node);
  }
  
  boolean removeExtraNode(Node node) {
    boolean canBeRemoved = false;
    if (node.isExtraNode()) {
      canBeRemoved = true;
    } else {
      for (Node otherNode : this.nodes) {
        if (otherNode != node && otherNode.nodeType == node.nodeType && otherNode.isExtraNode()) {
          otherNode.setExtraNode(false);
          canBeRemoved = true;
          break;
        } 
      } 
    } 
    if (canBeRemoved)
      this.nodes.remove(node); 
    return canBeRemoved;
  }
  
  public Collection<IEnergyTile> getSubTiles() {
    return this.subTiles;
  }
  
  IEnergyTile getSubTileAt(BlockPos pos) {
    for (IEnergyTile subTile : this.subTiles) {
      if (EnergyNet.instance.getPos(subTile).equals(pos))
        return subTile; 
    } 
    return null;
  }
  
  void setDisabled() {
    this.disabled = true;
  }
  
  public boolean isDisabled() {
    return this.disabled;
  }
  
  public double getAmount() {
    return this.amount;
  }
  
  public void setAmount(double amount) {
    this.amount = amount;
  }
  
  public int getPacketCount() {
    return this.packetCount;
  }
  
  public void setSourceData(double amount, int packetCount) {
    this.amount = amount;
    this.packetCount = packetCount;
  }
  
  public String toString() {
    String ret = getTeClassName(this.mainTile);
    Level world = EnergyNet.instance.getWorld(this.mainTile);
    MinecraftServer server = world.getServer();
    if (server != null && server.isSameThread()) {
      BlockPos pos = EnergyNet.instance.getPos(this.mainTile);
      if (world.isLoaded(pos)) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te != null) {
          ret = ret + "|" + getTeClassName(te);
        } else {
          ret = ret + "|" + world.getBlockState(pos);
        } 
      } 
    } 
    return ret;
  }
  
  private static String getTeClassName(Object o) {
    return o.getClass().getSimpleName().replace("TileEntity", "");
  }
}
