package com.iteale.industrialcase.core.block.container;


import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class ICContainer implements Container {
    private final int size;
    private final NonNullList<ItemStack> items;
    private List<ContainerListener> listeners;

    public ICContainer(int sizeIn) {
        this.size = sizeIn;
        this.items = NonNullList.withSize(sizeIn, ItemStack.EMPTY);
    }

    public ICContainer(ItemStack... itemsIn) {
        this.size = itemsIn.length;
        this.items = NonNullList.of(ItemStack.EMPTY, itemsIn);
    }


    @Override
    public int getContainerSize() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        if (index >= 0 && index < this.items.size()) {
            return this.items.get(index);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack removeItem(int index, int splitCount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.items, index, splitCount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack itemstack = this.items.get(index);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(index, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public void setItem(int index, ItemStack item) {
        this.items.set(index, item);
        if (!item.isEmpty() && item.getCount() > this.getMaxStackSize()) {
            item.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public void setChanged() {
        if (this.listeners != null) {
            for(ContainerListener containerlistener : this.listeners) {
                containerlistener.containerChanged(this);
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.setChanged();
    }
}