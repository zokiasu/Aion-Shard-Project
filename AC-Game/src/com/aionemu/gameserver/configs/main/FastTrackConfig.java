package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

/**
 * @author -Enomine-
 * @rework Eloann
 */
public class FastTrackConfig {

    @Property(key = "fast_track.server_id", defaultValue = "2")
    public static int FASTTRACK_SERVER_ID;
    @Property(key = "fast_track.max_level", defaultValue = "55")
    public static int FASTTRACK_MAX_LEVEL;
    @Property(key = "fast_track.enable", defaultValue = "true")
    public static boolean FASTTRACK_ENABLE;
}
