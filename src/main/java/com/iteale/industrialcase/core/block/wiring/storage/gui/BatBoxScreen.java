package com.iteale.industrialcase.core.block.wiring.storage.gui;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.block.wiring.storage.menu.BatBoxMenu;
import com.iteale.industrialcase.core.gui.EnergyGauge;
import com.iteale.industrialcase.core.gui.FuelGauge;
import com.iteale.industrialcase.core.gui.GuiIC;
import com.iteale.industrialcase.core.gui.VanillaButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class BatBoxScreen extends ElectricScreen<BatBoxMenu>
{
    public BatBoxScreen(BatBoxMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, 176, 196);
        addElement(EnergyGauge.asBar(this, 79, 38, () -> {
            HashMap<String, Float> data = new HashMap<>();
            data.put("storage", (float) menu.getStorage());
            data.put("capacity", (float) menu.getCapacity());
            float ratio = 0.0F;
            if (menu.getCapacity() > 0 && menu.getStorage() > 0)
                ratio = (float) menu.getStorage() / menu.getCapacity();
            data.put("fillRatio", ratio);
            return data;
        }));

        addElement(new VanillaButton(this, 152, 4, 20, 20, null).withIcon(
                ()-> new ItemStack(Items.REDSTONE)
        ));
    }
}
