package com.iteale.industrialcase.core.energy.grid;


import com.iteale.industrialcase.api.energy.tile.IEnergyTile;
import net.minecraft.core.BlockPos;

import java.util.List;

class GridChange {
  final Type type;
  final BlockPos pos;
  
  GridChange(Type type, BlockPos pos, IEnergyTile ioTile) {
    this.type = type;
    this.pos = pos;
    this.ioTile = ioTile;
  }
  final IEnergyTile ioTile;
  List<IEnergyTile> subTiles;
  
  enum Type { ADDITION, REMOVAL; }

}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\core\energy\grid\GridChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */