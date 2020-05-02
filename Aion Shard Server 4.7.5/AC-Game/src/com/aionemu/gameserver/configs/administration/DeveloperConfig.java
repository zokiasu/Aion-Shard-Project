package com.aionemu.gameserver.configs.administration;

import com.aionemu.commons.configuration.Property;

/**
 * @author ATracer
 * @author GiGatR00n v4.7.5.x
 */
public class DeveloperConfig {

    /**
     * if false - not spawns will be loaded
     */
    @Property(key = "gameserver.developer.spawn.enable", defaultValue = "true")
    public static boolean SPAWN_ENABLE;
    /**
     * if true - checks spawns being outside any known zones
     */
    @Property(key = "gameserver.developer.spawn.check", defaultValue = "false")
    public static boolean SPAWN_CHECK;
    /**
     * if set, adds specified stat bonus for items with random bonusess
     */
    @Property(key = "gameserver.developer.itemstat.id", defaultValue = "0")
    public static int ITEM_STAT_ID;
    /**
     * Show sended cm/sm packets in game server log
     */
    @Property(key = "gameserver.developer.showpackets.enable", defaultValue = "false")
    public static boolean SHOW_PACKETS;
	/**
	* Display Packets Name in Chat Window
	*/
	@Property(key = "gameserver.developer.show.packetnames.inchat.enable", defaultValue = "false")
	public static boolean SHOW_PACKET_NAMES_INCHAT;
	/**
	* Display Packets Hex-Bytes in Chat Window
	*/
	@Property(key = "gameserver.developer.show.packetbytes.inchat.enable", defaultValue = "false")
	public static boolean SHOW_PACKET_BYTES_INCHAT;
	/**
	* How many Packet Bytes should be shown in Chat Window?
	* Default: 200-Hexed bytes
	*/
	@Property(key = "gameserver.developer.show.packetbytes.inchat.total", defaultValue = "200")
	public static int TOTAL_PACKET_BYTES_INCHAT;
	/**
	* Filters which Packets should be shown in Chat Windows?
	* Default: *
	*
	* e.g.  SM_MOVE, CM_CASTSPELL, CM_ATTACK
	*/
	@Property(key = "gameserver.developer.filter.packets.inchat", defaultValue = "*")
	public static String FILTERED_PACKETS_INCHAT;
	/**
	* if Player Access Level is meet, display Packets-Name or Hex-Bytes in Chat Window 
	* 
	* Tip: Player Access-Level higher than or equal to 3 is recommended
	* 
	*   10 - Server-Owner
	*   9 - Server-CoOwner
	*   8 - Root-Admin L3
	*   7 - Root-Admin L2
	*   6 - Root-Admin L1
	*	5 - Admin
	*	4 - Head-GM
	*	3 - GM
	*	2 - Jr.-GM
	*	1 - Supporter
	*	0 - Players
	*/
	@Property(key = "gameserver.developer.show.packets.inchat.accesslevel", defaultValue = "3")
	public static int SHOW_PACKETS_INCHAT_ACCESSLEVEL;	
}
