package com.blockninja.createcoasters.content.create.renderers;

import com.blockninja.createcoasters.content.blocks.entity.BoostBlockEntity;
import com.blockninja.createcoasters.content.create.GenericTrackBlock;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.track.ITrackBlock;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour.RenderedTrackOverlayType;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BoostBlockRenderer extends SmartBlockEntityRenderer<BoostBlockEntity> {

    public BoostBlockRenderer(Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(BoostBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        BlockPos pos = be.getBlockPos();

        TrackTargetingBehaviour<GenericTrackBlock> target = be.edgePoint;
        BlockPos targetPosition = target.getGlobalPosition();
        Level level = be.getLevel();
        BlockState trackState = level.getBlockState(targetPosition);
        Block block = trackState.getBlock();

        if (!(block instanceof ITrackBlock))
            return;



        ms.pushPose();
        TransformStack.cast(ms)
                .translate(targetPosition.subtract(pos));
        RenderedTrackOverlayType type = RenderedTrackOverlayType.STATION;
        TrackTargetingBehaviour.render(level, targetPosition, target.getTargetDirection(), target.getTargetBezier(), ms,
                buffer, light, overlay, type, 1 + be.randomScale);
        ms.popPose();

    }

}