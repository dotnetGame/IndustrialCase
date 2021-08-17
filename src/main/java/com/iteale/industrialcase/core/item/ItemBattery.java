package com.iteale.industrialcase.core.item;


import com.iteale.industrialcase.api.item.ElectricItem;
import com.iteale.industrialcase.api.item.IElectricItem;
import com.iteale.industrialcase.api.item.IItemHudInfo;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBattery extends BaseElectricItem
{
    public ItemBattery(Properties properties, double maxCharge, double transferLimit, int tier) {
        super(properties.durability(27).setNoRepair(), maxCharge, transferLimit, tier);
    }

    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide || stack.getCount() != 1)
            return new InteractionResultHolder(InteractionResult.PASS, stack);

        if (ElectricItem.manager.getCharge(stack) > 0.0D) {
            boolean transferred = false;

            for (int i = 0; i < 9; i++) {
                ItemStack target = (ItemStack)player.getInventory().items.get(i);
                if (target != null && target != stack)
                {
                    if (ElectricItem.manager.discharge(target, Double.POSITIVE_INFINITY, 2147483647, true, true, true) <= 0.0D) {

                        double transfer = ElectricItem.manager.discharge(stack, 2.0D * this.transferLimit, 2147483647, true, true, true);
                        if (transfer > 0.0D) {


                            transfer = ElectricItem.manager.charge(target, transfer, this.tier, true, false);
                            if (transfer > 0.0D)

                            {
                                ElectricItem.manager.discharge(stack, transfer, 2147483647, true, true, false);
                                transferred = true; }
                        }
                    }  }
            }
            if (transferred && !level.isClientSide) {
                // player.openContainer.detectAndSendChanges();
            }
        }

        return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
    }

    public static int maxLevel = 4;
}
