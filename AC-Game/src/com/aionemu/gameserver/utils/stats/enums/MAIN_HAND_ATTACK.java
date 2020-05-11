package com.aionemu.gameserver.utils.stats.enums;

/**
 * @author ATracer
 */
public enum MAIN_HAND_ATTACK {

    WARRIOR(19),
    GLADIATOR(19),
    TEMPLAR(19),
    SCOUT(18),
    ASSASSIN(19),
    RANGER(18),
    MAGE(16),
    SORCERER(16),
    SPIRIT_MASTER(16),
    PRIEST(17),
    CLERIC(19),
    CHANTER(19),
    ENGINEER(18),
    RIDER(19),
    GUNNER(18),
    ARTIST(16),
    BARD(16);
    private int value;

    private MAIN_HAND_ATTACK(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
