package com.iteale.industrialcase.core;


import com.iteale.industrialcase.core.block.TileEntityBlock;
import com.iteale.industrialcase.core.block.comp.BlockEntityComponent;
import com.iteale.industrialcase.core.network.NetworkManager;
import com.iteale.industrialcase.core.slot.SlotHologramSlot;
import com.iteale.industrialcase.core.slot.SlotInvSlot;
import com.iteale.industrialcase.core.slot.SlotInvSlotReadOnly;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class MenuBase<T extends Container> extends AbstractContainerMenu {
    protected static final int windowBorder = 8;
    protected static final int slotSize = 16;
    protected static final int slotDistance = 2;

    protected static final int slotSeparator = 4;
    protected static final int hotbarYOffset = -24;
    protected static final int inventoryYOffset = -82;
    public final T base;
    public final Inventory inventory;
    public final ContainerData data;

    protected MenuBase(@Nullable MenuType<?> menuType, int containerId, Inventory inventoryIn, T containerIn, ContainerData dataIn) {
        super(menuType, containerId);
        this.base = containerIn;
        this.inventory = inventoryIn;
        this.data = dataIn;
    }

    protected void addPlayerInventorySlots(Player player, int height) {
        addPlayerInventorySlots(player, 178, height);
    }

    protected void addPlayerInventorySlots(Player player, int width, int height) {
        int xStart = (width - 162) / 2;

        for (int row = 0; row < 3; row++) {
            for (int i = 0; i < 9; i++) {
                addSlot(new Slot(player.getInventory(), i + row * 9 + 9, xStart + i * 18, height + -82 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(player.getInventory(), col, xStart + col * 18, height + -24));
        }
    }

    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, Player player) {
        Slot slot = this.slots.get(slotId);
        if (slotId >= 0 && slotId < this.slots.size() && slot instanceof SlotHologramSlot) {
            return ((SlotHologramSlot)slot).slotClick(dragType, clickType, player);
        }
        super.clicked(slotId, dragType, clickType, player);
        return slot.getItem();
    }

    public final ItemStack transferStackInSlot(Player player, int sourceSlotIndex) {
        Slot sourceSlot = this.slots.get(sourceSlotIndex);

        if (sourceSlot != null && sourceSlot.hasItem()) {
            ItemStack resultStack, sourceItemStack = sourceSlot.getItem();

            int oldSourceItemStackSize = StackUtil.getSize(sourceItemStack);

            if (sourceSlot.container == player.getInventory()) {
                resultStack = handlePlayerSlotShiftClick(player, sourceItemStack);
            } else {
                resultStack = handleGUISlotShiftClick(player, sourceItemStack);
            }

            if (StackUtil.isEmpty(resultStack) || StackUtil.getSize(resultStack) != oldSourceItemStackSize) {
                sourceSlot.set(resultStack);
                sourceSlot.onTake(player, sourceItemStack);

                if (!(player.level).isClientSide) {
                    this.sendAllDataToRemote();
                }
            }
        }

        return StackUtil.emptyStack;
    }

    protected ItemStack handlePlayerSlotShiftClick(Player player, ItemStack sourceItemStack) {
        for (int run = 0; run < 4 && !StackUtil.isEmpty(sourceItemStack); run++) {
            for (Slot targetSlot : this.slots) {
                if (targetSlot.container != player.getInventory())
                    if (isValidTargetSlot(targetSlot, sourceItemStack, (run % 2 == 1), (run < 2))) {
                        sourceItemStack = transfer(sourceItemStack, targetSlot);

                        if (StackUtil.isEmpty(sourceItemStack))
                            break;
                    }
            }
        }
        return sourceItemStack;
    }

    protected ItemStack handleGUISlotShiftClick(Player player, ItemStack sourceItemStack) {
        for (int run = 0; run < 2 && !StackUtil.isEmpty(sourceItemStack); run++) {
            for (ListIterator<Slot> it = this.slots.listIterator(this.slots.size()); it.hasPrevious(); ) {
                Slot targetSlot = it.previous();

                if (targetSlot.container == player.getInventory() &&
                        isValidTargetSlot(targetSlot, sourceItemStack, (run == 1), false)) {
                    sourceItemStack = transfer(sourceItemStack, targetSlot);

                    if (StackUtil.isEmpty(sourceItemStack))
                        break;
                }
            }
        }
        return sourceItemStack;
    }

    protected static final boolean isValidTargetSlot(Slot slot, ItemStack stack, boolean allowEmpty, boolean requireInputOnly) {
        if (slot instanceof SlotInvSlotReadOnly || slot instanceof SlotHologramSlot)
        {
            return false;
        }

        if (!slot.mayPlace(stack)) return false;
        if (!allowEmpty && !slot.hasItem()) return false;

        if (requireInputOnly) {
            return (slot instanceof SlotInvSlot && ((SlotInvSlot)slot).invSlot
                    .canInput());
        }
        return true;
    }

    public boolean canInteractWith(Player entityPlayer) {
        return this.base.stillValid(entityPlayer);
    }

    @Override
    public void sendAllDataToRemote() {
        super.sendAllDataToRemote();

        if (this.base instanceof BlockEntity) {
            for (String name : getNetworkedFields()) {
                for (ContainerListener crafter : this.getListeners()) {
                    if (crafter instanceof ServerPlayer) {
                        ((NetworkManager)IndustrialCase.network).updateTileEntityFieldTo((BlockEntity)this.base, name, (EntityPlayerMP)crafter);
                    }
                }
            }

            if (this.base instanceof TileEntityBlock) {
                for (BlockEntityComponent component : ((TileEntityBlock)this.base).getComponents()) {
                    for (ContainerListener crafter : this.getListeners()) {
                        if (crafter instanceof ServerPlayer) {
                            component.onContainerUpdate((ServerPlayer)crafter);
                        }
                    }
                }
            }
        }
    }

    public List<String> getNetworkedFields() {
        return new ArrayList<>();
    }

    public List<ContainerListener> getListeners() {
        return this.getListeners();
    }


    public void onContainerEvent(String event) {}


    protected final ItemStack transfer(ItemStack stack, Slot dst) {
        int amount = getTransferAmount(stack, dst);
        if (amount <= 0) return stack;

        ItemStack dstStack = dst.getItem();

        if (StackUtil.isEmpty(dstStack)) {
            dst.set(StackUtil.copyWithSize(stack, amount));
        } else {
            dst.set(StackUtil.incSize(dstStack, amount));
        }

        stack = StackUtil.decSize(stack, amount);

        return stack;
    }


    private int getTransferAmount(ItemStack stack, Slot dst) {
        int amount = Math.min(dst.container.getMaxStackSize(), dst.getMaxStackSize());
        amount = Math.min(amount, stack.isStackable() ? stack.getMaxStackSize() : 1);

        ItemStack dstStack = dst.getItem();

        if (!StackUtil.isEmpty(dstStack)) {
            if (!StackUtil.checkItemEqualityStrict(stack, dstStack)) return 0;

            amount -= StackUtil.getSize(dstStack);
        }

        amount = Math.min(amount, StackUtil.getSize(stack));

        return amount;
    }
}

