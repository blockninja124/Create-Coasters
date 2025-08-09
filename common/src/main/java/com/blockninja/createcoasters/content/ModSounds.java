package com.blockninja.createcoasters.content;

import com.blockninja.createcoasters.CreateCoasters;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final RegistryEntry<SoundEvent> WOODEN_TRAIN = CreateCoasters.REGISTRATE
            .simple(
                    "wooden_train",
                    Registries.SOUND_EVENT,
                    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreateCoasters.MOD_ID, "wooden_train"))
            );

    public static final RegistryEntry<SoundEvent> WOODEN_TRAIN3 = CreateCoasters.REGISTRATE
            .simple(
                    "wooden_train_3",
                    Registries.SOUND_EVENT,
                    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreateCoasters.MOD_ID, "wooden_train_3"))
            );

    public static final RegistryEntry<SoundEvent> EMPTY = CreateCoasters.REGISTRATE
            .simple(
                    "empty",
                    Registries.SOUND_EVENT,
                    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreateCoasters.MOD_ID, "empty"))
            );

    public static final RegistryEntry<SoundEvent> METAL_TRAIN = CreateCoasters.REGISTRATE
            .simple(
                    "metal_train",
                    Registries.SOUND_EVENT,
                    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreateCoasters.MOD_ID, "metal_train"))
            );

    public static void register() {
    }
}
