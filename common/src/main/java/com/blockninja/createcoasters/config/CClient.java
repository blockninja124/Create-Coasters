package com.blockninja.createcoasters.config;

import com.blockninja.createcoasters.CreateCoasters;
import com.simibubi.create.foundation.config.ConfigBase;

@SuppressWarnings("unused")
public class CClient extends ConfigBase {

    public final ConfigBool enableZoom = b(true, "enableZoom", Comments.enableZoom);
    public final ConfigInt zoomMod = i(8, 1, 20, "zoomMod", Comments.zoomMod);
    public final ConfigFloat zoomLerpMod = f(0.75F, 0, 1, "zoomLerpMod", Comments.zoomLerpMod);

    @Override
    public String getName() {
        return CreateCoasters.MOD_ID.concat("_client");
    }

    private static class Comments {
        static String enableZoom = "Whether the camera will zoom out slightly when on high speed trains. " +
                "If you are sensitive to motion sickness, you may want to disable this.";
        static String zoomMod = "The value that the train speed is divided by for fov zoom out. Higher values mean less zoom out";
        static String zoomLerpMod = "The modifier used to smooth out the camera zoom out affect on trains. 0.1 will be a very slow transition, 1 will be instant.";
    }
}
