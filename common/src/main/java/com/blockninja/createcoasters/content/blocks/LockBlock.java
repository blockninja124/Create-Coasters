package com.blockninja.createcoasters.content.blocks;

import com.blockninja.createcoasters.content.blocks.entity.LockBlockEntity;
import com.blockninja.createcoasters.content.blocks.entity.SpeedBlockEntity;
import com.simibubi.create.content.equipment.goggles.IHaveHoveringInformation;
import com.simibubi.create.content.equipment.goggles.IProxyHoveringInformation;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.IHaveBigOutline;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;

import java.util.concurrent.locks.Lock;


public class LockBlock extends Block implements IBE<LockBlockEntity> {
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public LockBlock(Properties props) {
        super(Properties.of().mapColor(MapColor.METAL).strength(2.0F).noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(POWERED, false));
    }

    @Override
    public Class<LockBlockEntity> getBlockEntityClass() {
        return LockBlockEntity.class;
    }

    @Override
    public BlockEntityType<LockBlockEntity> getBlockEntityType() {
        return ModBlocks.LOCK_BLOCK_ENTITY.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(POWERED));
    }

    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (!level.isClientSide) {
            int redstone = level.getBestNeighborSignal(blockPos);
            boolean newPowered = redstone > 0;
            if (newPowered != blockState.getValue(POWERED)) {
                level.setBlock(blockPos, blockState.cycle(POWERED), 2);
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LockBlockEntity(pos, state, 4);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? (LockBlockEntity::clientTick) : LockBlockEntity::serverTick;
    }

}
