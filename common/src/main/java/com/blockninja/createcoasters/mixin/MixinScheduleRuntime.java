package com.blockninja.createcoasters.mixin;


import com.blockninja.createcoasters.content.create.schedule.IScheduleInstruction;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.schedule.Schedule;
import com.simibubi.create.content.trains.schedule.ScheduleEntry;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ScheduleRuntime.class)
public class MixinScheduleRuntime {

    @Shadow(remap = false)
    Train train;

    @Shadow(remap = false)
    Schedule schedule;

    @Shadow(remap = false)
    String currentTitle;

    @Shadow(remap = false)
    public int currentEntry;


    // Your existing logic to populate subEntries
    @Inject(method = "startCurrentInstruction", at = @At("HEAD"), remap = false, cancellable = true)
    private void onStartCurrentInstruction(CallbackInfoReturnable<DiscoveredPath> cir) {
        ScheduleEntry currentScheduleEntry = schedule.entries.get(currentEntry);
        if (currentScheduleEntry.instruction instanceof IScheduleInstruction customScheduleInstruction) {
            cir.setReturnValue(customScheduleInstruction.onCalled(train, schedule, currentTitle, currentEntry));
            currentEntry = currentEntry + 1;
            cir.cancel();
        }

    }
}
