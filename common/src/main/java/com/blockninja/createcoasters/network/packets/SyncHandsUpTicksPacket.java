package com.blockninja.createcoasters.network.packets;

import com.blockninja.createcoasters.mixin_interfaces.CarriageEntityExtraAccess;
import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

public class SyncHandsUpTicksPacket extends RCPacket {
    public final int entityId;
    public final int handsUpTicks;

    public SyncHandsUpTicksPacket(int entityId, int handsUpTicks) {
        this.entityId = entityId;
        this.handsUpTicks = handsUpTicks;
    }

    @SuppressWarnings("unused")
    public static void encode(SyncHandsUpTicksPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.handsUpTicks);
    }

    @SuppressWarnings("unused")
    public static SyncHandsUpTicksPacket decode(FriendlyByteBuf buf) {
        return new SyncHandsUpTicksPacket(buf.readInt(), buf.readInt());
    }

    @Override
    public void handleClient() {
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);
        if (entity instanceof AbstractContraptionEntity abstractContraptionEntity) {
            ((ContraptionEntityExtraAccess) abstractContraptionEntity).setHandsUpTicks(handsUpTicks);
        }
    }
}
