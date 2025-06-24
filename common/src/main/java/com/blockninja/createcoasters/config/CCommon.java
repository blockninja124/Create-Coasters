package com.blockninja.createcoasters.config;

import com.blockninja.createcoasters.CreateCoasters;
import com.simibubi.create.foundation.config.ConfigBase;

@SuppressWarnings("unused")
public class CCommon extends ConfigBase {

    public final ConfigBool doesNothing = b(false, "doesNothing", Comments.doesNothing);

    @Override
    public String getName() {
        return CreateCoasters.MOD_ID.concat("_common");
    }

    private static class Comments {
        static String doesNothing = "Placeholder";

    }
}
