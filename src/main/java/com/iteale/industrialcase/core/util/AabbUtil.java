package com.iteale.industrialcase.core.util;

import com.mojang.math.Vector3d;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.mutable.MutableObject;

public class AabbUtil
{
  enum Edge
  {
    AD, AB, AE, DC, DH, BC, BF, EH, EF, CG, FG, HG;
  }

  public static Direction getIntersection(Vector3d origin, Vector3d direction, AABB bbox, MutableObject<Vector3d> intersection) {
    Vector3d planeOrigin;
    double length = Util.square(direction.x) + Util.square(direction.y) + Util.square(direction.z);
    
    if (Math.abs(length - 1.0D) > 1.0E-5D) {
      length = Math.sqrt(length);
      direction = new Vector3d(direction.x / length, direction.y / length, direction.z / length);
    }

    Direction intersectingDirection = intersects(origin, direction, bbox);
    if (intersectingDirection == null) return null;

    if (direction.x < 0.0D && direction.y < 0.0D && direction.z < 0.0D) {
      planeOrigin = new Vector3d(bbox.maxX, bbox.maxY, bbox.maxZ);
    } else if (direction.x < 0.0D && direction.y < 0.0D && direction.z >= 0.0D) {
      planeOrigin = new Vector3d(bbox.maxX, bbox.maxY, bbox.minZ);
    } else if (direction.x < 0.0D && direction.y >= 0.0D && direction.z < 0.0D) {
      planeOrigin = new Vector3d(bbox.maxX, bbox.minY, bbox.maxZ);
    } else if (direction.x < 0.0D && direction.y >= 0.0D && direction.z >= 0.0D) {
      planeOrigin = new Vector3d(bbox.maxX, bbox.minY, bbox.minZ);
    } else if (direction.x >= 0.0D && direction.y < 0.0D && direction.z < 0.0D) {
      planeOrigin = new Vector3d(bbox.minX, bbox.maxY, bbox.maxZ);
    } else if (direction.x >= 0.0D && direction.y < 0.0D && direction.z >= 0.0D) {
      planeOrigin = new Vector3d(bbox.minX, bbox.maxY, bbox.minZ);
    } else if (direction.x >= 0.0D && direction.y >= 0.0D && direction.z < 0.0D) {
      planeOrigin = new Vector3d(bbox.minX, bbox.minY, bbox.maxZ);
    } else {
      planeOrigin = new Vector3d(bbox.minX, bbox.minY, bbox.minZ);
    }

    Vector3d planeNormalVector = null;

    if (intersectingDirection == Direction.EAST || intersectingDirection == Direction.WEST)
        planeNormalVector = new Vector3d(1.0D, 0.0D, 0.0D);
    else if (intersectingDirection == Direction.UP || intersectingDirection == Direction.DOWN)
        planeNormalVector = new Vector3d(0.0D, 1.0D, 0.0D);
    else if (intersectingDirection == Direction.NORTH || intersectingDirection == Direction.SOUTH)
        planeNormalVector = new Vector3d(0.0D, 0.0D, 1.0D);
    
    if (intersection != null) {
      intersection.setValue(getIntersectionWithPlane(origin, direction, planeOrigin, planeNormalVector));
    }
    
    return intersectingDirection;
  }

