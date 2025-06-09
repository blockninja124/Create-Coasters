package com.blockninja.createcoasters;

import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;

public interface ContraptionEntityExtraAccess {
    ArrayList<DyeColor> getDisabledColors();
    void setDisabledColors(ArrayList<DyeColor> newBlocks);
}
