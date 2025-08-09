package com.blockninja.createcoasters.network.packets;

import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.blockninja.createcoasters.network.NetworkHandler;
import com.simibubi.create.AllPackets;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TrainIconType;
import com.simibubi.create.content.trains.station.TrainEditPacket;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class SyncIconPacket extends RCPacket {
    private String name;
    private UUID id;
    private ResourceLocation iconType;

    public SyncIconPacket(UUID id, String name, ResourceLocation iconType) {
        this.name = name;
        this.id = id;
        this.iconType = iconType;
    }

    public SyncIconPacket(FriendlyByteBuf buffer) {
        id = buffer.readUUID();
        name = buffer.readUtf(256);
        iconType = buffer.readResourceLocation();
    }

    public static void encode(SyncIconPacket msg, FriendlyByteBuf buffer) {
        buffer.writeUUID(msg.id);
        buffer.writeUtf(msg.name);
        buffer.writeResourceLocation(msg.iconType);
    }

    public static SyncIconPacket decode(FriendlyByteBuf buf) {
        return new SyncIconPacket(buf.readUUID(), buf.readUtf(), buf.readResourceLocation());
    }

    /*@Override
    public void handleServer(ServerPlayer sender) {
        Level level = sender == null ? null : sender.level();
        Train train = Create.RAILWAYS.sided(level).trains.get(id);
        if (train == null)
            return;
        if (!name.isBlank())
            train.name = Components.literal(name);
        train.icon = TrainIconType.byId(iconType);

    }*/

    @Override
    public void handleClient() {
        Train train = Create.RAILWAYS.sided(Minecraft.getInstance().level).trains.get(id);
        if (train == null)
            return;
        if (!name.isBlank())
            train.name = Components.literal(name);
        train.icon = TrainIconType.byId(iconType);
    }
}
