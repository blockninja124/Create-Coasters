package com.blockninja.createcoasters.content.create.schedule;

import com.blockninja.createcoasters.mixin_interfaces.CarriageEntityExtraAccess;
import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.blockninja.createcoasters.CreateCoasters;
import com.blockninja.createcoasters.network.NetworkHandler;
import com.blockninja.createcoasters.network.packets.SyncDoSoundsPacket;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.schedule.Schedule;
import com.simibubi.create.content.trains.schedule.destination.ScheduleInstruction;
import com.simibubi.create.foundation.gui.ModularGuiLineBuilder;
import com.simibubi.create.foundation.utility.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DoSoundsSchedule extends ScheduleInstruction implements IScheduleInstruction {

    private final List<Component> soundOptions = List.of(Component.translatable(CreateCoasters.MOD_ID+".schedule.sound_on"), Component.translatable(CreateCoasters.MOD_ID+".schedule.sound_off"));

    public DoSoundsSchedule() {
        // Create a test schedule with basic instructions
        //testSchedule.addInstruction(new WaitInstruction(200)); // Wait for 200 ticks
        //testSchedule.addInstruction(new GoToStationInstruction("TestStation"));
    }


    @Override
    public boolean supportsConditions() {
        return false;
    }

    @Override
    public Pair<ItemStack, Component> getSummary() {
        return Pair.of(new ItemStack(AllBlocks.STEAM_WHISTLE.asItem()), Component.translatable(CreateCoasters.MOD_ID+".schedule.do_sounds", soundOptions.get(intData("Mode")).getString()));
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(CreateCoasters.MOD_ID, "dosoundsschedule");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initConfigurationWidgets(ModularGuiLineBuilder builder) {
        //builder.speechBubble();

        builder.addSelectionScrollInput(20, 101,
                (i, l) -> i.forOptions(soundOptions)
                        .titled(Component.translatable(CreateCoasters.MOD_ID+".schedule.sound_mode")),
                "Mode");
    }


    /*@Override
    public int slotsTargeted() {
        return 1;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        Item item = stack.getItem();

        for (DyeColor color : DyeColor.values()) {
            BlockEntry<SeatBlock> seatEntry = AllBlocks.SEATS.get(color);
            if (seatEntry.asItem() == item) {
                seatColor = color;
                return;
            }
        }

        seatColor = null;
    }

    @Override
    public ItemStack getItem(int slot) {
        if (seatColor == null) return new ItemStack(Items.AIR.asItem());
        return AllBlocks.SEATS.get(seatColor).asStack();
    }*/


    @Override
    public DiscoveredPath onCalled(Train train, Schedule schedule, String currentTitle, int currentEntry) {
        // This is such bad code ohhhh my goodness
        train.carriages.forEach((carriage) -> {
            carriage.forEachPresentEntity((entity) -> {

                boolean oldDoSounds = ((CarriageEntityExtraAccess) entity).getDoSounds();

                boolean doSounds = true;

                // Sound off
                if (intData("Mode") == 1) {
                    doSounds = false;
                }

                if (oldDoSounds != doSounds) {
                    NetworkHandler.sendRCPacketToTracking(new SyncDoSoundsPacket(entity.getId(), doSounds), entity);
                    ((CarriageEntityExtraAccess) entity).setDoSounds(doSounds);
                }


            });
        });
        return null;
    }

    /*@Override
    protected void writeAdditional(CompoundTag tag) {
        if (seatColor != null) {
            tag.putInt("seatColor", seatColor.getId());
        } else {
            tag.putInt("seatColor", -1);
        }
    }

    @Override
    protected void readAdditional(CompoundTag tag) {
        int color = tag.getInt("seatColor");
        if (color == -1) {
            this.seatColor = null;
        } else {
            this.seatColor = DyeColor.byId(color);
        }
    }*/
}
