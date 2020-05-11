package com.aionemu.gameserver.utils.stats.enums;

/**
 * @author ATracer
 */
public enum KNOWLEDGE {

    WARRIOR(90),
    GLADIATOR(90),
    TEMPLAR(90),
    SCOUT(90),
    ASSASSIN(90),
    RANGER(120),
    MAGE(115),
    SORCERER(120),
    SPIRIT_MASTER(115),
    PRIEST(100),
    CLERIC(105),
    CHANTER(105),
    ENGINEER(100),
    RIDER(105),
    GUNNER(100),
    ARTIST(115),
    BARD(115);
    private int value;

    private KNOWLEDGE(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
