package com.blockninja.createcoasters.mixin.forge;

import com.blockninja.createcoasters.ContraptionEntityExtraAccess;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "stopRiding", at = @At("HEAD"), cancellable = true)
    private void onStopRiding(CallbackInfo ci) {
        Entity self = (Entity) (Object) this;

        // Only apply to players
        if (!(self instanceof Player player)) return;

        Entity vehicle = player.getVehicle();
        if (vehicle instanceof CarriageContraptionEntity carriageContraptionEntity) {
            System.out.println("Carriage");
            if (carriageContraptionEntity instanceof ContraptionEntityExtraAccess) {
                Contraption contraption = carriageContraptionEntity.getContraption();
                BlockPos seatPos = contraption.getSeatOf(player.getUUID());

                // LocalPlayer's will be null
                if (seatPos == null) return;

                BlockState state = contraption.getActorAt(seatPos).getLeft().state();
                if (((ContraptionEntityExtraAccess) vehicle).getDisabledBlocks().contains(state)) {
                    System.out.println("Cancelling dismount in stopRiding");
                    ci.cancel(); // Cancel the dismount
                }
                System.out.println("Disabled blocks:");
                System.out.println(((ContraptionEntityExtraAccess) vehicle).getDisabledBlocks());
            }
        }
        /*if (vehicle != null && vehicle.getClass().getName().contains("CarriageContraptionEntity")) {

        }*/
    }
}

