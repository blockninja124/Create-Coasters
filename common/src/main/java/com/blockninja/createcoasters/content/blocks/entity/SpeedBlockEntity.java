package com.blockninja.createcoasters.content.blocks.entity;

import com.blockninja.createcoasters.content.blocks.ModBlocks;
import com.blockninja.createcoasters.content.create.EdgePointTypes;
import com.blockninja.createcoasters.content.create.GenericTrackBlock;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SpeedBlockEntity extends AbstractTrackBlockEntity<GenericTrackBlock> {
    protected ScrollValueBehaviour maxSpeed;


    public SpeedBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SPEED_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        maxSpeed = new ScrollValueBehaviour(Component.translatable("speedbox.label"),
                this, new SetSpeedValueBox());
        maxSpeed.between(0, 19);
        maxSpeed.value = 1;
        maxSpeed.withCallback(i -> this.updateGeneratedRotation());
        behaviours.add(maxSpeed);
    }

    @Override
    public EdgePointType<GenericTrackBlock> getEdgePointType() {
        return EdgePointTypes.SPEED_BLOCK;
    }

    private void updateGeneratedRotation() {

    }

    @Override
    public void onTrain(Train train, Level level, BlockPos blockPos) {
        if (level.getBestNeighborSignal(blockPos) == 0) {

            train.throttle = ((2 * 10 * 1.0D) - (double) maxSpeed.getValue()) / 2 / 10;
        }

    }

    class SetSpeedValueBox extends ValueBoxTransform.Sided {

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
            Direction facing = Direction.UP;
            return direction.getAxis() != facing.getAxis();
        }

    }
}
