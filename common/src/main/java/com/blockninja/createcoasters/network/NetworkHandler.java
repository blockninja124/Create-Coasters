package com.blockninja.createcoasters.network;

import com.blockninja.createcoasters.CreateCoasters;
import com.blockninja.createcoasters.network.packets.RCPacket;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class NetworkHandler {

    public static final ResourceLocation networkRL = new ResourceLocation(CreateCoasters.MOD_ID, "network");

    @ExpectPlatform
    public static void registerPackets() {

    }

    @ExpectPlatform
    public static <T extends RCPacket> void sendRCPacket(ServerPlayer to, T packet) {
        throw new AssertionError("not platformed");
    }

    @ExpectPlatform
    public static <T extends RCPacket> void sendRCPacketToTracking(T packet, Entity entity) {
        throw new AssertionError("not platformed");
    }

    @ExpectPlatform
    public static <T extends RCPacket> void sendRCPacketToServer(T packet) {
        throw new AssertionError("not platformed");
    }
}

