package com.blockninja.createcoasters.mixin;

import com.blockninja.createcoasters.ContraptionEntityExtraAccess;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSounds;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
