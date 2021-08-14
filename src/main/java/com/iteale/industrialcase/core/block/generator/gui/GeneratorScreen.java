package com.iteale.industrialcase.core.block.generator.gui;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.BlockEntityBase;
import com.iteale.industrialcase.core.block.generator.blockentity.GeneratorBlockEntity;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.gui.EnergyGauge;
import com.iteale.industrialcase.core.gui.GuiIC;
import com.iteale.industrialcase.core.gui.Text;
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
        addElement(EnergyGauge.asBolt(this, 12, 44, () -> {
            HashMap<String, Float> data = new HashMap<>();
            data.put("storage", (float) menu.getStorage());
            data.put("capacity", (float) menu.getCapacity());
            data.put("fillRatio", (float) menu.getStorage() / menu.getCapacity());
            return data;
        }));
    }


    public ResourceLocation getBackgroundTexture() {
        return GENERATOR_TEXTURE;
    }
}
