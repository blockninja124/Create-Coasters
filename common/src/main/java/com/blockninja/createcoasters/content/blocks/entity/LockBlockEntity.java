package com.blockninja.createcoasters.content.blocks.entity;

import com.blockninja.createcoasters.content.blocks.ModBlocks;
import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
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

public class LockBlockEntity extends AbstractContraptionModBlockEntity {

    public LockBlockEntity(BlockPos pos, BlockState state, double dy) {
        super(ModBlocks.LOCK_BLOCK_ENTITY.get(), pos, state);
        this.dy = dy;
    }

    @Override
    public void onContraption(Level level, BlockPos blockPos, AbstractContraptionEntity contraption) {

        BlockState aboveBlock = level.getBlockState(blockPos.above());

        int redstone = level.getBestNeighborSignal(blockPos);

        // Use block above as color
        if (aboveBlock.getBlock() instanceof SeatBlock seatBlock) {
            ArrayList<DyeColor> currentColors = ((ContraptionEntityExtraAccess) contraption).getDisabledColors();
            // Lock color
            if (redstone > 0) {
                if (!(currentColors.contains(seatBlock.getColor()))) {
                    currentColors.add(seatBlock.getColor());
                }
                // Unlock color
            } else {
                currentColors.remove(seatBlock.getColor());
            }
            ((ContraptionEntityExtraAccess) contraption).setDisabledColors(currentColors);

            // Lock / Unlock all seats
        } else {
            // Lock all
            if (redstone > 0) {
                ArrayList<DyeColor> allColors = new ArrayList<>();
                for (BlockEntry<SeatBlock> seatBlockBlockEntry : AllBlocks.SEATS) {
                    Block block = seatBlockBlockEntry.getDefaultState().getBlock();
                    // Probably redundant
                    if (block instanceof SeatBlock seatBlock) {
                        allColors.add(seatBlock.getColor());
                    }
                }
                ((ContraptionEntityExtraAccess) contraption).setDisabledColors(allColors);
                // Unlock all
            } else {
                ((ContraptionEntityExtraAccess) contraption).setDisabledColors(new ArrayList<>());
            }
        }

    }
}
