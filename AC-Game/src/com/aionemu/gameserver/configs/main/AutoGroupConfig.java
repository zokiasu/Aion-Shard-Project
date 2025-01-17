package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

/**
 * @author xTz
 * @author GiGatR00n v4.7.5.x
 */
public class AutoGroupConfig {

    /**
     * Dredgion
     */
    @Property(key = "gameserver.autogroup.enable", defaultValue = "true")
    public static boolean AUTO_GROUP_ENABLE;
    @Property(key = "gameserver.startTime.enable", defaultValue = "true")
    public static boolean START_TIME_ENABLE;
    @Property(key = "gameserver.dredgion.timer", defaultValue = "120")
    public static long DREDGION_TIMER;
    @Property(key = "gameserver.dredgion2.enable", defaultValue = "true")
    public static boolean DREDGION2_ENABLE;
    @Property(key = "gameserver.dredgion.time", defaultValue = "0 0 0,12,20 ? * *")
    public static String DREDGION_TIMES;
    /**
     * Kamar Battlefield v4.0 (PvPvE - 12v12)
     */
    @Property(key = "gameserver.kamar.timer", defaultValue = "120")
    public static long KAMAR_TIMER;
    @Property(key = "gameserver.kamar.enable", defaultValue = "true")
    public static boolean KAMAR_ENABLE;
    @Property(key = "gameserver.kamar.time", defaultValue = "0 0 0,21 ? * *")
    public static String KAMAR_TIMES;
    /**
     * Engulfed Ophidan Bridge v4.5 (PvPvE - 6v6)
     */
    @Property(key = "gameserver.ophidan.timer", defaultValue = "120")
    public static long OPHIDAN_TIMER;
    @Property(key = "gameserver.ophidan.enable", defaultValue = "true")
    public static boolean OPHIDAN_ENABLE;
    @Property(key = "gameserver.ophidan.time", defaultValue = "0 0 0,20 ? * *")
    public static String OPHIDAN_TIMES;
    /**
     * Iron Wall WarFront v4.5 (PvPvE - 24v24)
     */
    @Property(key = "gameserver.ironwall.timer", defaultValue = "120")
    public static long IRONWALL_TIMER;
    @Property(key = "gameserver.ironwall.enable", defaultValue = "true")
    public static boolean IRONWALL_ENABLE;
    @Property(key = "gameserver.ironwall.time", defaultValue = "0 0 0,19 ? * *")
    public static String IRONWALL_TIMES;
    /**
     * Idgel Dome v4.7 (PvPvE - 6v6)
     */
    @Property(key = "gameserver.idgeldome.enable", defaultValue = "true")
    public static boolean IDGELDOME_ENABLE;
    @Property(key = "gameserver.idgeldome.timer", defaultValue = "60")
    public static long IDGELDOME_TIMER;
    @Property(key = "gameserver.idgeldome.time", defaultValue = "0 0 23 1/1 * ? *")
    public static String IDGELDOME_TIMES;    
}
