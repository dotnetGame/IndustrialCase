package com.iteale.industrialcase.core.block.inventory;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.IInventorySlotHolder;
import com.iteale.industrialcase.core.util.LogCategory;
import com.iteale.industrialcase.core.util.StackUtil;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class InvSlot implements Iterable<ItemStack> {
    public final IInventorySlotHolder<?> base;
    public final String name;
    private final ItemStack[] contents;

    public InvSlot(IInventorySlotHolder<?> base, String name, Access access, int count) {
        this(base, name, access, count, InvSide.ANY);
    }

    protected final Access access;
    public final InvSide preferredSide;
    private int stackSizeLimit;

    public InvSlot(IInventorySlotHolder<?> base, String name, Access access, int count, InvSide preferredSide) {
        if (count <= 0) throw new IllegalArgumentException("invalid slot count: " + count);

        this.contents = new ItemStack[count];
        clear();

        this.base = base;
        this.name = name;
        this.access = access;
        this.preferredSide = preferredSide;
        this.stackSizeLimit = 64;

        base.addInventorySlot(this);
    }


    public InvSlot(int count) {
        this.contents = new ItemStack[count];
        clear();

        this.base = null;
        this.name = null;
        this.access = Access.NONE;
        this.preferredSide = InvSide.ANY;
    }

    public void load(CompoundTag nbt) {
        clear();
        ListTag contentsTag = nbt.getList("Contents", 10);

        for (int i = 0; i < contentsTag.size(); i++) {
            CompoundTag contentTag = contentsTag.getCompound(i);

            int index = contentTag.getByte("Index") & 0xFF;

            if (index >= size()) {
                IndustrialCase.log.error(LogCategory.Block, "Can't load item stack for %s, slot %s, index %d is out of bounds.",
                        Util.toString(this.base.getParent()), this.name, index
                );
            } else {
                ItemStack stack = ItemStack.of(contentTag);

                if (stack.isEmpty()) {
                    IndustrialCase.log.warn(LogCategory.Block, "Can't load item stack %s for %s, slot %s, index %d, no matching item for %d:%d.",
                            stack, Util.toString(this.base.getParent()), this.name, index, contentTag.getShort("id"), contentTag.getShort("Damage")
                    );
                } else {

                    if (!isEmpty(index)) {
                        IndustrialCase.log.error(LogCategory.Block, "Loading content to non-empty slot for %s, slot %s, index %d, replacing %s with %s.",
                                Util.toString(this.base.getParent()), this.name, index, get(index), stack
                        );
                    }
                    putFromNBT(index, stack);
                }
            }
        }
        onChanged();
    }

    public void save(CompoundTag nbt) {
        ListTag contentsTag = new ListTag();

        for (int i = 0; i < this.contents.length; i++) {
            ItemStack content = this.contents[i];
            if (!StackUtil.isEmpty(content)) {

                CompoundTag contentTag = new CompoundTag();

                contentTag.putByte("Index", (byte) i);
                content.save(contentTag);

                contentsTag.add(contentTag);
            }
        }
        nbt.put("Contents", contentsTag);
    }

    public int size() {
        return this.contents.length;
    }

    public boolean isEmpty() {
        for (ItemStack stack : this.contents) {
            if (!StackUtil.isEmpty(stack)) {
                return false;
            }
        }

        return true;
    }

    public boolean isEmpty(int index) {
        return StackUtil.isEmpty(this.contents[index]);
    }

    public ItemStack get() {
        return get(0);
    }

    public ItemStack get(int index) {
        return this.contents[index];
    }

    public void put(ItemStack content) {
        put(0, content);
    }

    protected void putFromNBT(int index, ItemStack content) {
        this.contents[index] = content;
    }

    public void put(int index, ItemStack content) {
        if (StackUtil.isEmpty(content))
            content = StackUtil.EMPTY;

        this.contents[index] = content;

        onChanged();
    }

    public void clear() {
        Arrays.fill((Object[])this.contents, StackUtil.EMPTY);
    }

    public void clear(int index) {
        put(index, StackUtil.EMPTY);
    }

    public void onChanged() {}

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
        for (int dstIndex = 0; dstIndex < this.contents.length - 1; dstIndex++) {
            ItemStack dst = this.contents[dstIndex];

            if (StackUtil.isEmpty(dst) || StackUtil.getSize(dst) < dst.getMaxStackSize())
            {


                for (int srcIndex = dstIndex + 1; srcIndex < this.contents.length; srcIndex++) {
                    ItemStack src = this.contents[srcIndex];
                    if (!StackUtil.isEmpty(src))
                    {


                        if (StackUtil.isEmpty(dst)) {
                            this.contents[srcIndex] = StackUtil.EMPTY;
                            this.contents[dstIndex] = dst = src;
                        } else if (StackUtil.checkItemEqualityStrict(dst, src)) {
                            int space = Math.min(getStackSizeLimit(), dst.getMaxStackSize() - StackUtil.getSize(dst));
                            int srcSize = StackUtil.getSize(src);

                            if (srcSize <= space)
                            { this.contents[srcIndex] = StackUtil.EMPTY;
                                this.contents[dstIndex] = dst = StackUtil.incSize(dst, srcSize);
                                if (srcSize == space)
                                    break;  }
                            else { this.contents[srcIndex] = StackUtil.decSize(src, space);
                                this.contents[dstIndex] = StackUtil.incSize(dst, space);
                                break; }

                        }  }
                }  }
        }
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }


    public Iterator<ItemStack> iterator() {
        return new Iterator<ItemStack>()
        {
            public boolean hasNext() {
                return (this.idx < InvSlot.this.contents.length);
            }


            public ItemStack next() {
                if (this.idx >= InvSlot.this.contents.length)
                    throw new NoSuchElementException();

                return InvSlot.this.contents[this.idx++];
            }


            public void remove() {
                throw new UnsupportedOperationException();
            }

            private int idx = 0;
        };
    }


    public String toString() {
        String ret = this.name + "[" + this.contents.length + "]: ";

        for (int i = 0; i < this.contents.length; i++) {
            ret = ret + this.contents[i];

            if (i < this.contents.length - 1) {
                ret = ret + ", ";
            }
        }

        return ret;
    }

    protected ItemStack[] backup() {
        ItemStack[] ret = new ItemStack[this.contents.length];

        for (int i = 0; i < this.contents.length; i++) {
            ItemStack content = this.contents[i];

            ret[i] = StackUtil.isEmpty(content) ? ItemStack.EMPTY : content.copy();
        }

        return ret;
    }

    protected void restore(ItemStack[] backup) {
        if (backup.length != this.contents.length) throw new IllegalArgumentException("invalid array size");

        for (int i = 0; i < this.contents.length; i++) {
            this.contents[i] = backup[i];
        }
    }

    public void onPickupFromSlot(Player player, ItemStack stack) {}

    public enum Access
    {
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