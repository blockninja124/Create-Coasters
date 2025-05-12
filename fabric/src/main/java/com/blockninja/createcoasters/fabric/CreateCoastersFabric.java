package com.blockninja.createcoasters.fabric;


import com.blockninja.createcoasters.CreateCoasters;
import net.fabricmc.api.ModInitializer;

public class CreateCoastersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CreateCoasters.init();



        // on fabric, Registrates must be explicitly finalized and registered.
        ExampleBlocks.REGISTRATE.register();
    }
}
