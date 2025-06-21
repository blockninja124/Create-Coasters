package com.blockninja.createcoasters.content.blocks.entity;

import com.blockninja.createcoasters.content.blocks.ModBlocks;
import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.blockninja.createcoasters.network.NetworkHandler;
import com.blockninja.createcoasters.network.packets.SyncDoSoundsPacket;
import com.blockninja.createcoasters.network.packets.SyncHandsUpTicksPacket;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class HandsUpBlockEntity extends AbstractContraptionModBlockEntity {

    public HandsUpBlockEntity(BlockPos pos, BlockState state, double dy) {
        super(ModBlocks.HANDS_UP_BLOCK_ENTITY.get(), pos, state);
        this.dy = dy;
    }

    @Override
    public void onContraption(Level level, BlockPos blockPos, AbstractContraptionEntity contraption) {

        int newTicks = level.getBestNeighborSignal(blockPos) * 5;
        int currentTicks = ((ContraptionEntityExtraAccess) contraption).getHandsUpTicks();
        if (newTicks > currentTicks) {
            NetworkHandler.sendRCPacketToTracking(new SyncHandsUpTicksPacket(contraption.getId(), newTicks), contraption);
            ((ContraptionEntityExtraAccess) contraption).setHandsUpTicks(newTicks);
        }

    }
}
