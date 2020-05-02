package com.aionemu.gameserver.model.gameobjects;

/**
 * @author Ever'
 */
public enum HousingAction {

    UNK(-1),
    ENTER_DECORATION(1),
    EXIT_DECORATION(2),
    ADD_ITEM(3),
    DELETE_ITEM(4),
    SPAWN_OBJECT(5),
    MOVE_OBJECT(6),
    DESPAWN_OBJECT(7),
    ENTER_RENOVATION(14),
    EXIT_RENOVATION(15),
    CHANGE_APPEARANCE(16);
    private int id;

    private HousingAction(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return id;
    }

    public static HousingAction getActionTypeById(int id) {
        for (HousingAction actionType : values()) {
            if (actionType.getTypeId() == id) {
                return actionType;
            }
        }
        return UNK;
    }
}
