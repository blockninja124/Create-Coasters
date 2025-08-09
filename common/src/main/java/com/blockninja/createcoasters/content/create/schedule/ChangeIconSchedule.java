package com.blockninja.createcoasters.content.create.schedule;

import com.blockninja.createcoasters.CreateCoasters;
import com.blockninja.createcoasters.mixin_interfaces.CarriageEntityExtraAccess;
import com.blockninja.createcoasters.network.NetworkHandler;
import com.blockninja.createcoasters.network.packets.SyncDoSoundsPacket;
import com.blockninja.createcoasters.network.packets.SyncIconPacket;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TrainIconType;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.schedule.Schedule;
import com.simibubi.create.content.trains.schedule.destination.ScheduleInstruction;
import com.simibubi.create.content.trains.station.TrainEditPacket;
import com.simibubi.create.foundation.gui.ModularGuiLineBuilder;
import com.simibubi.create.foundation.gui.widget.ScrollInput;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.stream.Collectors;

public class ChangeIconSchedule extends ScheduleInstruction implements IScheduleInstruction {

    private final List<ResourceLocation> iconTypes = TrainIconType.REGISTRY.keySet()
            .stream()
            .toList();


    public ChangeIconSchedule() {
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
        return Pair.of(new ItemStack(AllBlocks.STEAM_WHISTLE.asItem()), Component.translatable(CreateCoasters.MOD_ID+".schedule.do_sounds", TrainIconType.REGISTRY.keySet().stream().toList().get(intData("iconType")).getPath()));
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(CreateCoasters.MOD_ID, "changeiconschedule");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initConfigurationWidgets(ModularGuiLineBuilder builder) {
        //builder.speechBubble();

        List<Component> components = iconTypes.stream()
                .map(resourceLocation -> {
                    return Component.literal(resourceLocation.getPath());
                })
                .collect(Collectors.toList());

        builder.addSelectionScrollInput(20, 101,
                (i, l) -> i.forOptions(components)
                        .titled(Component.translatable(CreateCoasters.MOD_ID+".schedule.sound_mode")),
                "iconType");
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
        /*train.carriages.forEach((carriage) -> {
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
        });*/
        //System.out.println(train.icon.getId());
        train.icon = TrainIconType.byId(TrainIconType.REGISTRY.keySet().stream().toList().get(intData("iconType")));
        //System.out.println(TrainIconType.REGISTRY.keySet().stream().toList().get(intData("iconType")));

        // Current problem: Clients done have updated icons, and when they open station, they get default icon.
        // Packet then sends to set the train back to default icon
        // Also means sounds will act like default icon

        // So:
        // Need to send packet to all clients hmm
        // Perhaps a "send to all tracking server" impl function in NetworkHandler

        // Is this a safe way to get the level? :sus:
        CarriageContraptionEntity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null) return null;
        Level level = entity.level();
        // In theory isn't ever true
        if (!(level instanceof ServerLevel)) return null;

        //System.out.println("Sending packet");

        NetworkHandler.sendRCPacketToAllInLevel(new SyncIconPacket(train.id, train.name.getString(), TrainIconType.REGISTRY.keySet().stream().toList().get(intData("iconType"))), (ServerLevel) level);

        //NetworkHandler.sendRCPacketToTracking(, train.z);
        return null;
    }

    /*@Override
    protected void writeAdditional(CompoundTag tag) {

    }

    @Override
    protected void readAdditional(CompoundTag tag) {

    }*/
}
