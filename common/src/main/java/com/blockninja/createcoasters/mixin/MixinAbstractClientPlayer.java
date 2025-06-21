package com.blockninja.createcoasters.mixin;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class MixinAbstractClientPlayer {
    @Unique
    public float lastFOV;

    @Inject(method = "getFieldOfViewModifier", at = @At("RETURN"), cancellable = true)
    private void getFOVMod(CallbackInfoReturnable<Float> cir) {
        AbstractClientPlayer self = (AbstractClientPlayer) (Object) this;
        float currentFOV = cir.getReturnValue();
        if (self.getVehicle() instanceof CarriageContraptionEntity carriageContraptionEntity) {
            //System.out.println(carriageContraptionEntity.getCarriage().train.speed);
            /*AbstractContraptionEntity.ContraptionRotationState rotState = carriageContraptionEntity.getRotationState();

            carriageContraptionEntity.getViewVector(1.0f);

            Vec3 forward = new Vec3(carriageContraptionEntity.yaw, carriageContraptionEntity.pitch, 0);


            float pitch = ((ICarriageBogeyAccessor) carriageContraptionEntity.getCarriage().leadingBogey()).getPitch().getValue(); // up/down
            float yaw = ((ICarriageBogeyAccessor) carriageContraptionEntity.getCarriage().leadingBogey()).getYaw().getValue();   // left/right

            float pitchRad = (float) Math.toRadians(pitch);
            float yawRad = (float) Math.toRadians(yaw);

            double x = -Math.sin(yawRad) * Math.cos(pitchRad);
            double y = -Math.sin(pitchRad);
            double z =  Math.cos(yawRad) * Math.cos(pitchRad);

            Vec3 entityLook = new Vec3(x, y, z).normalize();

            double angleDiff = self.getLookAngle().dot(entityLook); // angular distance*/

            double dx = carriageContraptionEntity.getX() - carriageContraptionEntity.xOld;
            double dy = carriageContraptionEntity.getY() - carriageContraptionEntity.yOld;
            double dz = carriageContraptionEntity.getZ() - carriageContraptionEntity.zOld;

            float budget_speed = (float) Math.sqrt(dx * dx + dy * dy + dz * dz); // per tick

            float newFov = currentFOV * (1+(Math.abs(budget_speed/5)));
            newFov = (float) Mth.lerp(0.75, this.lastFOV, newFov);
            cir.setReturnValue(newFov);

            this.lastFOV = newFov;
        }
    }
}
