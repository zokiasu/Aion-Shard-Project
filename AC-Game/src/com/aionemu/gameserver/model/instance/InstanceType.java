package com.aionemu.gameserver.model.instance;

/**
 * @author Ever'
 */
public enum InstanceType {

    NORMAL,
    BATTLEFIELD;

    public boolean isNormalInstance() {
        return this.equals(InstanceType.NORMAL);
    }

    public boolean isBattlefieldInstance() {
        return this.equals(InstanceType.BATTLEFIELD);
    }
}
