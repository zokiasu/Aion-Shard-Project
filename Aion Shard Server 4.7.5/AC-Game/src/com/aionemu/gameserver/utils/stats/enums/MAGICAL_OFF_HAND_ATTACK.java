package com.aionemu.gameserver.utils.stats.enums;

/**
 * @author Ever'
 */
public enum MAGICAL_OFF_HAND_ATTACK {

    WARRIOR(0),
    GLADIATOR(0),
    TEMPLAR(0),
    SCOUT(0),
    ASSASSIN(0),
    RANGER(0),
    MAGE(0),
    SORCERER(0),
    SPIRIT_MASTER(0),
    PRIEST(0),
    CLERIC(0),
    CHANTER(0),
    ENGINEER(0),
    RIDER(19),
    GUNNER(19),
    ARTIST(0),
    BARD(0);
    private int value;

    private MAGICAL_OFF_HAND_ATTACK(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
