package com.blockninja.createcoasters.mixin;

import com.blockninja.createcoasters.ContraptionEntityExtraAccess;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;

@Mixin(CarriageContraptionEntity.class)
public class MixinCarriageContraptionEntity implements ContraptionEntityExtraAccess {
    @Unique
    private ArrayList<DyeColor> disabledColors = new ArrayList<>();

    @Unique
    private boolean doSounds = true;

    @Override
    public ArrayList<DyeColor> getDisabledColors() {
        return disabledColors;
    }

    @Override
    public void setDisabledColors(ArrayList<DyeColor> newBlocks) {
        disabledColors = newBlocks;
    }

    @Override
    public boolean getDoSounds() {
        return doSounds;
    }

    @Override
    public void setDoSounds(boolean doSoundsNew) {
        System.out.println("Set do sounds on:");
        System.out.println(this);
        doSounds = doSoundsNew;
    }
}
