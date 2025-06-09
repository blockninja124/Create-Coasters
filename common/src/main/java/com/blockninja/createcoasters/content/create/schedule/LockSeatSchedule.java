package com.blockninja.createcoasters.content.create.schedule;

import com.blockninja.createcoasters.ContraptionEntityExtraAccess;
import com.blockninja.createcoasters.CreateCoasters;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSyncData;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.schedule.Schedule;
import com.simibubi.create.content.trains.schedule.ScheduleItem;
import com.simibubi.create.content.trains.schedule.destination.ScheduleInstruction;
import com.simibubi.create.foundation.gui.ModularGuiLineBuilder;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Pair;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LockSeatSchedule extends ScheduleInstruction implements IScheduleInstruction {

    private DyeColor seatColor = DyeColor.WHITE;
    private final List<Component> lockOptions = List.of(Component.literal("Lock"), Component.literal("Unlock"));

    public LockSeatSchedule() {
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
        if (seatColor != null) {
            return Pair.of(new ItemStack(AllBlocks.SEATS.get(seatColor).asItem()), Component.literal("Lock "+seatColor.getName()));
        }
        return Pair.of(new ItemStack(Items.AIR.asItem()), Component.literal("Unlock all"));
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(CreateCoasters.MOD_ID, "lockseatschedule");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initConfigurationWidgets(ModularGuiLineBuilder builder) {
        //builder.speechBubble();

        builder.addSelectionScrollInput(20, 101,
                (i, l) -> i.forOptions(lockOptions)
                        .titled(Component.literal("Mode")),
                "Mode");
    }


    @Override
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
    }


    @Override
    public DiscoveredPath onCalled(Train train, Schedule schedule, String currentTitle, int currentEntry) {
        System.out.println("Scroll data:");
        System.out.println(intData("Mode"));
        // This is such bad code ohhhh my goodness
        train.carriages.forEach((carriage) -> {
            carriage.forEachPresentEntity((entity) -> {

                // TODO: remove this redundant for loop
                entity.getContraption().getSeats().forEach((blockPos) -> {

                    System.out.println(seatColor);

                    BlockState state = entity.getContraption().getActorAt(blockPos).getLeft().state();

                    ArrayList<BlockState> list = ((ContraptionEntityExtraAccess) entity).getDisabledBlocks();

                    if (seatColor == null) {
                        list = new ArrayList<>();
                    } else {
                        if (state.getBlock() instanceof SeatBlock seatBlock) {
                            if (seatBlock.getColor() == seatColor) {
                                if (!list.contains(state)) {
                                    list.add(state);
                                }
                            }
                        }
                    }

                    ((ContraptionEntityExtraAccess) entity).setDisabledBlocks(list);
                });

                entity.getSelfAndPassengers().forEach((seatedEntity) -> {

                    System.out.println(seatedEntity);
                });
            });
        });
        System.out.println("Foo on train: ");
        System.out.println(train);
        return null;
    }

    @Override
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
    }
}
