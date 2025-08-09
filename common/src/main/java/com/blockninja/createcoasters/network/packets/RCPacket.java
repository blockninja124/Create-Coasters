package com.blockninja.createcoasters.network.packets;

import net.minecraft.server.level.ServerPlayer;

public abstract class RCPacket {

    public RCPacket() {}

    public void handleClient() {};

    public void handleServer(ServerPlayer serverPlayer) {};
}
