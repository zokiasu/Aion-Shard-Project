package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class StarterPackConfig {

    /**
     * Starter Pack
     */
    @Property(key = "gameserver.starter.pack.enabled", defaultValue = "false")
    public static boolean STARTER_ENABLED;

    @Property(key = "gameserver.starter.gladiator.pack", defaultValue = "141000001")
    public static String STARTER_GLADIATOR;

    @Property(key = "gameserver.starter.templar.pack", defaultValue = "141000001")
    public static String STARTER_TEMPLAR;

    @Property(key = "gameserver.starter.assassin.pack", defaultValue = "141000001")
    public static String STARTER_ASSASSIN;

    @Property(key = "gameserver.starter.ranger.pack", defaultValue = "141000001")
    public static String STARTER_RANGER;

    @Property(key = "gameserver.starter.gunner.pack", defaultValue = "141000001")
    public static String STARTER_GUNNER;

    @Property(key = "gameserver.starter.rider.pack", defaultValue = "141000001")
    public static String STARTER_RIDER;

    @Property(key = "gameserver.starter.bard.pack", defaultValue = "141000001")
    public static String STARTER_BARD;

    @Property(key = "gameserver.starter.sorcerer.pack", defaultValue = "141000001")
    public static String STARTER_SORCERER;

    @Property(key = "gameserver.starter.spiritmaster.pack", defaultValue = "141000001")
    public static String STARTER_SPIRITMASTER;

    @Property(key = "gameserver.starter.cleric.pack", defaultValue = "141000001")
    public static String STARTER_CLERIC;

    @Property(key = "gameserver.starter.chanter.pack", defaultValue = "141000001")
    public static String STARTER_CHANTER;

    @Property(key = "gameserver.starter.kinah", defaultValue = "1000000")
    public static long STARTER_KINAH;

    @Property(key = "gameserver.starter.other.item", defaultValue = "141000001")
    public static String STARTER_OTHER_ITEM;
}
