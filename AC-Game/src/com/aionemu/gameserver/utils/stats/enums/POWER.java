package com.aionemu.gameserver.utils.stats.enums;

/**
 * @author ATracer
 */
public enum POWER {

    WARRIOR(110),
    GLADIATOR(115),
    TEMPLAR(115),
    SCOUT(100),
    ASSASSIN(110),
    RANGER(90),
    MAGE(90),
    SORCERER(90),
    SPIRIT_MASTER(90),
    PRIEST(95),
    CLERIC(105),
    CHANTER(110),
    ENGINEER(100),
    RIDER(115),
    GUNNER(100),
    ARTIST(95),
    BARD(95);
    private int value;

    private POWER(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
