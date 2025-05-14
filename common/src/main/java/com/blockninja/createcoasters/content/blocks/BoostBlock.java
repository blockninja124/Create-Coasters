package com.blockninja.createcoasters.content.blocks;

import com.blockninja.createcoasters.content.blocks.entity.BoostBlockEntity;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;


public class BoostBlock extends Block implements IBE<BoostBlockEntity>, IWrenchable {
    public static final BooleanProperty INVERTED = BooleanProperty.create("inverted");

    public BoostBlock(Properties props) {
        super(Properties.of().mapColor(MapColor.METAL).strength(2.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(INVERTED, true));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(INVERTED));
    }

    @Override
    public Class<BoostBlockEntity> getBlockEntityClass() {
        return BoostBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BoostBlockEntity> getBlockEntityType() {
        return ModBlocks.BOOST_BLOCK_ENTITY.get();
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AllShapes.STATION;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            level.setBlock(context.getClickedPos(), state.cycle(INVERTED), 2);

            System.out.println(state.getValue(INVERTED));
            return InteractionResult.SUCCESS;
        }
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
