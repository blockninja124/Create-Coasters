package com.blockninja.createcoasters.content.create;

import com.blockninja.createcoasters.CreateCoasters;
import com.simibubi.create.content.trains.entity.TrainIconType;
import net.minecraft.resources.ResourceLocation;

public class ModTrainIcons {
    public static void register() {
        ResourceLocation sheet = new ResourceLocation(CreateCoasters.MOD_ID, "textures/gui/train_icons.png");
        TrainIconType.register(new ResourceLocation(CreateCoasters.MOD_ID, "wooden"), sheet, 2, 1);
    }
}
