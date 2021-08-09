package com.iteale.industrialcase.core.energy;


import com.iteale.industrialcase.api.energy.EnergyNet;
import com.iteale.industrialcase.api.energy.NodeStats;
import com.iteale.industrialcase.api.energy.tile.*;
import com.iteale.industrialcase.core.energy.grid.NodeType;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class Tile
{
  final IEnergyTile mainTile;
  final List<IEnergyTile> subTiles;
  final List<Node> nodes;
  final double maxCurrent;
  
  Tile(EnergyNetLocal energyNet, IEnergyTile mainTile) {
    this.nodes = new ArrayList<>();
    this.mainTile = mainTile;
    if (mainTile instanceof IMetaDelegate) {
      this.subTiles = new ArrayList<>(((IMetaDelegate)mainTile).getSubTiles());
      if (this.subTiles.isEmpty())
        throw new RuntimeException("Tile " + mainTile + " must return at least 1 sub tile for IMetaDelegate.getSubTiles()."); 
    } else {
      this.subTiles = Arrays.asList(new IEnergyTile[] { mainTile });
    } 
    if (mainTile instanceof IEnergySource)
      this.nodes.add(new Node(energyNet, this, NodeType.Source));
    if (mainTile instanceof IEnergySink)
      this.nodes.add(new Node(energyNet, this, NodeType.Sink)); 
    if (mainTile instanceof IEnergyConductor) {
      this.nodes.add(new Node(energyNet, this, NodeType.Conductor));
      this.maxCurrent = ((IEnergyConductor)mainTile).getConductorBreakdownEnergy();
    } else {
      this.maxCurrent = Double.MAX_VALUE;
    } 
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
    if (canBeRemoved) {
      this.nodes.remove(node);
      return true;
    } 
    return false;
  }
  
  IEnergyTile getSubTileAt(BlockPos pos) {
    for (IEnergyTile subTile : this.subTiles) {
      if (EnergyNet.instance.getPos(subTile).equals(pos))
        return subTile; 
    } 
    return null;
  }
  
  Iterable<NodeStats> getStats() {
    List<NodeStats> ret = new ArrayList(this.nodes.size());
    for (Node node : this.nodes)
      ret.add(node.getStats()); 
    return ret;
  }
}
