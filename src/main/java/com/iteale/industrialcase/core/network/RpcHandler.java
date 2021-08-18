package com.iteale.industrialcase.core.network;


import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.util.LogCategory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ChannelHandler.Sharable
public class RpcHandler extends SimpleChannelInboundHandler<Packet<?>>
{
    public static boolean registerProvider(IRpcProvider<?> provider) {
        return (providers.putIfAbsent(provider.getClass().getName(), provider) == null);
    }

    private static ConcurrentMap<String, IRpcProvider<?>> providers = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet<?> msg) throws Exception {

    }

    /*
    public static <V> Rpc<V> run(Class<? extends IRpcProvider<V>> provider, Object... args) {
        int id = IndustrialCase.random.nextInt();
        Rpc<V> rpc = new Rpc<>();


        Rpc<V> prev = (Rpc<V>)pending.putIfAbsent(Integer.valueOf(id), rpc);
        if (prev != null) return run(provider, args);

        IndustrialCase.network.initiateRpc(id, provider, args);

        return rpc;
    }


    protected static void processRpcRequest(GrowingBuffer is, ServerPlayer player) throws IOException {
        int id = is.readInt();
        String providerClassName = is.readString();
        Object[] args = (Object[])DataEncoder.decode(is);

        IRpcProvider<?> provider = providers.get(providerClassName);

        if (provider == null) {
            IndustrialCase.log.warn(LogCategory.Network, "Invalid RPC request from %s.", new Object[] { player.getName() });

            return;
        }
        Object result = provider.executeRpc(args);

        GrowingBuffer buffer = new GrowingBuffer(256);

        SubPacketType.Rpc.writeTo(buffer);
        buffer.writeInt(id);
        DataEncoder.encode(buffer, result, true);

        buffer.flip();
        IndustrialCase.network.sendPacket(buffer, true, player);
    }


    public RpcHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        String nettyHandlerName = "ic2_rpc_handler";

        if (event.getManager().channel().pipeline().get("ic2_rpc_handler") == null) {
            try {
                event.getManager().channel().pipeline().addBefore("packet_handler", "ic2_rpc_handler", (ChannelHandler)this);
            } catch (Exception e) {
                throw new RuntimeException("Can't insert handler in " + event.getManager().channel().pipeline().names() + ".", e);
            }
        }
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        for (Rpc<?> rpc : pending.values()) {
            rpc.cancel(true);
        }

        pending.clear();
    }


    protected void channelRead0(ChannelHandlerContext ctx, Packet<?> oPacket) throws Exception {
        FMLProxyPacket packet = null;

        if (oPacket instanceof FMLProxyPacket) {
            packet = (FMLProxyPacket)oPacket;
        } else if (oPacket instanceof SPacketCustomPayload) {
            packet = new FMLProxyPacket((SPacketCustomPayload)oPacket);
        }

        if (packet == null || !packet.channel().equals("ic2")) {
            ctx.fireChannelRead(oPacket);

            return;
        }
        ByteBuf payload = packet.payload();

        if (payload.isReadable() && payload.getByte(0) == SubPacketType.Rpc.getId()) {
            processRpcResponse(GrowingBuffer.wrap(packet.payload()));
        } else {
            ctx.fireChannelRead(oPacket);
        }
    }

    private void processRpcResponse(GrowingBuffer buffer) {
        try {
            buffer.readByte();

            int id = buffer.readInt();
            Object result = DataEncoder.decode(buffer);

            Rpc<?> rpc = pending.remove(Integer.valueOf(id));

            if (rpc == null) {
                IndustrialCase.log.warn(LogCategory.Network, "RPC %d wasn't found while trying to process its response.", new Object[] { Integer.valueOf(id) });
            } else {
                rpc.finish(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ConcurrentMap<Integer, Rpc<?>> pending = new ConcurrentHashMap<>();
     */
}

