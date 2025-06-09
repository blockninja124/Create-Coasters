package com.blockninja.createcoasters.content.create.schedule;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.schedule.Schedule;
import org.jetbrains.annotations.Nullable;

public interface IScheduleInstruction {
    @Nullable DiscoveredPath onCalled(Train train, Schedule schedule, String currentTitle, int currentEntry);
}
