package com.iteale.industrialcase.core.slot;


import net.minecraft.world.entity.EquipmentSlot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArmorSlot
{
    public static EquipmentSlot get(int index) {
        return armorSlots[index];
    }

    public static int getCount() {
        return armorSlots.length;
    }

    public static Iterable<EquipmentSlot> getAll() {
        return armorSlotList;
    }

    private static EquipmentSlot[] getArmorSlots() {
        EquipmentSlot[] values = EquipmentSlot.values();
        int count = 0;

        for (EquipmentSlot slot : values) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) count++;

        }
        EquipmentSlot[] ret = new EquipmentSlot[count];
        int i;
        for (i = 0; i < ret.length; i++) {
            for (EquipmentSlot slot : values) {
                if (slot.getType() == EquipmentSlot.Type.ARMOR && slot.getIndex() == i) {
                    ret[i] = slot;

                    break;
                }
            }
        }
        for (i = 0; i < ret.length; i++) {
            if (ret[i] == null) throw new RuntimeException("Can't find an armor mapping for idx " + i);

        }
        return ret;
    }

    private static final EquipmentSlot[] armorSlots = getArmorSlots();
    private static final List<EquipmentSlot> armorSlotList = Collections.unmodifiableList(Arrays.asList(armorSlots));
}

