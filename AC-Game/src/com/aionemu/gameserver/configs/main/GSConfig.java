package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

import java.util.Calendar;

public class GSConfig {

    /**
     * Gameserver
     */

    /* Server Country Code */
    @Property(key = "gameserver.country.code", defaultValue = "1")
    public static int SERVER_COUNTRY_CODE;
    /* Server Credits Name */
    @Property(key = "gameserver.name", defaultValue = "Aion Genesis")
    public static String SERVER_NAME;
    /* Server Credits Name */
    @Property(key = "loginserver.database.name", defaultValue = "al_login_ls")
    public static String LOGINSERVER_NAME;
    /* Server Version */
    @Property(key = "gameserver.version", defaultValue = "4.7")
    public static String SERVER_VERSION;
    /* Players Max Level */
    @Property(key = "gameserver.players.max.level", defaultValue = "65")
    public static int PLAYER_MAX_LEVEL;
    @Property(key = "gameserver.lang", defaultValue = "en")
    public static String LANG;
    /* Time Zone name (used for events & timed spawns) */
    @Property(key = "gameserver.timezone", defaultValue = "")
    public static String TIME_ZONE_ID = Calendar.getInstance().getTimeZone().getID();
    /* Enable connection with CS (ChatServer) */
    @Property(key = "gameserver.chatserver.enable", defaultValue = "true")
    public static boolean ENABLE_CHAT_SERVER;
    @Property(key = "gameserver.chatserver.port", defaultValue = "10241")
    public static int CHATSERVER_PORT;
    /* Server Language */
    @Property(key = "gameserver.language", defaultValue = "en")
    public static String SERVER_LANGUAGE;
    /* Server MOTD Display revision */
    @Property(key = "gameserver.revisiondisplay.enable", defaultValue = "false")
    public static boolean SERVER_MOTD_DISPLAYREV;
    /**
     * Character creation
     */
    @Property(key = "gameserver.character.creation.mode", defaultValue = "0")
    public static int CHARACTER_CREATION_MODE;
    /**
     * Server Mode
     */
    @Property(key = "gameserver.character.limit.count", defaultValue = "8")
    public static int CHARACTER_LIMIT_COUNT;
    @Property(key = "gameserver.character.faction.limitation.mode", defaultValue = "0")
    public static int CHARACTER_FACTION_LIMITATION_MODE;
    @Property(key = "gameserver.ratio.limitation.enable", defaultValue = "false")
    public static boolean ENABLE_RATIO_LIMITATION;
    @Property(key = "gameserver.ratio.min.value", defaultValue = "60")
    public static int RATIO_MIN_VALUE;
    @Property(key = "gameserver.ratio.min.required.level", defaultValue = "10")
    public static int RATIO_MIN_REQUIRED_LEVEL;
    @Property(key = "gameserver.ratio.min.characters_count", defaultValue = "50")
    public static int RATIO_MIN_CHARACTERS_COUNT;
    @Property(key = "gameserver.ratio.high_player_count.disabling", defaultValue = "500")
    public static int RATIO_HIGH_PLAYER_COUNT_DISABLING;
    /**
     * Misc
     */
    @Property(key = "gameserver.abyssranking.small.cache", defaultValue = "false")
    public static boolean ABYSSRANKING_SMALL_CACHE;
    @Property(key = "gameserver.character.reentry.time", defaultValue = "20")
    public static int CHARACTER_REENTRY_TIME;

    /**
     * Player Starting Level
     */
    @Property(key = "gameserver.starting.level", defaultValue = "1")
    public static int STARTING_LEVEL;

    @Property(key = "gameserver.startClass.maxLevel", defaultValue = "66")
    public static int STARTCLASS_MAXLEVEL;

    /**
     * Memory Optimization Configs
     */
    @Property(key = "gameserver.gc.enable", defaultValue = "true")
    public static boolean ENABLE_MEMORY_GC;

    @Property(key = "gameserver.gc.optimization.time", defaultValue = "5")
    public static int GC_OPTIMIZATION_TIME;
}
