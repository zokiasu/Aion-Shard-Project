package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class EnchantsConfig {

    /**
     * Supplement Additional Rates
     */
    @Property(key = "gameserver.supplement.lesser", defaultValue = "1.0")
    public static float LESSER_SUP;
    @Property(key = "gameserver.supplement.regular", defaultValue = "1.0")
    public static float REGULAR_SUP;
    @Property(key = "gameserver.supplement.greater", defaultValue = "1.0")
    public static float GREATER_SUP;
    @Property(key = "gameserver.supplement.mythic", defaultValue = "1.0")
    public static float MYTHIC_SUP;
    /**
     * Max enchant level
     */
    @Property(key = "gameserver.enchant.type1", defaultValue = "10")
    public static int ENCHANT_MAX_LEVEL_TYPE1;
    @Property(key = "gameserver.enchant.type2", defaultValue = "15")
    public static int ENCHANT_MAX_LEVEL_TYPE2;
    /**
     * ManaStone Rates
     */
    @Property(key = "gameserver.base.manastone", defaultValue = "25")
    public static float MANA_STONE;
    @Property(key = "gameserver.base.enchant", defaultValue = "30")
    public static float ENCHANT_STONE;
    @Property(key = "gameserver.manastone.clean", defaultValue = "false")
    public static boolean CLEAN_STONE;
    @Property(key = "gameserver.manastone.enchant_cast_delay", defaultValue = "2000")
    public static int ENCHANT_CAST_DELAY;    
}
