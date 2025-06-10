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

    @Override
    public ArrayList<DyeColor> getDisabledColors() {
        return disabledColors;
    }

    @Override
    public void setDisabledColors(ArrayList<DyeColor> newBlocks) {
        disabledColors = newBlocks;
    }
}
