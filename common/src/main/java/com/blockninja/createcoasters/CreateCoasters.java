package com.blockninja.createcoasters;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.blockninja.createcoasters.content.blocks.ModBlocks;

public class CreateCoasters {
    public static final String MOD_ID = "createcoasters";
    public static final String NAME = "Create Coasters";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateCoasters.MOD_ID);

    public static void init() {

        ModBlocks.register(); // hold registrate in a separate class to avoid loading early on forge
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