  public static Direction intersects(Vector3d origin, Vector3d direction, AABB bbox) {
    double[] ray = getRay(origin, direction);
    
    if (direction.x < 0.0D && direction.y < 0.0D && direction.z < 0.0D) {
      if (origin.x < bbox.minX) return null; 
      if (origin.y < bbox.minY) return null; 
      if (origin.z < bbox.minZ) return null; 
      if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D) return null;
      
      if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
        return Direction.SOUTH;
      if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D) {
        return Direction.UP;
      }
      return Direction.EAST;
    } 
    if (direction.x < 0.0D && direction.y < 0.0D && direction.z >= 0.0D) {
      if (origin.x < bbox.minX) return null; 
      if (origin.y < bbox.minY) return null; 
      if (origin.z > bbox.maxZ) return null; 
      if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D) return null;
      
      if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D)
        return Direction.EAST;
      if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D) {
        return Direction.UP;
      }
      return Direction.NORTH;
    } 
    if (direction.x < 0.0D && direction.y >= 0.0D && direction.z < 0.0D) {
      if (origin.x < bbox.minX) return null; 
      if (origin.y > bbox.maxY) return null; 
      if (origin.z < bbox.minZ) return null; 
      if (side(ray, getEdgeRay(Edge.FG, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D) return null;
      
      if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D)
        return Direction.SOUTH;
      if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D) {
        return Direction.EAST;
      }
      return Direction.DOWN;
    } 
    if (direction.x < 0.0D && direction.y >= 0.0D && direction.z >= 0.0D) {
      if (origin.x < bbox.minX) return null; 
      if (origin.y > bbox.maxY) return null; 
      if (origin.z > bbox.maxZ) return null; 
      if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D) return null;
      
      if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D)
        return Direction.DOWN;
      if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D) {
        return Direction.NORTH;
      }
      return Direction.EAST;
    } 
    if (direction.x >= 0.0D && direction.y < 0.0D && direction.z < 0.0D) {
      if (origin.x > bbox.maxX) return null; 
      if (origin.y < bbox.minY) return null; 
      if (origin.z < bbox.minZ) return null; 
      if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AE, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.HG, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D) return null;
      
      if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
        return Direction.WEST;
      if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D) {
        return Direction.SOUTH;
      }
      return Direction.UP;
    } 
    if (direction.x >= 0.0D && direction.y < 0.0D && direction.z >= 0.0D) {
      if (origin.x > bbox.maxX) return null; 
      if (origin.y < bbox.minY) return null; 
      if (origin.z > bbox.maxZ) return null; 
      if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AE, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.EF, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D) return null;
      
      if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D)
        return Direction.NORTH;
      if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D) {
        return Direction.WEST;
      }
      return Direction.UP;
    } 
    if (direction.x >= 0.0D && direction.y >= 0.0D && direction.z < 0.0D) {
      if (origin.x > bbox.maxX) return null; 
      if (origin.y > bbox.maxY) return null; 
      if (origin.z < bbox.minZ) return null; 
      if (side(ray, getEdgeRay(Edge.BF, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.HG, bbox)) < 0.0D) return null; 
      if (side(ray, getEdgeRay(Edge.FG, bbox)) > 0.0D) return null;
      
      if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D)
        return Direction.WEST;
      if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D) {
        return Direction.DOWN;
      }
      return Direction.SOUTH;
    } 
    
    if (origin.x > bbox.maxX) return null; 
    if (origin.y > bbox.maxY) return null; 
    if (origin.z > bbox.maxZ) return null; 
    if (side(ray, getEdgeRay(Edge.EF, bbox)) < 0.0D) return null; 
    if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D) return null; 
    if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D) return null; 
    if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D) return null; 
    if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D) return null; 
    if (side(ray, getEdgeRay(Edge.BF, bbox)) > 0.0D) return null;
    
    if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D && side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D)
      return Direction.WEST;
    if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D) {
      return Direction.NORTH;
    }
    return Direction.DOWN;
  }
  
  private static double[] getRay(Vector3d origin, Vector3d direction) {
    double[] ret = new double[6];
    
    ret[0] = origin.x * direction.y - direction.x * origin.y;
    ret[1] = origin.x * direction.z - direction.x * origin.z;
    ret[2] = -direction.x;
    ret[3] = origin.y * direction.z - direction.y * origin.z;
    ret[4] = -direction.z;
    ret[5] = direction.y;
    
    return ret;
  }



  
  private static double[] getEdgeRay(Edge edge, AABB bbox) {
    switch (edge) {
      case AD:
        return new double[] { -bbox.minY, -bbox.minZ, -1.0D, 0.0D, 0.0D, 0.0D };
      case AB:
        return new double[] { bbox.minX, 0.0D, 0.0D, -bbox.minZ, 0.0D, 1.0D };
      case AE:
        return new double[] { 0.0D, bbox.minX, 0.0D, bbox.minY, -1.0D, 0.0D };
      case DC:
        return new double[] { bbox.maxX, 0.0D, 0.0D, -bbox.minZ, 0.0D, 1.0D };
      case DH:
        return new double[] { 0.0D, bbox.maxX, 0.0D, bbox.minY, -1.0D, 0.0D };
      case BC:
        return new double[] { -bbox.maxY, -bbox.minZ, -1.0D, 0.0D, 0.0D, 0.0D };
      case BF:
        return new double[] { 0.0D, bbox.minX, 0.0D, bbox.maxY, -1.0D, 0.0D };
      case EH:
        return new double[] { -bbox.minY, -bbox.maxZ, -1.0D, 0.0D, 0.0D, 0.0D };
      case EF:
        return new double[] { bbox.minX, 0.0D, 0.0D, -bbox.maxZ, 0.0D, 1.0D };
      case CG:
        return new double[] { 0.0D, bbox.maxX, 0.0D, bbox.maxY, -1.0D, 0.0D };
      case FG:
        return new double[] { -bbox.maxY, -bbox.maxZ, -1.0D, 0.0D, 0.0D, 0.0D };
      case HG:
        return new double[] { bbox.maxX, 0.0D, 0.0D, -bbox.maxZ, 0.0D, 1.0D };
    } 
    return new double[0];
  }

  private static double side(double[] ray1, double[] ray2) {
    return ray1[2] * ray2[3] + ray1[5] * ray2[1] + ray1[4] * ray2[0] + ray1[1] * ray2[5] + ray1[0] * ray2[4] + ray1[3] * ray2[2];
  }

  private static Vector3d getIntersectionWithPlane(Vector3d origin, Vector3d direction, Vector3d planeOrigin, Vector3d planeNormalVector) {
    double distance = getDistanceToPlane(origin, direction, planeOrigin, planeNormalVector);
    
    return new Vector3d(origin.x + direction.x * distance, origin.y + direction.y * distance, origin.z + direction.z * distance);
  }

  private static double getDistanceToPlane(Vector3d origin, Vector3d direction, Vector3d planeOrigin, Vector3d planeNormalVector) {
    Vector3d base = new Vector3d(planeOrigin.x - origin.x, planeOrigin.y - origin.y, planeOrigin.z - origin.z);


    
    return dotProduct(base, planeNormalVector) / dotProduct(direction, planeNormalVector);
  }


  private static double dotProduct(Vector3d a, Vector3d b) {
    return a.x * b.x + a.y * b.y + a.z * b.z;
  }
}
