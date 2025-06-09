package com.blockninja.createcoasters.mixin;

import com.blockninja.createcoasters.ContraptionEntityExtraAccess;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;

@Mixin(CarriageContraptionEntity.class)
public class MixinCarriageContraptionEntity implements ContraptionEntityExtraAccess {
    @Unique
    private ArrayList<BlockState> disabledBlocks = new ArrayList<>();

    @Override
    public ArrayList<BlockState> getDisabledBlocks() {
        return disabledBlocks;
    }

    @Override
    public void setDisabledBlocks(ArrayList<BlockState> newBlocks) {
        disabledBlocks = newBlocks;
    }
}
