package com.aionemu.gameserver.skillengine.model;

/**
 * @author MrPoke
 */
public enum SkillMoveType {

    RESIST(0),
    DEFAULT(16),
    PULL(18),
    STUMBLE(20),
    KNOCKBACK(28),
    STAGGER(112),
    UNK(54);
    private int id;

    private SkillMoveType(int id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
}
