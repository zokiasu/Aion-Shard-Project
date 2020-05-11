package com.aionemu.gameserver.model.instance.playerreward;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.InstanceBuff;

/**
 * @author Eloann
 */
public class EternalBastionWarPlayerReward extends InstancePlayerReward {

    private InstanceBuff boostMorale;
    private int timeBonus;
    private long logoutTime;
    private float timeBonusModifier;

    public EternalBastionWarPlayerReward(Integer object, int timeBonus, byte buffId) {
        super(object);
        super.addPoints(4000);
        this.timeBonus = timeBonus;
        timeBonusModifier = ((float) this.timeBonus / (float) 660000);
        boostMorale = new InstanceBuff(buffId);
    }

    public float getParticipation() {
        return (float) getTimeBonus() / timeBonus;
    }

    public int getScorePoints() {
        return timeBonus + getPoints();
    }

    public int getTimeBonus() {
        return timeBonus > 0 ? timeBonus : 0;
    }

    public void updateLogOutTime() {
        logoutTime = System.currentTimeMillis();
    }

    public void updateBonusTime() {
        int offlineTime = (int) (System.currentTimeMillis() - logoutTime);
        timeBonus -= offlineTime * timeBonusModifier;
    }

    public boolean hasBoostMorale() {
        return boostMorale.hasInstanceBuff();
    }

    public void applyBoostMoraleEffect(Player player) {
        boostMorale.applyEffect(player, 20000);
    }

    public void endBoostMoraleEffect(Player player) {
        boostMorale.endEffect(player);
    }

    public int getRemaningTime() {
        int time = boostMorale.getRemaningTime();
        if (time >= 0 && time < 20) {
            return 20 - time;
        }
        return 0;
    }
}
