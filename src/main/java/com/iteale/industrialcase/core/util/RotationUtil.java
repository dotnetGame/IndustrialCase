package com.iteale.industrialcase.core.util;


import com.iteale.industrialcase.core.IndustrialCase;
import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class RotationUtil
{
  public static Direction rotateByRay(BlockHitResult ray) {
    assert ray.getType() == BlockHitResult.Type.BLOCK;
    Vec3 hit = ray.getLocation();
    BlockPos pos = ray.getBlockPos();
    return rotateByHit(ray.getDirection(), (float)hit.x - pos.getX(), (float)hit.y - pos.getY(), (float)hit.z - pos.getZ());
  }
  
  public static Direction rotateByHit(Direction facingHit, float hitX, float hitY, float hitZ) {
    switch (facingHit) {
      case DOWN:
        if (hitX <= 0.25F) {
          if (hitZ > 0.25F && hitZ < 0.75F) {
            return Direction.WEST;
          }
          return Direction.UP;
        } 
        if (hitX > 0.25F && hitX < 0.75F) {
          if (hitZ <= 0.25F)
            return Direction.NORTH; 
          if (hitZ >= 0.75F) {
            return Direction.SOUTH;
          }
          return Direction.DOWN;
        } 
        if (hitX >= 0.75F) {
          if (hitZ > 0.25F && hitZ < 0.75F) {
            return Direction.EAST;
          }
          return Direction.UP;
        } 
        break;
      
      case UP:
        if (hitX <= 0.25F) {
          if (hitZ > 0.25F && hitZ < 0.75F) {
            return Direction.WEST;
          }
          return Direction.DOWN;
        } 
        if (hitX > 0.25F && hitX < 0.75F) {
          if (hitZ <= 0.25F)
            return Direction.NORTH; 
          if (hitZ >= 0.75F) {
            return Direction.SOUTH;
          }
          return Direction.UP;
        } 
        if (hitX >= 0.75F) {
          if (hitZ > 0.25F && hitZ < 0.75F) {
            return Direction.EAST;
          }
          return Direction.DOWN;
        } 
        break;
      
      case NORTH:
        if (hitX <= 0.25F) {
          if (hitY > 0.25F && hitY < 0.75F) {
            return Direction.WEST;
          }
          return Direction.SOUTH;
        } 
        if (hitX > 0.25F && hitX < 0.75F) {
          if (hitY <= 0.25F)
            return Direction.DOWN; 
          if (hitY >= 0.75F) {
            return Direction.UP;
          }
          return Direction.NORTH;
        } 
        if (hitX >= 0.75F) {
          if (hitY > 0.25F && hitY < 0.75F) {
            return Direction.EAST;
          }
          return Direction.SOUTH;
        } 
        break;
      
      case SOUTH:
        if (hitX <= 0.25F) {
          if (hitY > 0.25F && hitY < 0.75F) {
            return Direction.WEST;
          }
          return Direction.NORTH;
        } 
        if (hitX > 0.25F && hitX < 0.75F) {
          if (hitY <= 0.25F)
            return Direction.DOWN; 
          if (hitY >= 0.75F) {
            return Direction.UP;
          }
          return Direction.SOUTH;
        } 
        if (hitX >= 0.75F) {
          if (hitY > 0.25F && hitY < 0.75F) {
            return Direction.EAST;
          }
          return Direction.NORTH;
        } 
        break;
      
      case WEST:
        if (hitZ <= 0.25F) {
          if (hitY > 0.25F && hitY < 0.75F) {
            return Direction.NORTH;
          }
          return Direction.EAST;
        } 
        if (hitZ > 0.25F && hitZ < 0.75F) {
          if (hitY <= 0.25F)
            return Direction.DOWN; 
          if (hitY >= 0.75F) {
            return Direction.UP;
          }
          return Direction.WEST;
        } 
        if (hitZ >= 0.75F) {
          if (hitY > 0.25F && hitY < 0.75F) {
            return Direction.SOUTH;
          }
          return Direction.EAST;
        } 
        break;
      
      case EAST:
        if (hitZ <= 0.25F) {
          if (hitY > 0.25F && hitY < 0.75F) {
            return Direction.NORTH;
          }
          return Direction.WEST;
        } 
        if (hitZ > 0.25F && hitZ < 0.75F) {
          if (hitY <= 0.25F)
            return Direction.DOWN; 
          if (hitY >= 0.75F) {
            return Direction.UP;
          }
          return Direction.EAST;
        } 
        if (hitZ >= 0.75F) {
          if (hitY > 0.25F && hitY < 0.75F) {
            return Direction.SOUTH;
          }
          return Direction.WEST;
        } 
        break;
    } 

    
    return facingHit;
  }
  
  public static int[] shuffledFacings() {
    int[] ordinals = { 0, 1, 2, 3, 4, 5 };
    
    for (int i = ordinals.length - 1; i > 0; i--) {
      int index = IndustrialCase.random.nextInt(i + 1);
      if (index != i) {
        ordinals[index] = ordinals[index] ^ ordinals[i];
        ordinals[i] = ordinals[i] ^ ordinals[index];
        ordinals[index] = ordinals[index] ^ ordinals[i];
      } 
    } 
    
    return ordinals;
  }
}
