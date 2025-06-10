package com.blockninja.createcoasters.content.create.schedule;

import com.blockninja.createcoasters.mixin_interfaces.ContraptionEntityExtraAccess;
import com.blockninja.createcoasters.CreateCoasters;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.schedule.Schedule;
import com.simibubi.create.content.trains.schedule.destination.ScheduleInstruction;
import com.simibubi.create.foundation.gui.ModularGuiLineBuilder;
import com.simibubi.create.foundation.utility.Pair;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LockSeatSchedule extends ScheduleInstruction implements IScheduleInstruction {

    private DyeColor seatColor = DyeColor.WHITE;
    private final List<Component> lockOptions = List.of(Component.translatable(CreateCoasters.MOD_ID+".schedule.lock"), Component.translatable(CreateCoasters.MOD_ID+".schedule.unlock"));

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
            return Pair.of(
                    new ItemStack(AllBlocks.SEATS.get(seatColor).asItem()),
                    // "%mode %color seats" = "Lock White seats"
                    Component.translatable(
                            CreateCoasters.MOD_ID+".schedule.seat",
                            lockOptions.get(intData("Mode")).getString(),
                            seatColor.getName()
                    )
            );
        }
        return Pair.of(
                new ItemStack(Items.AIR.asItem()),
                Component.translatable(
                        CreateCoasters.MOD_ID+".schedule.all_seats",
                        lockOptions.get(intData("Mode")).getString()
                )
        );
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
                        .titled(Component.translatable(CreateCoasters.MOD_ID+".schedule.mode")),
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
        // This is such bad code ohhhh my goodness
        train.carriages.forEach((carriage) -> {
            carriage.forEachPresentEntity((entity) -> {

                ArrayList<DyeColor> list = ((ContraptionEntityExtraAccess) entity).getDisabledColors();

                // If no seat color is specified, do them all
                if (seatColor == null) {
                    // Lock all
                    if (intData("Mode") == 0) {
                        for (BlockEntry<SeatBlock> seatBlockBlockEntry : AllBlocks.SEATS) {
                            Block block = seatBlockBlockEntry.getDefaultState().getBlock();
                            // Probably redundant
                            if (block instanceof SeatBlock seatBlock) {
                                list.add(seatBlock.getColor());
                            }
                        }

                    // Unlock all
                    } else {
                        list = new ArrayList<>();
                    }
                } else {
                    // Lock seat
                    if (intData("Mode") == 0) {
                        if (!list.contains(seatColor)) {
                            list.add(seatColor);
                        }
                    // Other option is "1" and should unlock seat
                    } else {
                        list.remove(seatColor);
                    }

                }

                ((ContraptionEntityExtraAccess) entity).setDisabledColors(list);
            });
        });
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
