package com.blockninja.createcoasters.content.blocks;

import com.blockninja.createcoasters.content.blocks.entity.BoostBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;


public class BoostBlock extends Block implements IBE<BoostBlockEntity> {
    public BoostBlock(Properties props) {
        super(Properties.of().mapColor(MapColor.METAL).strength(2.0F));
    }

    @Override
    public Class<BoostBlockEntity> getBlockEntityClass() {
        return BoostBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BoostBlockEntity> getBlockEntityType() {
        return ModBlocks.BOOST_BLOCK_ENTITY.get();
    }



    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BoostBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? (BoostBlockEntity::clientTick) : BoostBlockEntity::serverTick;
    }
}
