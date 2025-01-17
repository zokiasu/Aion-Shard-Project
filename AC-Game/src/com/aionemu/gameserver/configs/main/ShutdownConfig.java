package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

/**
 * @author lord_rex
 * @rework Blackfire
 */
public class ShutdownConfig {

	/**
     * Specifies the server restart frequency.
     */
	@Property(key = "gameserver.shutdown.frequency", defaultValue = "NEVER")
     public static String GAMESERVER_SHUTDOWN_FREQUENCY;
        
    /**
     * Specifies the exact time of day the server should be restarted (of course respecting the frequency)
     */
	 @Property(key = "gameserver.shutdown.time", defaultValue = "5:00")
    public static String GAMESERVER_SHUTDOWN_TIME;
	
	/**
     * AutoShutdown Hook Mode for automatic system.
     */
    @Property(key = "gameserver.shutdownauto.mode", defaultValue = "1")
    public static int HOOK_MODE_AUTO;
    /**
     * AutoShutdown Hook delay for automatic system.
     */
    @Property(key = "gameserver.shutdownauto.delay", defaultValue = "60")
    public static int HOOK_DELAY_AUTO;
    /**
     * AutoShutdown announce interval for automatic system.
     */
    @Property(key = "gameserver.shutdownauto.interval", defaultValue = "1")
    public static int ANNOUNCE_INTERVAL_AUTO;

    /**
     * Shutdown Hook Mode.
     */
    @Property(key = "gameserver.shutdown.mode", defaultValue = "1")
    public static int HOOK_MODE;
    /**
     * Shutdown Hook delay.
     */
    @Property(key = "gameserver.shutdown.delay", defaultValue = "60")
    public static int HOOK_DELAY;
    /**
     * Shutdown announce interval.
     */
    @Property(key = "gameserver.shutdown.interval", defaultValue = "1")
    public static int ANNOUNCE_INTERVAL;
    /**
     * Safe reboot mode.
     */
    @Property(key = "gameserver.shutdown.safereboot", defaultValue = "true")
    public static boolean SAFE_REBOOT;
}
