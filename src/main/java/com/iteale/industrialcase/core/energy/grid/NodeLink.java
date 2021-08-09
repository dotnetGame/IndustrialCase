package com.iteale.industrialcase.core.energy.grid;
import com.iteale.industrialcase.api.energy.EnergyNet;
import com.iteale.industrialcase.api.energy.tile.IEnergyTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class NodeLink {
  Node nodeA;
  Node nodeB;
  
  NodeLink(Node nodeA, Node nodeB, double loss) {
    this(nodeA, nodeB, loss, null, null);
    
    calculateDirections();
  }
  Direction dirFromA;
  Direction dirFromB;
  double loss;
  NodeLink(NodeLink link) {
    this(link.nodeA, link.nodeB, link.loss, link.dirFromA, link.dirFromB);
    
    this.skippedNodes.addAll(link.skippedNodes);
  }
  
  private NodeLink(Node nodeA1, Node nodeB1, double loss1, Direction dirFromA, Direction dirFromB) {
    assert nodeA1 != nodeB1;
    
    this.nodeA = nodeA1;
    this.nodeB = nodeB1;
    this.loss = loss1;
    this.dirFromA = dirFromA;
    this.dirFromB = dirFromB;
  }
  
  public Node getNeighbor(Node node) {
    if (this.nodeA == node) {
      return this.nodeB;
    }
    
    return this.nodeA;
  }
  
  Node getNeighbor(int uid) {
    if (this.nodeA.uid == uid) {
      return this.nodeB;
    }
    
    return this.nodeA;
  }
  
  public double getLoss() {
    return this.loss;
  }
  
  void replaceNode(Node oldNode, Node newNode) {
    if (this.nodeA == oldNode) {
      this.nodeA = newNode;
    } else if (this.nodeB == oldNode) {
      this.nodeB = newNode;
    } else {
      throw new IllegalArgumentException("Node " + oldNode + " isn't in " + this + ".");
    } 
  }
  
  public Direction getDirFrom(Node node) {
    if (this.nodeA == node)
      return this.dirFromA; 
    if (this.nodeB == node) {
      return this.dirFromB;
    }
    return null;
  }


  
  public String toString() {
    return "NodeLink:" + this.nodeA + "@" + this.dirFromA + "->" + this.nodeB + "@" + this.dirFromB;
  }
  
  private void calculateDirections() {
    for (IEnergyTile posA : this.nodeA.tile.subTiles) {
      for (IEnergyTile posB : this.nodeB.tile.subTiles) {
        BlockPos delta = EnergyNet.instance.getPos(posA).subtract(EnergyNet.instance.getPos(posB));
        
        for (Direction dir : Direction.values()) {
          if (dir.getStepX() == delta.getX() && dir
            .getStepY() == delta.getY() && dir
            .getStepZ() == delta.getZ()) {
            this.dirFromA = dir;
            this.dirFromB = dir.getOpposite();
            
            return;
          } 
        } 
      } 
    } 
    
    assert false;
    this.dirFromA = null;
    this.dirFromB = null;
  }





  
  List<Node> skippedNodes = new ArrayList<>();
}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\core\energy\grid\NodeLink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */