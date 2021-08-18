package com.iteale.industrialcase.core.block;

import com.iteale.industrialcase.core.block.comp.ComparatorEmitter;
import com.iteale.industrialcase.core.block.invslot.InvSlot;
import com.iteale.industrialcase.core.block.invslot.InvSlotUpgrade;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BlockEntityInventory extends BlockEntityContainer
        implements IContainerHolder<BlockEntityInventory> {
    private final List<InvSlot> invSlots;
    private final IItemHandler[] itemHandler;
    protected final ComparatorEmitter comparator;

    public BlockEntityInventory(BlockEntityType<?> t, BlockPos pos, BlockState state) {
        super(t, pos, state);
        this.invSlots = new ArrayList<>();

        this.itemHandler = new IItemHandler[Direction.values().length + 1];
        this.comparator = addComponent(new ComparatorEmitter(this));
        this.comparator.setUpdate(this::calcRedstoneFromInvSlots);
    }

    private SlotLocation locateInvSlot(int extIndex) {
        if (extIndex < 0)
            return new SlotLocation(-1, -1);
        for (int i = 0; i < this.invSlots.size(); i++) {
            int size = (this.invSlots.get(i)).getContainerSize();
            if (extIndex < size)
                return new SlotLocation(i, extIndex);
            extIndex -= size;
        }
        return new SlotLocation(-1, -1);
    }

    private InvSlot getAt(SlotLocation loc) {
        return this.invSlots.get(loc.getIndex());
    }

    private ItemStack getStackAt(SlotLocation loc) {
        return getAt(loc).getItem(loc.getOffset());
    }

    @Override
    public void load(CompoundTag nbtTagCompound) {
        super.load(nbtTagCompound);
        CompoundTag invSlotsTag = nbtTagCompound.getCompound("InvSlots");
        for (InvSlot invSlot : this.invSlots)
            invSlot.load(invSlotsTag.getCompound(invSlot.name));
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        CompoundTag invSlotsTag = new CompoundTag();
        for (InvSlot invSlot : this.invSlots) {
            CompoundTag invSlotTag = new CompoundTag();
            invSlot.save(invSlotTag);
            invSlotsTag.put(invSlot.name, invSlotTag);
        }
        nbt.put("InvSlots", invSlotsTag);
        return nbt;
    }

    protected static int calcRedstoneFromInvSlots(InvSlot... slots) {
        return calcRedstoneFromInvSlots(Arrays.asList(slots));
    }

    protected int calcRedstoneFromInvSlots() {
        return calcRedstoneFromInvSlots(this.invSlots);
    }

    protected static int calcRedstoneFromInvSlots(Iterable<InvSlot> invSlots) {
        int space = 0;
        int used = 0;
        for (InvSlot slot : invSlots) {
            if (slot instanceof InvSlotUpgrade)
                continue;
            int size = slot.getContainerSize();
            int limit = slot.getMaxStackSize();
            space += size * limit;
            for (int i = 0; i < size; i++) {
                ItemStack stack = slot.getItem(i);
                if (!StackUtil.isEmpty(stack))
                    used += Math.min(limit, stack.getCount() * limit / stack.getMaxStackSize());
            }
        }
        if (used == 0 || space == 0)
            return 0;
        return 1 + used * 14 / space;
    }

    @Override
    public BlockEntityInventory getParent() {
        return this;
    }

    @Override
    public InvSlot getContainer(String paramString) {
        for (InvSlot invSlot : this.invSlots) {
            if (invSlot.name.equals(paramString))
                return invSlot;
        }
        return null;
    }

    @Override
    public void addContainer(InvSlot paramContainer) {
        assert this.invSlots.stream().noneMatch(slot -> slot.name.equals(paramContainer.name));
        this.invSlots.add(paramContainer);
    }

    @Override
    public int getBaseIndex(InvSlot paramContainer) {
        int ret = 0;
        for (InvSlot slot : this.invSlots) {
            if (slot == paramContainer)
                return ret;
            ret += slot.getContainerSize();
        }
        return -1;
    }

    @Override
    public int getContainerSize() {
        int totalSize = 0;
        for (InvSlot invSlot : this.invSlots) {
            totalSize += invSlot.getContainerSize();
        }
        return totalSize;
    }

    @Override
    public boolean isEmpty() {
        for (InvSlot invSlot : this.invSlots) {
            if (!invSlot.isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        SlotLocation loc = locateInvSlot(index);
        if (!loc.valid())
            return ItemStack.EMPTY;
        return getStackAt(loc);
    }

    @Override
    public ItemStack removeItem(int index, int splitCount) {
        SlotLocation loc = locateInvSlot(index);
        if (!loc.valid())
            return ItemStack.EMPTY;
        return this.invSlots.get(loc.getIndex()).removeItem(loc.getOffset(), splitCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        SlotLocation loc = locateInvSlot(index);
        if (!loc.valid())
            return ItemStack.EMPTY;
        return this.invSlots.get(loc.getIndex()).removeItemNoUpdate(loc.getOffset());
    }

    @Override
    public void clearContent() {
        for (InvSlot invSlot : this.invSlots) {
            invSlot.clearContent();
        }
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        SlotLocation loc = locateInvSlot(index);
        if (!loc.valid())
            return;
        this.invSlots.get(loc.getIndex()).setItem(loc.getOffset(), itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    class SlotLocation {
        public int containerIndex;
        public int itemIndex;
        public SlotLocation(int containerIndex, int itemIndex) {
            this.containerIndex = containerIndex;
            this.itemIndex = itemIndex;
        }
        public int getIndex() {
            return this.containerIndex;
        }

        public int getOffset() {
            return this.itemIndex;
        }

        public boolean valid() {
            return this.containerIndex >= 0 && this.itemIndex>=0;
        }
    }
}