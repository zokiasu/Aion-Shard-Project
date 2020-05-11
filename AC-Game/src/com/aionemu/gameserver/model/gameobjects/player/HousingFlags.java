package com.aionemu.gameserver.model.gameobjects.player;

/**
 * @author Rolandas
 */
public enum HousingFlags {

    IS_OWNER(1 << 0),
    HAS_OWNER(1 << 0),
    BUY_STUDIO_ALLOWED(1 << 1),
    SINGLE_HOUSE(1 << 1),
    BIDDING_ALLOWED(1 << 2),
    HOUSE_OWNER((IS_OWNER.getId() | BIDDING_ALLOWED.getId()) & ~BUY_STUDIO_ALLOWED.getId()),
    SELLING_HOUSE(IS_OWNER.getId() | BUY_STUDIO_ALLOWED.getId()),
    // Player status
    SOLD_HOUSE(BIDDING_ALLOWED.getId() | BUY_STUDIO_ALLOWED.getId());
    private byte id;

    private HousingFlags(int id) {
        id = (byte) (id & 0xFF);
    }

    public byte getId() {
        return id;
    }
}
