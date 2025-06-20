package com.blockninja.createcoasters.content.blocks.entity;

import com.blockninja.createcoasters.content.blocks.ModBlocks;
import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;

import java.util.ArrayList;
import java.util.List;

public class LockBlockEntity extends SmartBlockEntity {
    public final double dx;
    public final double dy;
    public final double dz;

    protected ScrollValueBehaviour horizontalDistance;

    public LockBlockEntity(BlockPos pos, BlockState state, double dx, double dy, double dz) {
        super(ModBlocks.LOCK_BLOCK_ENTITY.get(), pos, state);
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState state, T t) {
        if (Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            CreateClient.OUTLINER.showAABB(t, getSearchBox(blockPos, (LockBlockEntity) t), 1);
        }
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

            AABB searchBox = getSearchBox(blockPos, abe);
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

    public static AABB getSearchBox(BlockPos blockPos, LockBlockEntity abe) {
        return new AABB(blockPos.getCenter().subtract(0.5, 0.5, 0.5).subtract(abe.horizontalDistance.getValue(), 0, abe.horizontalDistance.getValue()), blockPos.getCenter().add(0.5, 0.5, 0.5).add(new Vec3(abe.horizontalDistance.getValue(), abe.dy, abe.horizontalDistance.getValue())));
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

        // Why wont these be UNIQUE RAHH
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
