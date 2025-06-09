package com.blockninja.createcoasters;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.schedule.Schedule;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Pair;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.blockninja.createcoasters.content.blocks.ModBlocks;
import com.blockninja.createcoasters.content.create.schedule.LockSeatSchedule;

public class CreateCoasters {
    public static final String MOD_ID = "createcoasters";
    public static final String NAME = "Create Coasters";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateCoasters.MOD_ID);

    public static void init() {

        //Register schedule
        Schedule.INSTRUCTION_TYPES.add(Pair.of(
                new ResourceLocation(MOD_ID, "lockseatschedule"),
                LockSeatSchedule::new
        ));

        ModBlocks.register(); // hold registrate in a separate class to avoid loading early on forge
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
