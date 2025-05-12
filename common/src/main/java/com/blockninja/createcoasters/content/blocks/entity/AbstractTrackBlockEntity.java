package com.blockninja.createcoasters.content.blocks.entity;

import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class AbstractTrackBlockEntity<T extends TrackEdgePoint> extends SmartBlockEntity {
    public TrackTargetingBehaviour<T> edgePoint;

    public AbstractTrackBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(edgePoint = new TrackTargetingBehaviour<>(this, getEdgePointType()));
    }

    public abstract EdgePointType<T> getEdgePointType();

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, T be) {
        if (be instanceof AbstractTrackBlockEntity abe) {
            abe.clientTick(level, blockPos, blockState);
        }
    }

    public void clientTick(Level level, BlockPos blockPos, BlockState blockState) {};

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T be) {
        if (be instanceof AbstractTrackBlockEntity abe) {

            AABB searchBox = new AABB(abe.edgePoint.getGlobalPosition().getCenter(), abe.edgePoint.getGlobalPosition().getCenter().add(new Vec3(0, 1, 0)));

            List<CarriageContraptionEntity> entities = level.getEntitiesOfClass(CarriageContraptionEntity.class, searchBox);
            for (CarriageContraptionEntity entity : entities) {
                //entity.getCarriage().train.approachTargetSpeed(1);
                abe.onTrain(entity.getCarriage().train, level, blockPos);
                //entity.getCarriage().train.throttle = ((2 * 10 * 1.0D) - (double) sbe.maxSpeed.getValue()) / 2 / 10;
            }
        }

    }

    public void onTrain(Train train, Level level, BlockPos pos) {};


}

