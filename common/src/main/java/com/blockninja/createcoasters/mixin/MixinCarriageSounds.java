package com.blockninja.createcoasters.mixin;

import com.blockninja.createcoasters.content.ModSounds;
import com.blockninja.createcoasters.content.create.ModTrainIcons;
import com.blockninja.createcoasters.mixin_interfaces.CarriageEntityExtraAccess;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSounds;
import com.simibubi.create.content.trains.entity.TrainIconType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CarriageSounds.class)
public class MixinCarriageSounds {
    @Shadow(remap = false)
    CarriageContraptionEntity entity;

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/AllSoundEvents$SoundEntry;playAt(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;FFZ)V"
            ),
            remap = false
    )
    private void redirectPlayAt(AllSoundEvents.SoundEntry instance, Level world, Vec3 pos, float volume, float pitch, boolean fade) {
        if (((CarriageEntityExtraAccess) entity).getDoSounds() || !(instance.equals(AllSoundEvents.STEAM))) {
            instance.playAt(world, pos, volume, pitch, fade); // Only call it if allowed
        }
    }

    @WrapOperation(method = {
            "tick",
            "submitSharedSoundVolume"
    }, at = @At(value = "INVOKE", target = "Lcom/simibubi/create/AllSoundEvents$SoundEntry;getMainEvent()Lnet/minecraft/sounds/SoundEvent;"))
    private SoundEvent useCogRumble(AllSoundEvents.SoundEntry instance, Operation<SoundEvent> original) {
        System.out.println(entity.getCarriage().train.icon);
        /*// Bogey wheel rumble
        if (instance == AllSoundEvents.TRAIN)
            return ModSounds.WOODEN_TRAIN.get();
        if (instance == AllSoundEvents.TRAIN2)
            return ModSounds.WOODEN_TRAIN3.get();
        // Bogey wheel rumble muffled
        if (instance == AllSoundEvents.TRAIN3)
            return ModSounds.WOODEN_TRAIN3.get();*/
        if (!(instance == AllSoundEvents.TRAIN))
            return ModSounds.WOODEN_TRAIN.get();
        return original.call(instance);
    }


}
