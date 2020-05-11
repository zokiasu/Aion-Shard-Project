package com.aionemu.gameserver.model;

/**
 * @author zdead
 */
public enum PetitionStatus {

    PENDING(0),
    IN_PROGRESS(1),
    REPLIED(2);
    private int element;

    private PetitionStatus(int id) {
        this.element = id;
    }

    public int getElementId() {
        return element;
    }
}
