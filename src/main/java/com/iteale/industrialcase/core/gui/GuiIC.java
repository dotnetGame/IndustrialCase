package com.iteale.industrialcase.core.gui;


import com.iteale.industrialcase.core.block.generator.menu.GeneratorMenu;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class GuiIC<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    private boolean fixKeyEvents;
    private boolean tick;
    private boolean background;
    private boolean mouseClick;
    private boolean mouseDrag;
    private boolean mouseRelease;

    private boolean mouseScroll;
    private boolean key;
    // private final Queue<Tooltip> queuedTooltips;
    protected final T container;
    protected final List<GuiElement<?>> elements;
    public static final int textHeight = 8;

    public GuiIC(T container, Inventory inventory, Component component) {
        this(container, inventory, component, 176, 166);
    }

    public GuiIC(T container, Inventory inventory, Component component, int ySize) {
        this(container, inventory, component, 176, ySize);
    }

    public GuiIC(T container, Inventory inventory, Component component, int xSize, int ySize) {
        super(container, inventory, component);

        this.fixKeyEvents = false;
        this.tick = false; this.background = false; this.mouseClick = false; this.mouseDrag = false; this.mouseRelease = false; this.mouseScroll = false; this.key = false;
        // this.queuedTooltips = new ArrayDeque();


        this.elements = new ArrayList();
        this.container = container;
        this.imageHeight = ySize;
        this.imageWidth = xSize;
    }

    public T getContainer() {
        return this.container;
    }
}
