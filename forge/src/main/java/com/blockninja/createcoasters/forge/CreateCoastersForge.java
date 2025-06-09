package com.blockninja.createcoasters.forge;

import com.blockninja.createcoasters.CreateCoasters;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateCoasters.MOD_ID)
public class CreateCoastersForge {
    public CreateCoastersForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CreateCoasters.init();

        CreateCoasters.REGISTRATE.registerEventListeners(eventBus);
    }


}
