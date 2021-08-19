package com.iteale.industrialcase.core.block.invslot;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.IContainerHolder;
import com.iteale.industrialcase.core.util.LogCategory;
import com.iteale.industrialcase.core.util.StackUtil;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class InvSlot implements Container {
    public final IContainerHolder<?> base;
    public String name;

    private final int size;
    private final NonNullList<ItemStack> contents;
    private List<ContainerListener> listeners;

    protected final Access access;
    public final InvSide preferredSide;
    private int stackSizeLimit;

    public InvSlot(IContainerHolder<?> base, String name, Access access, int count) {
        this(base, name, access, count, InvSide.ANY);
    }

    public InvSlot(IContainerHolder<?> base, String name, Access access, int count, InvSide preferredSide) {
        if (count <= 0)
            throw new IllegalArgumentException("invalid slot count: " + count);

        this.size = count;
        this.contents = NonNullList.withSize(count, ItemStack.EMPTY);
        clearContent();

        this.base = base;
        this.name = name;

        this.access = access;
        this.preferredSide = preferredSide;
        this.stackSizeLimit = 64;

        base.addContainer(this);
    }


    public InvSlot(int count) {
        if (count <= 0)
            throw new IllegalArgumentException("invalid slot count: " + count);

        this.size = count;
        this.contents = NonNullList.withSize(count, ItemStack.EMPTY);

        this.base = null;
        this.name = null;

        this.access = Access.NONE;
        this.preferredSide = InvSide.ANY;
        this.stackSizeLimit = 64;

    }

    public InvSlot(ItemStack... itemsIn) {
        this.size = itemsIn.length;
        this.contents = NonNullList.of(ItemStack.EMPTY, itemsIn);

        this.base = null;
        this.name = null;

        this.access = Access.NONE;
        this.preferredSide = InvSide.ANY;
        this.stackSizeLimit = 64;
    }

    public void load(CompoundTag nbt) {
        this.clearContent();
        ListTag contentsTag = nbt.getList("Contents", 10);

        for (int i = 0; i < contentsTag.size(); i++) {
            CompoundTag contentTag = contentsTag.getCompound(i);

            int index = contentTag.getByte("Index") & 0xFF;

            if (index >= getContainerSize()) {
                IndustrialCase.log.error(LogCategory.Block, "Can't load item stack for %s, slot %s, index %d is out of bounds.",
                        Util.toString(this.base.getParent()), this.name, index);
            } else {

                ItemStack stack = ItemStack.of(contentTag);

                if (StackUtil.isEmpty(stack)) {
                    IndustrialCase.log.warn(LogCategory.Block, "Can't load item stack %s for %s, slot %s, index %d, no matching item for %d:%d.",
                            StackUtil.toStringSafe(stack), Util.toString(this.base.getParent()), this.name, index, contentTag.getShort("id"), contentTag.getShort("Damage")
                    );
                } else {

                    if (!StackUtil.isEmpty(getItem(index))) {
                        IndustrialCase.log.error(LogCategory.Block, "Loading content to non-empty slot for %s, slot %s, index %d, replacing %s with %s.",
                                Util.toString(this.base.getParent()), this.name, index, getItem(index), stack
                        );
                    }
                    setItem(index, stack);
                }
            }
        }
        this.setChanged();
    }

    public void save(CompoundTag nbt) {
        ListTag contentsTag = new ListTag();

        for (int i = 0; i < this.contents.size(); i++) {
            ItemStack content = this.contents.get(i);
            if (!StackUtil.isEmpty(content)) {

                CompoundTag contentTag = new CompoundTag();

                contentTag.putByte("Index", (byte) i);
                content.save(contentTag);

                contentsTag.add(contentTag);
            }
        }
        nbt.put("Contents", contentsTag);
    }


    @Override
    public int getContainerSize() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.contents) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        if (index >= 0 && index < this.contents.size()) {
            return this.contents.get(index);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack removeItem(int index, int splitCount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.contents, index, splitCount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack itemstack = this.contents.get(index);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.contents.set(index, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public void setItem(int index, ItemStack item) {
        this.contents.set(index, item);
        if (!item.isEmpty() && item.getCount() > this.getMaxStackSize()) {
            item.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public void setChanged() {
        if (this.listeners != null) {
            for (ContainerListener containerlistener : this.listeners) {
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
        this.contents.clear();
        this.setChanged();
    }


    public boolean accepts(ItemStack stack) {
        return true;
    }

    public boolean canInput() {
        return (this.access == Access.I || this.access == Access.IO);
    }

    public boolean canOutput() {
        return (this.access == Access.O || this.access == Access.IO);
    }

    public void organize() {
        for (int dstIndex = 0; dstIndex < this.contents.size() - 1; dstIndex++) {
            ItemStack dst = this.contents.get(dstIndex);

            if (StackUtil.isEmpty(dst) || StackUtil.getSize(dst) < dst.getMaxStackSize()) {
                for (int srcIndex = dstIndex + 1; srcIndex < this.contents.size(); srcIndex++) {
                    ItemStack src = this.contents.get(srcIndex);
                    if (!StackUtil.isEmpty(src)) {
                        if (StackUtil.isEmpty(dst)) {
                            this.contents.set(srcIndex, StackUtil.emptyStack);
                            this.contents.set(dstIndex, dst);
                            this.contents.set(dstIndex, src);
                        } else if (StackUtil.checkItemEqualityStrict(dst, src)) {
                            int space = Math.min(getStackSizeLimit(), dst.getMaxStackSize() - StackUtil.getSize(dst));
                            int srcSize = StackUtil.getSize(src);

                            if (srcSize <= space) {
                                this.contents.set(srcIndex, StackUtil.emptyStack);
                                this.contents.set(dstIndex, dst);
                                this.contents.set(dstIndex, StackUtil.incSize(dst, srcSize));
                                if (srcSize == space)
                                    break;
                            } else {
                                this.contents.set(srcIndex, StackUtil.decSize(src, space));
                                this.contents.set(dstIndex, StackUtil.incSize(dst, space));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

    public void onPickupFromSlot(Player player, ItemStack stack) {}

    public enum Access {
        NONE,
        I,
        O,
        IO;

        public boolean isInput() {
            return ((ordinal() & 0x1) != 0);
        }

        public boolean isOutput() {
            return ((ordinal() & 0x2) != 0);
        }
    }

    public enum InvSide {
        ANY(Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST),
        TOP(Direction.UP),
        BOTTOM(Direction.DOWN),
        SIDE(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST),
        NOTSIDE(new Direction[0]);

        private Set<Direction> acceptedSides;

        InvSide(Direction... sides) {
            if (sides.length == 0) {
                this.acceptedSides = Collections.emptySet();
            } else {
                Set<Direction> acceptedSides = EnumSet.noneOf(Direction.class);
                acceptedSides.addAll(Arrays.asList(sides));
                this.acceptedSides = Collections.unmodifiableSet(acceptedSides);
            }
        }

        public boolean matches(Direction side) {
            return this.acceptedSides.contains(side);
        }

        public Set<Direction> getAcceptedSides() {
            return this.acceptedSides;
        }
    }
}