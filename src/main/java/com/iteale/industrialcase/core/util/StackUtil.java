package com.iteale.industrialcase.core.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class StackUtil {
    public static ItemStack emptyStack = ItemStack.EMPTY;
    static final Set<String> ignoredNbtKeys = new HashSet<>(Arrays.asList(new String[] { "damage", "charge", "energy", "advDmg" }));
    public static boolean isEmpty(ItemStack stack) {
        return (stack == null || stack == ItemStack.EMPTY || stack.isEmpty() || stack.getItem() == null || stack.getCount() <= 0);
    }

    public static boolean isEmpty(Player player, InteractionHand hand) {
        return isEmpty(player.getItemInHand(hand));
    }

    public static int getSize(ItemStack stack) {
        if (isEmpty(stack)) {
            return 0;
        }
        return stack.getCount();
    }

    public static ItemStack setSize(ItemStack stack, int size) {
        if (size <= 0) return emptyStack;

        stack.setCount(size);

        return stack;
    }

    public static ItemStack incSize(ItemStack stack) {
        return incSize(stack, 1);
    }

    public static ItemStack incSize(ItemStack stack, int amount) {
        return setSize(stack, getSize(stack) + amount);
    }

    public static ItemStack decSize(ItemStack stack) {
        return decSize(stack, 1);
    }

    public static ItemStack decSize(ItemStack stack, int amount) {
        return incSize(stack, -amount);
    }

    public static boolean check2(Iterable<List<ItemStack>> list) {
        for (List<ItemStack> list2 : list) {
            if (!check(list2)) return false;

        }
        return true;
    }

    public static boolean check(ItemStack[] array) {
        return check(Arrays.asList(array));
    }

    public static boolean check(Iterable<ItemStack> list) {
        for (ItemStack stack : list) {
            if (!check(stack)) return false;
        }
        return true;
    }

    public static boolean check(ItemStack stack) {
        return (stack.getItem() != null);
    }


    public static boolean checkItemEquality(ItemStack a, ItemStack b) {
        return ((isEmpty(a) && isEmpty(b)) || (
                !isEmpty(a) && !isEmpty(b) && a
                        .getItem() == b.getItem() && (
                        !a.hasTag() || a.getTag() == b.getTag()) &&
                        checkNbtEquality(a, b)));
    }

    public static boolean checkItemEquality(ItemStack a, Item b) {
        return ((isEmpty(a) && b == null) || (
                !isEmpty(a) && b != null && a.getItem() == b));
    }

    public static boolean checkItemEqualityStrict(ItemStack a, ItemStack b) {
        return ((isEmpty(a) && isEmpty(b)) || (
                !isEmpty(a) && !isEmpty(b) && a.sameItem(b) &&
                        checkNbtEqualityStrict(a, b)));
    }

    public static boolean checkNbtEqualityStrict(ItemStack a, ItemStack b) {
        CompoundTag nbtA = a.getTag();
        CompoundTag nbtB = b.getTag();

        if (nbtA == nbtB) return true;

        return (nbtA != null && nbtB != null && nbtA.equals(nbtB));
    }

    private static boolean checkNbtEquality(ItemStack a, ItemStack b) {
        return checkNbtEquality(a.getTag(), b.getTag());
    }

    public static boolean checkNbtEquality(CompoundTag a, CompoundTag b) {
        if (a == b) return true;

        Set<String> keysA = (a != null) ? a.getAllKeys() : Collections.<String>emptySet();
        Set<String> keysB = (b != null) ? b.getAllKeys() : Collections.<String>emptySet();
        Set<String> toCheck = new HashSet<>(Math.max(keysA.size(), keysB.size()));

        for (String key : keysA) {
            if (ignoredNbtKeys.contains(key))
                continue;
            if (!keysB.contains(key)) return false;
            toCheck.add(key);
        }

        for (String key : keysB) {
            if (ignoredNbtKeys.contains(key))
                continue;
            if (!keysA.contains(key)) return false;
            toCheck.add(key);
        }

        for (String key : toCheck) {
            if (!a.getCompound(key).equals(b.getCompound(key))) return false;
        }
        return true;
    }

    public static CompoundTag getOrCreateNbtData(ItemStack stack) {
        CompoundTag ret = stack.getTag();

        if (ret == null) {
            ret = new CompoundTag();

            stack.setTag(ret);
        }

        return ret;
    }

    public static String toStringSafe(ItemStack[] array) {
        return toStringSafe(Arrays.asList(array));
    }

    public static String toStringSafe(Iterable<ItemStack> list) {
        StringBuilder ret = new StringBuilder("[");

        for (ItemStack stack : list) {
            if (ret.length() > 1) ret.append(", ");

            ret.append(toStringSafe(stack));
        }

        return ret.append(']').toString();
    }

    public static String toStringSafe(ItemStack stack) {
        if (stack == null)
            return "(null)";
        if (stack.getItem() == null) {
            return getSize(stack) + "x(null)@(unknown)";
        }
        return stack.toString();
    }

    public static int getRawMeta(ItemStack stack) {
        // FIXME
        return Items.BLACK_DYE.getDamage(stack);
    }

    public static void setRawMeta(ItemStack stack, int meta) {
        if (meta < 0) throw new IllegalArgumentException("negative meta");

        Items.BLACK_DYE.setDamage(stack, meta);
    }


    public static ItemStack copy(ItemStack stack) {
        return stack.copy();
    }

    public static ItemStack copyWithSize(ItemStack stack, int newSize) {
        if (isEmpty(stack)) throw new IllegalArgumentException("empty stack: " + toStringSafe(stack));

        return setSize(copy(stack), newSize);
    }

    public static ItemStack copyShrunk(ItemStack stack, int amount) {
        if (isEmpty(stack)) throw new IllegalArgumentException("empty stack: " + toStringSafe(stack));

        return setSize(copy(stack), getSize(stack) - amount);
    }

    public static ItemStack copyWithWildCard(ItemStack stack) {
        ItemStack ret = copy(stack);
        setRawMeta(ret, 32767);

        return ret;
    }

    public static Collection<ItemStack> copy(Collection<ItemStack> c) {
        List<ItemStack> ret = new ArrayList<>(c.size());

        for (ItemStack stack : c) {
            ret.add(copy(stack));
        }

        return ret;
    }


    public static ItemStack wrapEmpty(ItemStack stack) {
        return (stack == null) ? emptyStack : stack;
    }
}
