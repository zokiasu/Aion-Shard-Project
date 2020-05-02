package com.aionemu.gameserver.model.instance.playerreward;

import com.aionemu.gameserver.model.autogroup.AGPlayer;

import java.util.List;

/**
 * @author xTz
 */
public class HarmonyGroupReward extends PvPArenaPlayerReward {

    private List<AGPlayer> players;

    public HarmonyGroupReward(Integer object, int timeBonus, byte buffId, List<AGPlayer> players) {
        super(object, timeBonus, buffId);
        this.players = players;
    }

    public List<AGPlayer> getAGPlayers() {
        return players;
    }

    public boolean containPlayer(Integer object) {
        for (AGPlayer agp : players) {
            if (agp.getObjectId().equals(object)) {
                return true;
            }
        }
        return false;
    }

    public AGPlayer getAGPlayer(Integer object) {
        for (AGPlayer agp : players) {
            if (agp.getObjectId().equals(object)) {
                return agp;
            }
        }
        return null;
    }
}
