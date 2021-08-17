package com.iteale.industrialcase.core.util;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fluids.IFluidBlock;

public class PumpUtil
{
  private static int moveUp(Level world, BlockPos.MutableBlockPos pos) {
    pos.set(pos.getX(), pos.getY() + 1, pos.getZ());
    
    int newDecay = getFlowDecay(world, (BlockPos)pos);
    if (newDecay >= 0) return newDecay;
    
    pos.set(pos.getX() + 1, pos.getY(), pos.getZ());
    newDecay = getFlowDecay(world, (BlockPos)pos);
    if (newDecay >= 0) return newDecay;
    
    pos.set(pos.getX() - 2, pos.getY(), pos.getZ());
    newDecay = getFlowDecay(world, (BlockPos)pos);
    if (newDecay >= 0) return newDecay;
    
    pos.set(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
    newDecay = getFlowDecay(world, (BlockPos)pos);
    if (newDecay >= 0) return newDecay;
    
    pos.set(pos.getX(), pos.getY(), pos.getZ() - 2);
    newDecay = getFlowDecay(world, (BlockPos)pos);
    if (newDecay >= 0) return newDecay;
    
    pos.set(pos.getX(), pos.getY() - 1, pos.getZ() + 1);
    
    return -1;
  }
  
  private static int moveSideways(Level world, BlockPos.MutableBlockPos pos, int decay) {
    pos.set(pos.getX() - 1, pos.getY(), pos.getZ());
    int newDecay = getFlowDecay(world, (BlockPos)pos);
    if (newDecay >= 0 && newDecay < decay) return newDecay;
    
    pos.set(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
    newDecay = getFlowDecay(world, (BlockPos)pos);
    if (newDecay >= 0 && newDecay < decay) return newDecay;
    
    pos.set(pos.getX(), pos.getY(), pos.getZ() - 2);
    newDecay = getFlowDecay(world, (BlockPos)pos);
    if (newDecay >= 0 && newDecay < decay) return newDecay;
    
    pos.set(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
    newDecay = getFlowDecay(world, (BlockPos)pos);
    if (newDecay >= 0 && newDecay < decay) return newDecay;
    
    pos.set(pos.getX() - 1, pos.getY(), pos.getZ());
    
    return -1;
  }
  
  public static BlockPos searchFluidSource(Level world, BlockPos startPos) {
    BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
    pos.set(startPos.getX(), startPos.getY(), startPos.getZ());
    
    int decay = getFlowDecay(world, (BlockPos)pos);
    
    for (int i = 0; i < 64; i++) {
      
      int newDecay = moveUp(world, pos);
      
      if (newDecay < 0) {
        
        newDecay = moveSideways(world, pos, decay);
        
        if (newDecay < 0)
          break; 
      } 
      decay = newDecay;
    } 
    
    Set<BlockPos> visited = new HashSet<>(64);

    
    for (int j = 0; j < 64; j++) {
      visited.add(new BlockPos(pos));
      
      pos.set(pos.getX() - 1, pos.getY(), pos.getZ());
      
      if (!visited.contains(pos)) {
        int newDecay = getFlowDecay(world, (BlockPos)pos);
        
        if (newDecay >= 0) {
          if (newDecay == 0) return (BlockPos)pos;
          
          continue;
        } 
      } 
      pos.set(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
      
      if (!visited.contains(pos)) {
        int newDecay = getFlowDecay(world, (BlockPos)pos);
        
        if (newDecay >= 0) {
          if (newDecay == 0) return (BlockPos)pos;
          
          continue;
        } 
      } 
      pos.set(pos.getX(), pos.getY(), pos.getZ() - 2);
      
      if (!visited.contains(pos)) {
        int newDecay = getFlowDecay(world, (BlockPos)pos);
        
        if (newDecay >= 0) {
          if (newDecay == 0) return (BlockPos)pos;
          
          continue;
        } 
      } 
      pos.set(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
      
      if (!visited.contains(pos)) {
        int newDecay = getFlowDecay(world, (BlockPos)pos);
        
        if (newDecay >= 0) {
          if (newDecay == 0) return (BlockPos)pos;
          
          continue;
        } 
      } 
      pos.set(pos.getX() - 1, pos.getY(), pos.getZ());
    } 

    
    BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
    
    for (int ix = -2; ix <= 2; ix++) {
      for (int iz = -2; iz <= 2; iz++) {
        cPos.set(pos.getX() + ix, pos.getY(), pos.getZ() + iz);
        
        BlockBehaviour.BlockStateBase state = world.getBlockState((BlockPos)cPos);
        decay = getFlowDecay(state, world, (BlockPos)cPos);
        
        if (decay >= 0) {
          
          if (decay == 0) {
            return (BlockPos)cPos;
          }

          
          if (decay >= 1 && decay < 7 && state.getBlock() instanceof LiquidBlock) {
            world.setBlock(cPos, state.setValue(LiquidBlock.LEVEL, Integer.valueOf(decay + 1)), 2);
          } else {
            world.setBlock(cPos, Blocks.AIR.defaultBlockState(), 2);
          } 
        } 
      } 
    } 

    
    return null;
  }
  
  protected static int getFlowDecay(Level world, BlockPos pos) {
    BlockBehaviour.BlockStateBase state = world.getBlockState(pos);
    
    return getFlowDecay(state, world, pos);
  }
  
  protected static int getFlowDecay(BlockBehaviour.BlockStateBase state, Level world, BlockPos pos) {
    Block block = state.getBlock();
    
    if (block instanceof IFluidBlock) {
      IFluidBlock fb = (IFluidBlock)block;
      
      if (fb.canDrain(world, pos)) {
        return 0;
      }
      float level = Math.abs(fb.getFilledPercentage(world, pos));
      
      return 7 - Util.limit(Math.round(6.0F * level), 0, 6);
    } 
    if (block instanceof LiquidBlock) {
      return ((Integer)state.getValue(LiquidBlock.LEVEL)).intValue();
    }
    return -1;
  }

  
  protected static boolean isExistInArray(int x, int y, int z, int[][] xyz, int end_i) {
    for (int i = 0; i <= end_i; i++) {
      if (xyz[i][0] == x && xyz[i][1] == y && xyz[i][2] == z) return true; 
    } 
    return false;
  }
}
