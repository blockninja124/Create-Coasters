package com.blockninja.createcoasters.network.forge;

import com.blockninja.createcoasters.network.NetworkHandler;
import com.blockninja.createcoasters.network.packets.RCPacket;
import com.blockninja.createcoasters.network.packets.SyncDoSoundsPacket;
import com.blockninja.createcoasters.network.packets.SyncHandsUpTicksPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class NetworkHandlerImpl {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            NetworkHandler.networkRL,
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        int id = 0;

        INSTANCE.registerMessage(id++,
                SyncDoSoundsPacket.class,
                SyncDoSoundsPacket::encode,
                SyncDoSoundsPacket::decode,
                NetworkHandlerImpl::handle
        );

        INSTANCE.registerMessage(id++,
                SyncHandsUpTicksPacket.class,
                SyncHandsUpTicksPacket::encode,
                SyncHandsUpTicksPacket::decode,
                NetworkHandlerImpl::handle
        );

        /*INSTANCE.registerMessage(id++,
                MoveDownPacket.class,
                MoveDownPacket::encode,
                MoveDownPacket::decode,
                NetworkHandlerImpl::handle
        );

        INSTANCE.registerMessage(id++,
                ActivatePacket.class,
                ActivatePacket::encode,
                ActivatePacket::decode,
                NetworkHandlerImpl::handle
        );

        INSTANCE.registerMessage(id++,
                DeactivatePacket.class,
                DeactivatePacket::encode,
                DeactivatePacket::decode,
                NetworkHandlerImpl::handle
        );

        INSTANCE.registerMessage(id++,
                LogPacksPacket.class,
                LogPacksPacket::encode,
                LogPacksPacket::decode,
                NetworkHandlerImpl::handle
        );

        INSTANCE.registerMessage(id++,
                LogToServerPacket.class,
                LogToServerPacket::encode,
                LogToServerPacket::decode,
                NetworkHandlerImpl::handle
        );*/
    }

    private static <T extends RCPacket> void handle(T msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {
            if (ctx.get().getSender() == null) {
                // Client
                msg.handleClient();
            } else {
                // Server
                msg.handleServer();
            }
        });

        ctx.get().setPacketHandled(true);

    }

    public static <T extends RCPacket> void sendRCPacket(ServerPlayer to, T packet) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> to), packet);
    }

    public static <T extends RCPacket> void sendRCPacketToTracking(T packet, Entity entity) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
    }

    public static <T extends RCPacket> void sendRCPacketToServer(T packet) {
        INSTANCE.sendToServer(packet);
    }
}
