package com.blockninja.createcoasters.fabric;

import com.blockninja.createcoasters.CreateCoasters;
import com.blockninja.createcoasters.config.CreateCoastersConfigs;
import com.simibubi.create.foundation.config.ConfigBase;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Map;

public class FabricConfigImpl {
    public static void register() {
        CreateCoastersConfigs.registerCommon();

        for (Map.Entry<ModConfig.Type, ConfigBase> pair : CreateCoastersConfigs.CONFIGS.entrySet())
            ForgeConfigRegistry.INSTANCE.register(CreateCoasters.MOD_ID, pair.getKey(), pair.getValue().specification);

        ModConfigEvents.loading(CreateCoasters.MOD_ID).register(CreateCoastersConfigs::onLoad);
        ModConfigEvents.reloading(CreateCoasters.MOD_ID).register(CreateCoastersConfigs::onReload);
    }
}
