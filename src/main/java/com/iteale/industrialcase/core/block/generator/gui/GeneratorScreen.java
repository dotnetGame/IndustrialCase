package com.iteale.industrialcase.core.block.generator.gui;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.gui.EnergyGauge;
import com.iteale.industrialcase.core.gui.FuelGauge;
import com.iteale.industrialcase.core.gui.GuiIC;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class GeneratorScreen extends GuiIC<GeneratorMenu>
{
    private static final ResourceLocation GENERATOR_TEXTURE = new ResourceLocation(IndustrialCase.MODID, "textures/gui/container/generator.png");
    public GeneratorScreen(GeneratorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, 175, 160);
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

        addElement(FuelGauge.create(this, 56, 36, () -> {
            HashMap<String, Float> data = new HashMap<>();
            data.put("fuel", (float) menu.getFuel());
            data.put("totalFuel", (float) menu.getTotalFuel());
            return data;
        }));
    }

    public ResourceLocation getBackgroundTexture() {
        return GENERATOR_TEXTURE;
    }
}
