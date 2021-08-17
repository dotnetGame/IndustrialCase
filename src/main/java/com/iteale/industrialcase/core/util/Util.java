package com.iteale.industrialcase.core.util;


import com.iteale.industrialcase.core.IndustrialCase;
import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Util
{
  public static int roundToNegInf(float x) {
    int ret = (int)x;
    
    if (ret > x) ret--;
    
    return ret;
  }
  
  public static int roundToNegInf(double x) {
    int ret = (int)x;
    
    if (ret > x) ret--;
    
    return ret;
  }
  
  public static int saturatedCast(double x) {
    if (x > 2.147483647E9D)
      return Integer.MAX_VALUE; 
    if (x < -2.147483648E9D) {
      return Integer.MIN_VALUE;
    }
    return (int)x;
  }

  
  public static int limit(int value, int min, int max) {
    if (value <= min)
      return min; 
    if (value >= max) {
      return max;
    }
    return value;
  }

  
  public static float limit(float value, float min, float max) {
    if (Float.isNaN(value) || value <= min)
      return min; 
    if (value >= max) {
      return max;
    }
    return value;
  }

  
  public static double limit(double value, double min, double max) {
    if (Double.isNaN(value) || value <= min)
      return min; 
    if (value >= max) {
      return max;
    }
    return value;
  }

  
  public static double map(double value, double srcMax, double dstMax) {
    if (value < 0.0D || Double.isNaN(value)) value = 0.0D; 
    if (value > srcMax) value = srcMax;
    
    return value / srcMax * dstMax;
  }
  
  public static double lerp(double start, double end, double fraction) {
    assert fraction >= 0.0D && fraction <= 1.0D;
    
    return start + (end - start) * fraction;
  }
  
  public static float lerp(float start, float end, float fraction) {
    assert fraction >= 0.0F && fraction <= 1.0F;
    
    return start + (end - start) * fraction;
  }
  
  public static int square(int x) {
    return x * x;
  }
  
  public static float square(float x) {
    return x * x;
  }
  
  public static double square(double x) {
    return x * x;
  }
  
  public static boolean isSimilar(float a, float b) {
    return (Math.abs(a - b) < 1.0E-5F);
  }
  
  public static boolean isSimilar(double a, double b) {
    return (Math.abs(a - b) < 1.0E-5D);
  }
  
  public static int countInArray(Object[] oa, Class<?>... clsz) {
    int ret = 0;
    for (Object o : oa) {
      for (Class<?> cls : clsz) {
        if (cls.isAssignableFrom(o.getClass())) ret++; 
      } 
    } 
    return ret;
  }
  
  public static int countInArray(Object[] oa, Class<?> cls) {
    int ret = 0;
    for (Object o : oa) {
      if (cls.isAssignableFrom(o.getClass())) ret++; 
    } 
    return ret;
  }
  
  public static boolean inDev() {
    return inDev;
  }
  
  public static boolean hasAssertions() {
    boolean ret = false;
    assert ret = true;
    return ret;
  }


  public static boolean checkInterfaces(Class<?> cls) {
    Boolean cached = checkedClasses.get(cls);
    if (cached != null) return cached.booleanValue();

    Set<Class<?>> interfaces = Collections.newSetFromMap(new IdentityHashMap<>());
    Class<?> c = cls;

    do {
      for (Class<?> i : c.getInterfaces()) {
        interfaces.add(i);
      }

      c = c.getSuperclass();
    } while (c != null);

    boolean result = true;

    for (Class<?> iface : interfaces) {
      for (Method method : iface.getMethods()) {
        boolean found = false;
        c = cls;

        do {
          try {
            Method match = c.getDeclaredMethod(method.getName(), method.getParameterTypes());

            if (method.getReturnType().isAssignableFrom(match.getReturnType())) {
              found = true;
              break;
            }
          } catch (NoSuchMethodException noSuchMethodException) {}

          c = c.getSuperclass();
        } while (c != null);

        if (!found) {
          IndustrialCase.log.info(LogCategory.General, "Can't find method %s.%s in %s.", new Object[] { method.getDeclaringClass().getName(), method.getName(), cls.getName() });
          result = false;
        }
      }
    }

    checkedClasses.put(cls, Boolean.valueOf(result));

    return result;
  }


  public static String toString(BlockEntity te) {
    if (te == null) return "null";

    return toString(te, te.getLevel(), te.getBlockPos());
  }

  public static String toString(Object o, BlockGetter world, BlockPos pos) {
    return toString(o, world, pos.getX(), pos.getY(), pos.getZ());
  }

  public static String toString(Object o, BlockGetter world, int x, int y, int z) {
    StringBuilder ret = new StringBuilder(64);

    if (o == null) {
      ret.append("null");
    } else {
      ret.append(o.getClass().getName());
      ret.append('@');
      ret.append(Integer.toHexString(System.identityHashCode(o)));
    }

    ret.append(" (");
    ret.append(formatPosition(world, x, y, z));
    ret.append(")");

    return ret.toString();
  }
  
  public static String formatPosition(int dimId, int x, int y, int z) {
    return "dim " + dimId + ": " + x + "/" + y + "/" + z;
  }

  public static String formatPosition(BlockEntity te) {
    return formatPosition(te.getLevel(), te.getBlockPos());
  }

  public static String formatPosition(BlockGetter world, BlockPos pos) {
    return formatPosition(world, pos.getX(), pos.getY(), pos.getZ());
  }


  public static String formatPosition(BlockGetter world, int x, int y, int z) {
    String dimId;
    if (world instanceof Level && ((Level)world).dimension() != null) {
      dimId = ((Level)world).dimension().location().toString();
    } else {
      dimId = "None";
    }

    return String.format("dim %s (@%x): %d/%d/%d", dimId, System.identityHashCode(world), x, y, z);
  }
  
  public static String formatPosition(BlockPos pos) {
    return formatPosition(pos.getX(), pos.getY(), pos.getZ());
  }
  
  public static String formatPosition(int x, int y, int z) {
    return x + "/" + y + "/" + z;
  }
  public static String toSiString(double value, int digits) {
    String si;
    if (value == 0.0D) return "0 "; 
    if (Double.isNaN(value)) return "NaN "; 
    String ret = "";
    
    if (value < 0.0D) {
      ret = "-";
      value = -value;
    } 
    
    if (Double.isInfinite(value)) return ret + "∞ ";

    
    double log = Math.log10(value);


    double mul;
    if (log >= 0.0D) {
      int reduce = (int)Math.floor(log / 3.0D);
      mul = 1.0D / Math.pow(10.0D, (reduce * 3));
      
      switch (reduce) { case 0:
          si = ""; break;
        case 1: si = "k"; break;
        case 2: si = "M"; break;
        case 3: si = "G"; break;
        case 4: si = "T"; break;
        case 5: si = "P"; break;
        case 6: si = "E"; break;
        case 7: si = "Z"; break;
        case 8: si = "Y"; break;
        default: si = "E" + (reduce * 3); break; }
    
    } else {
      int expand = (int)Math.ceil(-log / 3.0D);
      mul = Math.pow(10.0D, (expand * 3));
      
      switch (expand) { case 0:
          si = ""; break;
        case 1: si = "m"; break;
        case 2: si = "µ"; break;
        case 3: si = "n"; break;
        case 4: si = "p"; break;
        case 5: si = "f"; break;
        case 6: si = "a"; break;
        case 7: si = "z"; break;
        case 8: si = "y"; break;
        default: si = "E-" + (expand * 3);
          break; }

    
    } 
    value *= mul;

    
    int iVal = (int)Math.floor(value);
    value -= iVal;

    
    int iDigits = 1;
    if (iVal > 0) iDigits = (int)(iDigits + Math.floor(Math.log10(iVal)));

    
    mul = Math.pow(10.0D, (digits - iDigits));
    int dVal = (int)Math.round(value * mul);

    
    if (dVal >= mul) {
      iVal++;
      dVal = (int)(dVal - mul);

      
      iDigits = 1;
      if (iVal > 0) iDigits = (int)(iDigits + Math.floor(Math.log10(iVal)));
    
    } 
    
    ret = ret + Integer.toString(iVal);

    
    if (digits > iDigits && dVal != 0) {
      ret = ret + String.format(".%0" + (digits - iDigits) + "d", new Object[] { Integer.valueOf(dVal) });
    }

    
    ret = ret.replaceFirst("(\\.\\d*?)0+$", "$1");
    
    return ret + " " + si;
  }
  
  public static void exit(int status) {
    Method exit = null;
    
    try {
      exit = Class.forName("java.lang.Shutdown").getDeclaredMethod("exit", new Class[] { int.class });
      exit.setAccessible(true);
    } catch (Exception e) {
      IndustrialCase.log.warn(LogCategory.General, e, "Method lookup failed.");
      
      try {
        Field security = System.class.getDeclaredField("security");
        security.setAccessible(true);
        security.set(null, null);
        
        exit = System.class.getMethod("exit", new Class[] { int.class });
      } catch (Exception f) {
        throw new Error(f);
      } 
    } 
    
    try {
      exit.invoke(null, new Object[] { Integer.valueOf(status) });
    } catch (Exception e) {
      throw new Error(e);
    } 
  }
  
  public static Vec3 getEyePosition(Entity entity) {
    return new Vec3(entity.position().x, entity.position().y + entity.getEyeHeight(), entity.position().z);
  }
  
  public static Vec3 getLook(Entity entity) {
    return entity.getLookAngle();
  }
  
  public static Set<Direction> noFacings = Collections.emptySet();
  public static Set<Direction> onlyNorth = Collections.unmodifiableSet(EnumSet.of(Direction.NORTH));
  public static Set<Direction> horizontalFacings = Collections.unmodifiableSet(EnumSet.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST));
  public static Set<Direction> verticalFacings = Collections.unmodifiableSet(EnumSet.of(Direction.DOWN, Direction.UP));
  public static Set<Direction> downSideFacings = Collections.unmodifiableSet(EnumSet.complementOf(EnumSet.of(Direction.UP)));
  public static Set<Direction> allFacings = Collections.unmodifiableSet(EnumSet.allOf(Direction.class));
  
  private static final boolean inDev = (System.getProperty("INDEV") != null);
  private static final boolean includeWorldHash = (System.getProperty("ic2.debug.includeworldhash") != null);
  private static final Map<Class<?>, Boolean> checkedClasses = new IdentityHashMap<>();
}

