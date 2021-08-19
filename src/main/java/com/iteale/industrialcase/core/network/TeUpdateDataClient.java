package com.iteale.industrialcase.core.network;


import com.iteale.industrialcase.core.block.TileEntityBlock;
import net.minecraft.core.BlockPos;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class TeUpdateDataClient
{
    public TeData addTe(BlockPos pos, int fieldCount) {
        TeData ret = new TeData(pos, fieldCount);
        this.updates.add(ret);
        return ret;
    }

    public Collection<TeData> getTes() {
        return this.updates;
    }
    static class TeData { final BlockPos pos;

        private TeData(BlockPos pos, int fieldCount) {
            this.pos = pos;
            this.fields = new ArrayList<>(fieldCount);
        }
        private final List<FieldData> fields; Class<? extends TileEntityBlock> teClass;
        public void addField(String name, Object value) {
            this.fields.add(new TeUpdateDataClient.FieldData(name, value));
        }

        public Collection<TeUpdateDataClient.FieldData> getFields() {
            return this.fields;
        } }


    static class FieldData {
        final String name;
        final Object value;
        Field field;

        private FieldData(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
    private final List<TeData> updates = new ArrayList<>();
}
