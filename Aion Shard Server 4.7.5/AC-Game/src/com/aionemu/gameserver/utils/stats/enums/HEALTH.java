package com.aionemu.gameserver.utils.stats.enums;

/**
 * @author ATracer
 */
public enum HEALTH {

    WARRIOR(110),
    GLADIATOR(115),
    TEMPLAR(100),
    SCOUT(100),
    ASSASSIN(100),
    RANGER(90),
    MAGE(90),
    SORCERER(90),
    SPIRIT_MASTER(90),
    PRIEST(95),
    CLERIC(110),
    CHANTER(105),
    ENGINEER(100),
    RIDER(90),
    GUNNER(100),
    ARTIST(90),
    BARD(95);
    private int value;

    private HEALTH(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
