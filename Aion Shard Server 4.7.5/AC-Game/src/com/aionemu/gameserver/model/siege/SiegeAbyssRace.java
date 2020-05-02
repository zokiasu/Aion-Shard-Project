package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.playerreward.InstancePlayerReward;

/**
 * @author Eloann
 */
public class SiegeAbyssRace extends InstancePlayerReward {

    private AbyssRaceBuff boost;

    public SiegeAbyssRace(Integer object, byte buffId) {
        super(object);
        boost = new AbyssRaceBuff(buffId);
    }

    public boolean hasBoost() {
        return boost.hasAbyssRaceBuff();
    }

    public void applyBoostEffect(Player player) {
        boost.applyEffect(player);
    }

    public void endBoostEffect(Player player) {
        boost.endEffect(player);
    }
}
