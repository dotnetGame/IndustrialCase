package com.iteale.industrialcase.core.block.generator.gui;

import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import com.iteale.industrialcase.core.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OldGeneratorScreen extends AbstractContainerScreen<GeneratorMenu> {
    private static final ResourceLocation GENERATOR_TEXTURE = new ResourceLocation(IndustrialCase.MODID, "textures/gui/container/generator.png");
    public OldGeneratorScreen(GeneratorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageHeight = 160;
        this.imageWidth = 175;

        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 92;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        super.renderLabels(poseStack, mouseX, mouseY);
        int storage = this.menu.getStorage();
        int capacity = this.menu.getCapacity();
        this.font.draw(poseStack,
                Util.toSiString(storage, 4) + "/" + Util.toSiString(capacity, 4),
                110, 38, 4210752);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float p_97788_, int p_97789_, int p_97790_) {
        // draw bg
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GENERATOR_TEXTURE);
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        // draw fuel gauge
        int fuelPercent;
        if (this.menu.getFuel() <= 0)
            fuelPercent = 0;
        else
            fuelPercent = (int)(13.0F * this.menu.getFuel() / this.menu.getTotalFuel());
        this.blit(poseStack, i + 56, j + 36 + 14 - fuelPercent, 176, 13 - fuelPercent, 14, fuelPercent + 1);

        // draw energy gauge
        int energyPercent = (int)(24.0F * this.menu.getStorage() / this.menu.getCapacity());
        this.blit(poseStack, i + 79, j + 35, 176, 15, energyPercent, 16);
    }
}