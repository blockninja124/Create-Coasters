package com.blockninja.createcoasters.network.packets;

import com.blockninja.createcoasters.mixin_interfaces.CarriageEntityExtraAccess;
import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

public class SyncDoSoundsPacket extends RCPacket {
    public final int entityId;
    public final boolean doSounds;

    public SyncDoSoundsPacket(int entityId, boolean doSounds) {
        this.entityId = entityId;
        this.doSounds = doSounds;
    }

    @SuppressWarnings("unused")
    public static void encode(SyncDoSoundsPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.doSounds);
    }

    @SuppressWarnings("unused")
    public static SyncDoSoundsPacket decode(FriendlyByteBuf buf) {
        return new SyncDoSoundsPacket(buf.readInt(), buf.readBoolean());
    }

    @Override
    public void handleClient() {
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);
        if (entity instanceof CarriageContraptionEntity carriageContraptionEntity) {
            ((CarriageEntityExtraAccess) carriageContraptionEntity).setDoSounds(doSounds);
        }
    }
}
