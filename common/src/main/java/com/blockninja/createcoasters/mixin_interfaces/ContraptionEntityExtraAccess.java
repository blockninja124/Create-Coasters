package com.blockninja.createcoasters.mixin_interfaces;

import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;

public interface ContraptionEntityExtraAccess {
    ArrayList<DyeColor> getDisabledColors();
    void setDisabledColors(ArrayList<DyeColor> newBlocks);
    int getHandsUpTicks();
    void setHandsUpTicks(int handsUpTicks);
}
