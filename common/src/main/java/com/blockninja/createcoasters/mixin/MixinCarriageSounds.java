package com.blockninja.createcoasters.mixin;

import com.blockninja.createcoasters.ContraptionEntityExtraAccess;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSounds;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CarriageSounds.class)
public class MixinCarriageSounds {
    @Shadow
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
        if (((ContraptionEntityExtraAccess) entity).getDoSounds() || !(instance.equals(AllSoundEvents.STEAM))) {
            instance.playAt(world, pos, volume, pitch, fade); // Only call it if allowed
        }
    }
}
