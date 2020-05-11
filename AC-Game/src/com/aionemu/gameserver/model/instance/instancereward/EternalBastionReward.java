package com.aionemu.gameserver.model.instance.instancereward;

import com.aionemu.gameserver.model.instance.playerreward.EternalBastionPlayerReward;

/**
 * @author Romanz
 */
public class EternalBastionReward extends InstanceReward<EternalBastionPlayerReward> {

    private int points;
    private int npcKills;
    private int rank = 7;
    private int basicAP;
    private int powerfulBundlewater;
    private int ceraniumMedal;
    private int powerfulBundleessence;
    private int largeBundlewater;
    private int largeBundleessence;
    private int smallBundlewater;
    private boolean isRewarded = false;

    public EternalBastionReward(Integer mapId, int instanceId) {
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

    public boolean isRewarded() {
        return isRewarded;
    }

    public void setRewarded() {
        isRewarded = true;
    }

    public int getBasicAP() {
        return basicAP;
    }

    public void setBasicAP(int ap) {
        this.basicAP = ap;
    }

    public int getCeramiumMedal() {
        return ceraniumMedal;
    }

    public int getPowerfulBundleWater() {
        return powerfulBundlewater;
    }

    public int getPowerfulBundleEssence() {
        return powerfulBundleessence;
    }

    public int getLargeBundleWater() {
        return largeBundlewater;
    }

    public int getLargeBundleEssence() {
        return largeBundleessence;
    }

    public int getSmallBundleWater() {
        return smallBundlewater;
    }

    public void setCeramiumMedal(int ceraniumMedal) {
        this.ceraniumMedal = ceraniumMedal;
    }

    public void setPowerfulBundleWater(int powerfulBundlewater) {
        this.powerfulBundlewater = powerfulBundlewater;
    }

    public void setPowerfulBundleEssence(int powerfulBundleessence) {
        this.powerfulBundleessence = powerfulBundleessence;
    }

    public void setLargeBundleWater(int largeBundlewater) {
        this.largeBundlewater = largeBundlewater;
    }

    public void setLargeBundleEssence(int largeBundleessence) {
        this.largeBundleessence = largeBundleessence;
    }

    public void setSmallBundleWater(int smallBundlewater) {
        this.smallBundlewater = smallBundlewater;
    }
}
