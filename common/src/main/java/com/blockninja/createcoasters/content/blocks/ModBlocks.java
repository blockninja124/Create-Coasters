package com.blockninja.createcoasters.content.blocks;

import com.blockninja.createcoasters.CreateCoasters;
import com.blockninja.createcoasters.content.blocks.entity.BoostBlockEntity;
import com.blockninja.createcoasters.content.blocks.entity.LockBlockEntity;
import com.blockninja.createcoasters.content.blocks.entity.SpeedBlockEntity;
import com.blockninja.createcoasters.content.create.EdgePointTypes;
import com.blockninja.createcoasters.content.create.renderers.BoostBlockRenderer;
import com.blockninja.createcoasters.content.create.renderers.SpeedBlockRenderer;
import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
    private static final CreateRegistrate REGISTRATE = CreateCoasters.REGISTRATE;

    public static final BlockEntry<SpeedBlock> SPEED_BLOCK = REGISTRATE.block("speed_block", SpeedBlock::new)
            .item(TrackTargetingBlockItem.ofType(EdgePointTypes.SPEED_BLOCK))
            .build()
            .register();

    public static final BlockEntry<BoostBlock> BOOST_BLOCK = REGISTRATE.block("boost_block", BoostBlock::new)
            .item(TrackTargetingBlockItem.ofType(EdgePointTypes.SPEED_BLOCK))
            .build()
            .register();

    public static final BlockEntry<LockBlock> LOCK_BLOCK = REGISTRATE.block("lock_block", LockBlock::new)
            .simpleItem()
            .register();

    public static final BlockEntityEntry<SpeedBlockEntity> SPEED_BLOCK_ENTITY = REGISTRATE
            .blockEntity("speed_block_entity", (BlockEntityType<SpeedBlockEntity> type, BlockPos pos, BlockState state) ->
                    new SpeedBlockEntity(pos, state)
            )
            .renderer(() -> SpeedBlockRenderer::new)
            .validBlocks(SPEED_BLOCK)
            .register();

    public static final BlockEntityEntry<BoostBlockEntity> BOOST_BLOCK_ENTITY = REGISTRATE
            .blockEntity("boost_block_entity", (BlockEntityType<BoostBlockEntity> type, BlockPos pos, BlockState state) ->
                    new BoostBlockEntity(pos, state)
            )
            .renderer(() -> BoostBlockRenderer::new)
            .validBlocks(BOOST_BLOCK)
            .register();

    public static final BlockEntityEntry<LockBlockEntity> LOCK_BLOCK_ENTITY = REGISTRATE
            .blockEntity("lock_block_entity", (BlockEntityType<LockBlockEntity> type, BlockPos pos, BlockState state) ->
                    new LockBlockEntity(pos, state, 4)
            )
            .validBlocks(LOCK_BLOCK)
            .register();

    public static void register() {
        // Dummy call to force class loading
    }
}

