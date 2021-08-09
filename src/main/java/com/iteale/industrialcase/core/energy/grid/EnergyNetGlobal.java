package com.iteale.industrialcase.core.energy.grid;

import com.iteale.industrialcase.api.energy.IEnergyNet;
import com.iteale.industrialcase.api.energy.IEnergyNetEventReceiver;
import com.iteale.industrialcase.api.energy.NodeStats;
import com.iteale.industrialcase.api.energy.tile.IEnergyTile;
import com.iteale.industrialcase.api.info.ILocatable;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.energy.leg.EnergyCalculatorLeg;
import com.iteale.industrialcase.core.util.LogCategory;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.WorldData;
import net.minecraftforge.server.permission.context.WorldContext;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnergyNetGlobal implements IEnergyNet
{
  public static EnergyNetGlobal create() {
    if (System.getProperty("IC2ExpEnet") != null);
    calculator = (IEnergyCalculator)new EnergyCalculatorLeg();

    EventHandler.init();
    
    return new EnergyNetGlobal();
  }

  public IEnergyTile getTile(Level world, BlockPos pos) {
    if (world == null) throw new NullPointerException("null world"); 
    if (pos == null) throw new NullPointerException("null pos");
    
    return getLocal(world).getIoTile(pos);
  }

  
  public IEnergyTile getSubTile(Level world, BlockPos pos) {
    if (world == null) throw new NullPointerException("null world"); 
    if (pos == null) throw new NullPointerException("null pos");
    
    return getLocal(world).getSubTile(pos);
  }

  
  public <T extends BlockEntity> void addTile(T tile) {
    if (tile == null) throw new NullPointerException("null tile");
    
    addTile((IEnergyTile)tile, tile.getLevel(), tile.getBlockPos());
  }

  
  public <T extends ILocatable> void addTile(T tile) {
    if (tile == null) throw new NullPointerException("null tile");
    
    addTile((IEnergyTile)tile, tile.getWorldObj(), tile.getPosition());
  }
  
  private static void addTile(IEnergyTile tile, Level world, BlockPos pos) {
    if (EnergyNetSettings.logEnetApiAccessTraces) {
      IndustrialCase.log.debug(LogCategory.EnergyNet, new Throwable("Called from:"), "API addTile %s.", Util.toString(tile, world, pos));
    } else if (EnergyNetSettings.logEnetApiAccesses) {
      IndustrialCase.log.debug(LogCategory.EnergyNet, "API addTile %s.", Util.toString(tile, world, pos));
    } 
    
    getLocal(world).addTile(tile, pos);
  }

  
  public void removeTile(IEnergyTile tile) {
    if (tile == null) throw new NullPointerException("null tile");
    
    Level world = getWorld(tile);
    BlockPos pos = getPos(tile);
    
    if (EnergyNetSettings.logEnetApiAccessTraces) {
      IndustrialCase.log.debug(LogCategory.EnergyNet, new Throwable("Called from:"), "API removeTile %s.", Util.toString(tile, world, pos));
    } else if (EnergyNetSettings.logEnetApiAccesses) {
      IndustrialCase.log.debug(LogCategory.EnergyNet, "API removeTile %s.", Util.toString(tile, world, pos) );
    } 
    
    getLocal(world).removeTile(tile, pos);
  }

  
  public Level getWorld(IEnergyTile tile) {
    if (tile == null) throw new NullPointerException("null tile");
    
    if (tile instanceof ILocatable)
      return ((ILocatable)tile).getWorldObj(); 
    if (tile instanceof BlockEntity) {
      return ((BlockEntity)tile).getLevel();
    }
    throw new UnsupportedOperationException("unlocatable tile type: " + tile.getClass().getName());
  }


  
  public BlockPos getPos(IEnergyTile tile) {
    if (tile == null) throw new NullPointerException("null tile");
    
    if (tile instanceof ILocatable)
      return ((ILocatable)tile).getPosition(); 
    if (tile instanceof BlockEntity) {
      return ((BlockEntity)tile).getBlockPos();
    }
    throw new UnsupportedOperationException("unlocatable tile type: " + tile.getClass().getName());
  }


  
  public NodeStats getNodeStats(IEnergyTile tile) {
    return getLocal(getWorld(tile)).getNodeStats(tile);
  }

  
  public boolean dumpDebugInfo(Level world, BlockPos pos, PrintStream console, PrintStream chat) {
    return getLocal(world).dumpDebugInfo(pos, console, chat);
  }

  
  public double getPowerFromTier(int tier) {
    if (tier < 14)
      return (8 << tier * 2); 
    if (tier < 30) {
      return 8.0D * Math.pow(4.0D, tier);
    }
    return 9.223372036854776E18D;
  }


  
  public int getTierFromPower(double power) {
    if (power <= 0.0D) return 0;
    
    return (int)Math.ceil(Math.log(power / 8.0D) / Math.log(4.0D));
  }

  
  public synchronized void registerEventReceiver(IEnergyNetEventReceiver receiver) {
    if (eventReceivers.contains(receiver))
      return;  eventReceivers.add(receiver);
  }

  
  public synchronized void unregisterEventReceiver(IEnergyNetEventReceiver receiver) {
    eventReceivers.remove(receiver);
  }
  
  static Iterable<IEnergyNetEventReceiver> getEventReceivers() {
    return eventReceivers;
  }
  
  static IEnergyCalculator getCalculator() {
    return calculator;
  }

  public static EnergyNetLocal getLocal(Level world) {
    if (world.isClientSide()) throw new IllegalStateException("not applicable clientside");
    assert world.getServer().isSameThread();

    // FIXME
    // return (WorldData.get(world)).energyNet;
    return null;
  }
  
  private static final List<IEnergyNetEventReceiver> eventReceivers = new CopyOnWriteArrayList<>();
  private static IEnergyCalculator calculator;
}
