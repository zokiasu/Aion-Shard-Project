package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class DualBoxConfig {

	/**
	* Active DualBox Restriction
	*/
	@Property(key = "gameserver.dualbox.active", defaultValue = "true")
	public static boolean	DUALBOX_PROTECTION;
	
	/**
	* DualBox Allowed ChaRS
	*/
	@Property(key = "gameserver.dualbox.count", defaultValue = "2")
	public static int	DUALBOX_ALLOWED_CHARS;
	
	/**
	* DualBox White List
	*/
	@Property(key = "gameserver.dualbox.whitelist", defaultValue = "127.0.0.1")
	public static String	DUALBOX_WHITELIST;
	
}
