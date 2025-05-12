package com.blockninja.createcoasters.content.create;

import com.blockninja.createcoasters.CreateCoasters;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class EdgePointTypes {
    public static final EdgePointType<GenericTrackBlock> SPEED_BLOCK = register(new ResourceLocation(CreateCoasters.MOD_ID, "speed_block"), GenericTrackBlock::new);

    public static <T extends TrackEdgePoint> EdgePointType<T> register(ResourceLocation id, Supplier<T> factory) {
        EdgePointType<T> type = new EdgePointType<T>(id, factory);
        EdgePointType.TYPES.put(id, type);
        return type;
    }
}
