package com.blockninja.createcoasters;

import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public interface ContraptionEntityExtraAccess {
    ArrayList<BlockState> getDisabledBlocks();
    void setDisabledBlocks(ArrayList<BlockState> newBlocks);
}
