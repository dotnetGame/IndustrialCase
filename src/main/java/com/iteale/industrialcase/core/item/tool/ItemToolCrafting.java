package com.iteale.industrialcase.core.item.tool;

import com.iteale.industrialcase.core.item.ItemIC;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;


public class ItemToolCrafting extends ItemIC {

    public ItemToolCrafting(Properties properties) {
        super(properties);
    }

    //@OnlyIn(Dist.CLIENT)
    // public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        //tooltip.add(Localization.translate("ic2.item.ItemTool.tooltip.UsesLeft", new Object[] { Integer.valueOf(getRemainingUses(stack)) }));
    //}
}
