package com.blockninja.createcoasters.mixin;

import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("RETURN"))
    private void injectHandsUp(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (!(livingEntity instanceof Player player)) return;

        if (player.getVehicle() instanceof AbstractContraptionEntity abstractContraptionEntity) {

            int handsUpTicks = ((ContraptionEntityExtraAccess) abstractContraptionEntity).getHandsUpTicks();
            if (handsUpTicks > 0) {
                HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;

                model.rightArm.xRot = (float) ((float) -Math.PI);
                model.rightArm.zRot = -0.15F;

                model.leftArm.xRot = (float) ((float) -Math.PI);
                model.leftArm.zRot = 0.15F;

            }
        }
    }
}
