package com.aionemu.gameserver.controllers.attack;

/**
 * @author Ever'
 */
public enum CounterSkillStatus {

    BLOCK(32),
    PARRY(64),
    DODGE(128),
    RESIST(256);
    private final int type;

    private CounterSkillStatus(int type) {
        this.type = type;
    }

    public final int getId() {
        return type;
    }
}
