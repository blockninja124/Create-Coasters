package com.blockninja.createcoasters.mixin;

import com.blockninja.createcoasters.mixin_interfaces.CarriageEntityExtraAccess;
import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;

@Mixin(CarriageContraptionEntity.class)
public class MixinCarriageContraptionEntity implements CarriageEntityExtraAccess {

    @Unique
    private boolean doSounds = true;

    @Override
    public boolean getDoSounds() {
        return doSounds;
    }

    @Override
    public void setDoSounds(boolean doSoundsNew) {
        //System.out.println("Set do sounds on:");
        //System.out.println(this);
        doSounds = doSoundsNew;
    }
}
