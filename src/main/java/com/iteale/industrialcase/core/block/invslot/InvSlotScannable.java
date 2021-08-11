package com.iteale.industrialcase.core.block.invslot;

import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.TimeUnit;

public class InvSlotScannable
  extends InvSlotConsumable
{
  public InvSlotScannable(IInventorySlotHolder<?> base1, String name1, int count) {
    super(base1, name1, count);
    
    setStackSizeLimit(1);
  }

  
  public boolean accepts(ItemStack stack) {
    if (IC2.platform.isSimulating()) {
      return isValidStack(stack);
    }
    Rpc<Boolean> rpc = RpcHandler.run(ServerScannableCheck.class, new Object[] { stack });
    
    try {
      return ((Boolean)rpc.get(1L, TimeUnit.SECONDS)).booleanValue();
    } catch (Exception e) {
      IC2.log.debug(LogCategory.Block, e, "Scannability check failed.");
      
      return false;
    } 
  }

  
  private static boolean isValidStack(ItemStack stack) {
    stack = UuGraph.find(stack);
    return (!StackUtil.isEmpty(stack) && UuIndex.instance.get(stack) < Double.POSITIVE_INFINITY);
  }
  
  public static class ServerScannableCheck
    implements IRpcProvider<Boolean> {
    public Boolean executeRpc(Object... args) {
      ItemStack stack = (ItemStack)args[0];
      
      return Boolean.valueOf(InvSlotScannable.isValidStack(stack));
    }
  }
  
  static {
    RpcHandler.registerProvider(new ServerScannableCheck());
  }
}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\core\block\invslot\InvSlotScannable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */