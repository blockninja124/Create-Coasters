package com.blockninja.createcoasters.content.blocks.entity;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class AbstractContraptionModBlockEntity extends SmartBlockEntity {
    public double dy = 0;

    protected ScrollValueBehaviour horizontalDistance;

    public AbstractContraptionModBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState state, T t) {
        if (Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            CreateClient.OUTLINER.showAABB(t, getSearchBox(blockPos, (AbstractContraptionModBlockEntity) t), 1);
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T be) {
        if (be instanceof AbstractContraptionModBlockEntity abe) {
            AABB searchBox = getSearchBox(blockPos, abe);
            List<AbstractContraptionEntity> entities = level.getEntitiesOfClass(AbstractContraptionEntity.class, searchBox);
            for (AbstractContraptionEntity entity : entities) {
                abe.onContraption(level, blockPos, entity);
            }
        }
    }

    public abstract void onContraption(Level level, BlockPos blockPos, AbstractContraptionEntity contraption);

    public static AABB getSearchBox(BlockPos blockPos, AbstractContraptionModBlockEntity abe) {
        return new AABB(blockPos.getCenter().subtract(0.5, 0.5, 0.5).subtract(abe.horizontalDistance.getValue(), 0, abe.horizontalDistance.getValue()), blockPos.getCenter().add(0.5, 0.5, 0.5).add(new Vec3(abe.horizontalDistance.getValue(), abe.dy, abe.horizontalDistance.getValue())));
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

        horizontalDistance = new ScrollValueBehaviour(Component.translatable("lockblock.label"),
                this, new SetSpeedValueBox(Direction.Axis.Y));
        horizontalDistance.between(0, 5);
        horizontalDistance.value = 0;
        behaviours.add(horizontalDistance);
    }

    private class SetSpeedValueBox extends ValueBoxTransform.Sided {
        Direction.Axis inactive;

        public SetSpeedValueBox(Direction.Axis inactive) {
            this.inactive = inactive;
        }

        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 8, 16);
        }

        @Override
        public Vec3 getLocalOffset(BlockState state) {
            Direction facing = Direction.UP;
            return super.getLocalOffset(state).add(Vec3.atLowerCornerOf(facing.getNormal())
                    .scale(0));
        }

        @Override
        public void rotate(BlockState state, PoseStack ms) {
            super.rotate(state, ms);
            Direction facing = Direction.UP;

            TransformStack.cast(ms)
                    .rotateZ(-AngleHelper.horizontalAngle(facing));
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return direction.getAxis() != inactive;
        }

    }
}
