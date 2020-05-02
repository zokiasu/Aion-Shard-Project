package com.aionemu.gameserver.services.transfers;

/**
 * @author Eloann - Enomine
 */
public class FastTrack {

    private int serverId;
    private int iconSet;
    private int minlevel, maxlevel;

    public FastTrack(int serverId, boolean sendIcon, int minLevel, int maxLevel) {
        this.serverId = serverId;
        this.iconSet = sendIcon ? 257 : 513;
        this.minlevel = minLevel;
        this.maxlevel = maxLevel;
    }

    public int getServerId() {
        return serverId;
    }

    public int getIconSet() {
        return iconSet;
    }

    public int getMinLevel() {
        return minlevel;
    }

    public int getMaxLevel() {
        return maxlevel;
    }
}
