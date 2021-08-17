package com.iteale.industrialcase.core.block.comp;


import com.iteale.industrialcase.core.block.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class Redstone extends BlockEntityComponent {
    private int redstoneInput;
    private Set<IRedstoneChangeHandler> changeSubscribers;

    public Redstone(BlockEntityBase parent) {
        super(parent);
    }
    private Set<IRedstoneModifier> modifiers; private LinkHandler outboundLink;

    public void onLoaded() {
        super.onLoaded();

        // update();
    }


    public void onUnloaded() {
        // unlinkOutbound();
        // unlinkInbound();

        super.onUnloaded();
    }


    public void onNeighborChange(Block srcBlock, BlockPos neighborPos) {
        super.onNeighborChange(srcBlock, neighborPos);

        update();
    }

    public void update() {
        Level world = this.parent.getLevel();
        if (world == null) {
            return;
        }


        int input = world.getDirectSignalTo(this.parent.getBlockPos());

        if (this.modifiers != null) {
            for (IRedstoneModifier modifier : this.modifiers) {
                input = modifier.getRedstoneInput(input);
            }
        }

        if (input != this.redstoneInput) {
            this.redstoneInput = input;

            if (this.changeSubscribers != null) {
                for (IRedstoneChangeHandler subscriber : this.changeSubscribers) {
                    subscriber.onRedstoneChange(input);
                }
            }
        }
    }

    public int getRedstoneInput() {
        return this.redstoneInput;
    }

    public boolean hasRedstoneInput() {
        return (this.redstoneInput > 0);
    }

    public void subscribe(IRedstoneChangeHandler handler) {
        if (handler == null) throw new NullPointerException("null handler");
        if (this.changeSubscribers == null) this.changeSubscribers = new HashSet<>();

        this.changeSubscribers.add(handler);
    }

    public void unsubscribe(IRedstoneChangeHandler handler) {
        if (handler == null) throw new NullPointerException("null handler");
        if (this.changeSubscribers == null)
            return;
        this.changeSubscribers.remove(handler);

        if (this.changeSubscribers.isEmpty()) this.changeSubscribers = null;
    }

    public void addRedstoneModifier(IRedstoneModifier modifier) {
        if (this.modifiers == null) {
            this.modifiers = new HashSet<>();
        }

        this.modifiers.add(modifier);
    }

    public void addRedstoneModifiers(Collection<IRedstoneModifier> modifiers) {
        if (this.modifiers == null) {
            this.modifiers = new HashSet<>(modifiers);
        } else {
            this.modifiers.addAll(modifiers);
        }
    }

    public void removeRedstoneModifier(IRedstoneModifier modifier) {
        if (this.modifiers == null)
            return;
        this.modifiers.remove(modifier);
    }

    public void removeRedstoneModifiers(Collection<IRedstoneModifier> modifiers) {
        if (this.modifiers == null)
            return;
        this.modifiers.removeAll(modifiers);
        if (this.modifiers.isEmpty()) this.modifiers = null;
    }

    public boolean isLinked() {
        return (this.outboundLink != null);
    }

    public Redstone getLinkReceiver() {
        return (this.outboundLink != null) ? this.outboundLink.receiver : null;
    }


    public Collection<Redstone> getLinkedOrigins() {
        if (this.modifiers == null) return Collections.emptyList();

        List<Redstone> ret = new ArrayList<>(this.modifiers.size());

        for (IRedstoneModifier modifier : this.modifiers) {
            if (modifier instanceof LinkHandler) {
                ret.add(((LinkHandler)modifier).origin);
            }
        }

        return Collections.unmodifiableList(ret);
    }

    public void linkTo(Redstone receiver) {
        if (receiver == null) throw new NullPointerException("null receiver");

        if (this.outboundLink != null) {
            if (this.outboundLink.receiver != receiver) {
                throw new IllegalStateException("already linked");
            }

            return;
        }

        this.outboundLink = new LinkHandler(this, receiver);

        this.outboundLink.receiver.addRedstoneModifier(this.outboundLink);
        subscribe(this.outboundLink);
        receiver.update();
    }

    public void unlinkOutbound() {
        if (this.outboundLink == null)
            return;
        this.outboundLink.receiver.removeRedstoneModifier(this.outboundLink);
        unsubscribe(this.outboundLink);
        this.outboundLink = null;
    }

    public void unlinkInbound() {
        for (Redstone origin : getLinkedOrigins()) {
            origin.unlinkOutbound();
        }
    }


    private static class LinkHandler
            implements IRedstoneChangeHandler, IRedstoneModifier
    {
        private final Redstone origin;

        private final Redstone receiver;


        public LinkHandler(Redstone origin, Redstone receiver) {
            this.origin = origin;
            this.receiver = receiver;
        }


        public void onRedstoneChange(int newLevel) {
            this.receiver.update();
        }


        public int getRedstoneInput(int redstoneInput) {
            return Math.max(redstoneInput, this.origin.redstoneInput);
        }
    }

    public static interface IRedstoneChangeHandler {
        void onRedstoneChange(int param1Int);
    }

    public static interface IRedstoneModifier {
        int getRedstoneInput(int param1Int);
    }
}