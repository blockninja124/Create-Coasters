package com.blockninja.createcoasters.mixin;

import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;

@Mixin(AbstractContraptionEntity.class)
public class MixinAbstractContraptionEntity implements ContraptionEntityExtraAccess {
    @Unique
    private ArrayList<DyeColor> disabledColors = new ArrayList<>();
    @Unique
    private int handsUpTicks = 0;

    @Override
    public ArrayList<DyeColor> getDisabledColors() {
        return disabledColors;
    }

    @Override
    public void setDisabledColors(ArrayList<DyeColor> newBlocks) {
        disabledColors = newBlocks;
    }

    @Override
    public int getHandsUpTicks() {
        // This is really sus but its the best place I think
        if (handsUpTicks > 0) {
            handsUpTicks--;
        }
        return handsUpTicks;
    }

    @Override
    public void setHandsUpTicks(int handsUpTicks) {
        this.handsUpTicks = handsUpTicks;
    }
}
