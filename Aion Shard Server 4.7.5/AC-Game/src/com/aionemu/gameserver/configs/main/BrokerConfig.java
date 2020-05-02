package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

/**
 * @author GiGatR00n v4.7.5.x
 */
public class BrokerConfig {

    /**
     * Time in seconds for saving broker data into the DB
     * Default....: 6-seconds
     * Standard...: 8-seconds
     * Pro........: 10-seconds
     */
    @Property(key = "gameserver.broker.savemanager.interval", defaultValue = "6")
    public static int SAVEMANAGER_INTERVAL;
    /**
     * Time in seconds for checking broker expired items and sending back to user's mail box
     * Default....: 60-seconds
     * Standard...: 35-seconds
     * Pro........: 20-seconds
     */
    @Property(key = "gameserver.broker.time.checkexpireditems.interval", defaultValue = "60")
    public static int CHECK_EXPIREDITEMS_INTERVAL;
    /**
     * Punishment
	 * 0 - add log record
	 * 1 - log and kick player from game
     */
    @Property(key = "gameserver.broker.antihack.punishment", defaultValue = "0")
    public static int ANTIHACK_PUNISHMENT;
    /**
     * How many days should pass for broker items to mark as expired items
     * Default....: 8-days
     */
    @Property(key = "gameserver.broker.items.expiretime", defaultValue = "8")
    public static int ITEMS_EXPIRETIME;
}
