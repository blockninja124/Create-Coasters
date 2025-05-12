package com.blockninja.createcoasters.content.blocks;

import com.blockninja.createcoasters.content.blocks.entity.SpeedBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;


public class SpeedBlock extends Block implements IBE<SpeedBlockEntity> {
    public SpeedBlock(Properties props) {
        super(Properties.of().mapColor(MapColor.METAL).strength(2.0F));
    }

    @Override
    public Class<SpeedBlockEntity> getBlockEntityClass() {
        return SpeedBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SpeedBlockEntity> getBlockEntityType() {
        return ModBlocks.SPEED_BLOCK_ENTITY.get();
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpeedBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? (SpeedBlockEntity::clientTick) : SpeedBlockEntity::serverTick;
    }
}
