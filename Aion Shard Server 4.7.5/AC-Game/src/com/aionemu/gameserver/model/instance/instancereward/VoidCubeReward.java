package com.aionemu.gameserver.model.instance.instancereward;

import com.aionemu.gameserver.model.instance.playerreward.VoidCubePlayerReward;

/**
 * @author Eloann
 */
public class VoidCubeReward extends InstanceReward<VoidCubePlayerReward> {

    private int points;
    private int npcKills;
    private int rank = 7;
    private int scoreAP;
    private int ceramium;
    private int sillus;
    private int favorable;
    private boolean isRewarded = false;

    public VoidCubeReward(Integer mapId, int instanceId) {
        super(mapId, instanceId);
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public void addNpcKill() {
        npcKills++;
    }

    public int getNpcKills() {
        return npcKills;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public int getScoreAP() {
        return scoreAP;
    }

    public void setScoreAP(int ap) {
        this.scoreAP = ap;
    }

    public boolean isRewarded() {
        return isRewarded;
    }

    public void setRewarded() {
        isRewarded = true;
    }

    public int getCeramium() {
        return ceramium;
    }

    public void setCeramium(int ceramium) {
        this.ceramium = ceramium;
    }

    public int getSillus() {
        return sillus;
    }

    public void setSillus(int sillus) {
        this.sillus = sillus;
    }

    public int getFavorable() {
        return favorable;
    }

    public void setFavorable(int favorable) {
        this.favorable = favorable;
    }
}
