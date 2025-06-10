package com.blockninja.createcoasters.content.blocks.entity;

import com.blockninja.createcoasters.content.blocks.ModBlocks;
import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.jozufozu.flywheel.fabric.mixin.DebugScreenOverlayMixin;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class LockBlockEntity extends BlockEntity {

    public LockBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.LOCK_BLOCK_ENTITY.get(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState state, T t) {

    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T be) {
        if (be instanceof LockBlockEntity abe) {

            ArrayList<DyeColor> allColors = new ArrayList<>();
            for (BlockEntry<SeatBlock> seatBlockBlockEntry : AllBlocks.SEATS) {
                Block block = seatBlockBlockEntry.getDefaultState().getBlock();
                // Probably redundant
                if (block instanceof SeatBlock seatBlock) {
                    allColors.add(seatBlock.getColor());
                }
            }


            BlockState aboveBlock = level.getBlockState(blockPos.above());

            int redstone = level.getBestNeighborSignal(blockPos);

            AABB searchBox = new AABB(blockPos.getCenter(), blockPos.getCenter().add(new Vec3(0, 4, 0)));
            List<AbstractContraptionEntity> entities = level.getEntitiesOfClass(AbstractContraptionEntity.class, searchBox);

            for (AbstractContraptionEntity entity : entities) {

                // Use block above as color
                if (aboveBlock.getBlock() instanceof SeatBlock seatBlock) {
                    ArrayList<DyeColor> currentColors = ((ContraptionEntityExtraAccess) entity).getDisabledColors();
                    // Lock color
                    if (redstone > 0) {
                        if (!(currentColors.contains(seatBlock.getColor()))) {
                            currentColors.add(seatBlock.getColor());
                        }
                    // Unlock color
                    } else {
                        currentColors.remove(seatBlock.getColor());
                    }
                    ((ContraptionEntityExtraAccess) entity).setDisabledColors(currentColors);

                // Lock / Unlock all seats
                } else {
                    // Lock all
                    if (redstone > 0) {
                        ((ContraptionEntityExtraAccess) entity).setDisabledColors(allColors);
                    // Unlock all
                    } else {
                        ((ContraptionEntityExtraAccess) entity).setDisabledColors(new ArrayList<>());
                    }
                }
            }
        }
    }

    /*@Override
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

    }*/
}
