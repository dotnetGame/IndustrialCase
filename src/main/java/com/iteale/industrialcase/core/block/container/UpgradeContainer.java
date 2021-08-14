package com.iteale.industrialcase.core.block.container;


import com.iteale.industrialcase.api.upgrade.IRemoteAccessUpgrade;
import com.iteale.industrialcase.api.upgrade.IUpgradableBlock;
import com.iteale.industrialcase.api.upgrade.IUpgradeItem;
import com.iteale.industrialcase.core.block.IContainerHolder;
import com.iteale.industrialcase.core.util.StackUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.redstone.Redstone;

import java.util.Collections;

public class UpgradeContainer extends ICContainer {
    private static final int maxStackSize = 64;
    public int augmentation;
    public int extraProcessTime;
    public double processTimeMultiplier;
    public int extraEnergyDemand;

    /*
    public static UpgradeContainer createUnchecked(IContainerHolder<?> base, String name, int count) {
        return new UpgradeContainer(base, name, count);
    }
    public double energyDemandMultiplier;
    public int extraEnergyStorage;
    public double energyStorageMultiplier;
    public int extraTier;
    private List<Redstone.IRedstoneModifier> redstoneModifiers;

    public <T extends IContainerHolder<?> & IUpgradableBlock> InvSlotUpgrade(T base, String name, int count) {
        super(base, name, ICContainer.Access.NONE, count);
        this.redstoneModifiers = Collections.emptyList();
        resetRates();
    }

    public boolean accepts(ItemStack stack) {
        Item rawItem = stack.getItem();
        if (!(rawItem instanceof IUpgradeItem))
            return false;
        IUpgradeItem item = (IUpgradeItem)rawItem;
        return item.isSuitableFor(stack, ((IUpgradableBlock)this.base).getUpgradableProperties());
    }

    public void onChanged() {
        resetRates();
        IUpgradableBlock block = (IUpgradableBlock)this.base;
        List<Redstone.IRedstoneModifier> newRedstoneModifiers = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            ItemStack stack = get(i);
            if (!StackUtil.isEmpty(stack) && accepts(stack)) {
                IUpgradeItem upgrade = (IUpgradeItem)stack.getItem();
                boolean all = upgrade instanceof ic2.api.upgrade.IFullUpgrade;
                int size = StackUtil.getSize(stack);
                if (all || upgrade instanceof IAugmentationUpgrade)
                    this.augmentation += ((IAugmentationUpgrade)upgrade).getAugmentation(stack, block) * size;
                if (all || upgrade instanceof IProcessingUpgrade) {
                    IProcessingUpgrade procUpgrade = (IProcessingUpgrade)upgrade;
                    this.extraProcessTime += procUpgrade.getExtraProcessTime(stack, block) * size;
                    this.processTimeMultiplier *= Math.pow(procUpgrade.getProcessTimeMultiplier(stack, block), size);
                    this.extraEnergyDemand += procUpgrade.getExtraEnergyDemand(stack, block) * size;
                    this.energyDemandMultiplier *= Math.pow(procUpgrade.getEnergyDemandMultiplier(stack, block), size);
                }
                if (all || upgrade instanceof IEnergyStorageUpgrade) {
                    IEnergyStorageUpgrade engUpgrade = (IEnergyStorageUpgrade)upgrade;
                    this.extraEnergyStorage += engUpgrade.getExtraEnergyStorage(stack, block) * size;
                    this.energyStorageMultiplier *= Math.pow(engUpgrade.getEnergyStorageMultiplier(stack, block), size);
                }
                if (all || upgrade instanceof ITransformerUpgrade)
                    this.extraTier += ((ITransformerUpgrade)upgrade).getExtraTier(stack, block) * size;
                if (all || upgrade instanceof IRedstoneSensitiveUpgrade) {
                    IRedstoneSensitiveUpgrade redUpgrade = (IRedstoneSensitiveUpgrade)upgrade;
                    if (redUpgrade.modifiesRedstoneInput(stack, block))
                        newRedstoneModifiers.add(new UpgradeRedstoneModifier(redUpgrade, stack, block));
                }
            }
        }
        for (BlockEntityComponent component : this.base.getParent().getComponents()) {
            if (!(component instanceof Redstone))
                continue;
            Redstone rs = (Redstone)component;
            rs.removeRedstoneModifiers(this.redstoneModifiers);
            rs.addRedstoneModifiers(newRedstoneModifiers);
            rs.update();
        }
        this.redstoneModifiers = newRedstoneModifiers.isEmpty() ? Collections.<Redstone.IRedstoneModifier>emptyList() : newRedstoneModifiers;
    }

    private void resetRates() {
        this.augmentation = 0;
        this.extraProcessTime = 0;
        this.processTimeMultiplier = 1.0D;
        this.extraEnergyDemand = 0;
        this.energyDemandMultiplier = 1.0D;
        this.extraEnergyStorage = 0;
        this.energyStorageMultiplier = 1.0D;
        this.extraTier = 0;
    }

    public int getOperationsPerTick(int defaultOperationLength) {
        if (defaultOperationLength == 0)
            return 64;
        return getOpsPerTick(getStackOpLen(defaultOperationLength));
    }

    public int getOperationLength(int defaultOperationLength) {
        if (defaultOperationLength == 0)
            return 1;
        double stackOpLen = getStackOpLen(defaultOperationLength);
        int opsPerTick = getOpsPerTick(stackOpLen);
        return Math.max(1, (int)Math.round(stackOpLen * opsPerTick / 64.0D));
    }

    private double getStackOpLen(int defaultOperationLength) {
        return (defaultOperationLength + this.extraProcessTime) * 64.0D * this.processTimeMultiplier;
    }

    private int getOpsPerTick(double stackOpLen) {
        return (int)Math.min(Math.ceil(64.0D / stackOpLen), 2.147483647E9D);
    }

    public int getEnergyDemand(int defaultEnergyDemand) {
        return applyModifier(defaultEnergyDemand, this.extraEnergyDemand, this.energyDemandMultiplier);
    }

    public int getEnergyStorage(int defaultEnergyStorage, int defaultOperationLength, int defaultEnergyDemand) {
        int opLen = getOperationLength(defaultOperationLength);
        int energyDemand = getEnergyDemand(defaultEnergyDemand);
        return applyModifier(defaultEnergyStorage, this.extraEnergyStorage + opLen * energyDemand, this.energyStorageMultiplier);
    }

    public int getTier(int defaultTier) {
        return applyModifier(defaultTier, this.extraTier, 1.0D);
    }

    public int getRemoteRange(int existingRange) {
        for (ItemStack stack : this) {
            if (StackUtil.isEmpty(stack) || !accepts(stack))
                continue;
            IUpgradeItem upgrade = (IUpgradeItem)stack.getItem();
            if (upgrade instanceof IRemoteAccessUpgrade)
                existingRange = ((IRemoteAccessUpgrade)upgrade).getRangeAmplification(stack, (IUpgradableBlock)this.base, existingRange);
        }
        return existingRange;
    }

    private static int applyModifier(int base, int extra, double multiplier) {
        double ret = Math.round((base + extra) * multiplier);
        return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int)ret;
    }

    public boolean tickNoMark() {
        IUpgradableBlock block = (IUpgradableBlock)this.base;
        boolean ret = false;
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!StackUtil.isEmpty(stack) && stack.getItem() instanceof IUpgradeItem)
                if (((IUpgradeItem)stack.getItem()).onTick(stack, block))
                    ret = true;
        }
        return ret;
    }

    public void tick() {
        if (tickNoMark())
            this.base.getParent().markDirty();
    }

    private static class UpgradeRedstoneModifier implements Redstone.IRedstoneModifier {
        private final IRedstoneSensitiveUpgrade upgrade;
        private final ItemStack stack;
        private final IUpgradableBlock block;

        UpgradeRedstoneModifier(IRedstoneSensitiveUpgrade upgrade, ItemStack stack, IUpgradableBlock block) {
            this.upgrade = upgrade;
            this.stack = stack.copy();
            this.block = block;
        }

        public int getRedstoneInput(int redstoneInput) {
            return this.upgrade.getRedstoneInput(this.stack, this.block, redstoneInput);
        }
    }
     */
}
