package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

/**
 * @author GiGatR00n
 */
public class ReturnScrollsConfig {

    /**
	* Teleport Animation
	* 
	* 0 = NO ANIMATION
	* 1 = BEAM ANIMATION
	* 2 = CIRCLE JUMP AIMATION
	* 3 = JUMP AIMATION 2
	* 4 = JUMP AIMATION 3
     */
    @Property(key = "gameserver.teleport.animation", defaultValue = "0")
    public static int TELEPORT_ANIMATION;
}
