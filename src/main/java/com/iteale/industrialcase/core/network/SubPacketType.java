package com.iteale.industrialcase.core.network;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.util.LogCategory;

public enum SubPacketType
{
    Rpc(true, true),
    BlockEntityEvent(true, true),
    ItemEvent(true, true),
    PlayerItemData(true, true),
    ContainerData(true, true),
    ContainerEvent(true, true),
    HandHeldInvData(true, true),

    LargePacket(true, false),
    GuiDisplay(true, false),
    ExplosionEffect(true, false),
    BlockEntityBlockComponent(true, false),
    BlockEntityBlockLandEffect(true, false),
    BlockEntityBlockRunEffect(true, false),

    KeyUpdate(false, true),
    BlockEntityData(false, true),
    RequestGUI(false, true); private boolean serverToClient;

    SubPacketType(boolean serverToClient, boolean clientToServer) {
        this.serverToClient = serverToClient;
        this.clientToServer = clientToServer;
    }
    private boolean clientToServer; private static final SubPacketType[] values;
    public void writeTo(GrowingBuffer out) {
        out.writeByte(getId());
    }

    public int getId() {
        return ordinal() + 1;
    }

    public static SubPacketType read(GrowingBuffer in, boolean simulating) {
        int id = in.readUnsignedByte() - 1;

        if (id < 0 || id >= values.length) {
            IndustrialCase.log.warn(LogCategory.Network, "Invalid sub packet type: %d", new Object[] { Integer.valueOf(id) });
            return null;
        }

        SubPacketType ret = values[id];

        if ((simulating && !ret.clientToServer) || (!simulating && !ret.serverToClient)) {
            IndustrialCase.log.warn(LogCategory.Network, "Invalid sub packet type %s for side %s", new Object[] { ret.name(), simulating ? "server" : "client" });
            return null;
        }

        return ret;
    }

    static {
        values = values();
        if (values.length > 255) throw new RuntimeException("too many sub packet types");
    }
}

