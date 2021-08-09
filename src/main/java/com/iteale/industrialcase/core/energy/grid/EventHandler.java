package com.iteale.industrialcase.core.energy.grid;

import com.iteale.industrialcase.api.energy.EnergyNet;
import com.iteale.industrialcase.api.energy.event.EnergyTileLoadEvent;
import com.iteale.industrialcase.api.energy.event.EnergyTileUnloadEvent;
import com.iteale.industrialcase.api.info.ILocatable;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.util.LogCategory;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class EventHandler
{
  private static boolean initialized;
  
  public static void init() {
    if (initialized) throw new IllegalStateException("already initialized"); 
    initialized = true;
    
    MinecraftForge.EVENT_BUS.register(new EventHandler());
  }


  
  @SubscribeEvent
  public void onEnergyTileLoad(EnergyTileLoadEvent event) {
    if (event.getWorld().isClientSide()) {
      IndustrialCase.log.warn(LogCategory.EnergyNet, "EnergyTileLoadEvent: posted for %s client-side, aborting", Util.toString(event.tile, event.getWorld(), EnergyNet.instance.getPos(event.tile)));
      
      return;
    } 
    if (event.tile instanceof BlockEntity) {
      EnergyNet.instance.addTile((BlockEntity)event.tile);
    } else if (event.tile instanceof ILocatable) {
      EnergyNet.instance.addTile((ILocatable)event.tile);
    } else {
      throw new IllegalArgumentException("invalid tile type: " + event.tile);
    } 
  }
  
  @SubscribeEvent
  public void onEnergyTileUnload(EnergyTileUnloadEvent event) {
    if ((event.getWorld()).isClientSide()) {
      IndustrialCase.log.warn(LogCategory.EnergyNet, "EnergyTileUnloadEvent: posted for %s client-side, aborting", new Object[] { Util.toString(event.tile, event.getWorld(), EnergyNet.instance.getPos(event.tile)) });
      
      return;
    } 
    EnergyNet.instance.removeTile(event.tile);
  }
  
  @SubscribeEvent
  public void onWorldTick(TickEvent.WorldTickEvent event) {
    EnergyNetLocal enet = EnergyNetGlobal.getLocal(event.world);
    
    if (event.phase == TickEvent.Phase.START) {
      enet.onTickStart();
    } else {
      enet.onTickEnd();
    } 
  }
}
