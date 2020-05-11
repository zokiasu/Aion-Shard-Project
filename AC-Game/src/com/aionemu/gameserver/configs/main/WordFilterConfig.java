package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

/**
 * @author Dr.Nism
 */
public class WordFilterConfig {

    @Property(key = "gameserver.wordfilter.enabled", defaultValue = "false")
    public static boolean WORDFILTER_ENABLED;
    @Property(key = "gameserver.wordfilter.limittime", defaultValue = "3")
    public static int WORDFILTER_LIMITTIME;
    @Property(key = "gameserver.wordfilter.enabled.kick", defaultValue = "false")
    public static boolean WORDFILTER_ENABLED_KICK;
    @Property(key = "gameserver.wordfilter.enabled.ban", defaultValue = "false")
    public static boolean WORDFILTER_ENABLED_BAN;
    @Property(key = "gameserver.wordfilter.bantime", defaultValue = "3")
    public static int WORDFILTER_BANTIME;
}
