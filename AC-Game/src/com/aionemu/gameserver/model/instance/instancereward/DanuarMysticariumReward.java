package com.aionemu.gameserver.model.instance.instancereward;

import com.aionemu.gameserver.model.instance.playerreward.DanuarPlayerReward;

/**
 * @author Eloann
 */
public class DanuarMysticariumReward extends InstanceReward<DanuarPlayerReward> {

    private int points;
    private int npcKills;
    private int rank = 7;
    private int basicAP;
    private int sillusCrest;
    private int ceraniumMedal;
    private int favorableBundle;
    private int ceraniumFragments;
    private int valorBundle;
    private int mithrilMedal;
    private boolean isRewarded = false;

    public DanuarMysticariumReward(Integer mapId, int instanceId) {
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

    public int getSillusCrest() {
        return sillusCrest;
    }

    public int getCeramiumMedal() {
        return ceraniumMedal;
    }

    public int getFavorableBundle() {
        return favorableBundle;
    }

    public int getCeramiumFragments() {
        return ceraniumFragments;
    }

    public int getValorBundle() {
        return valorBundle;
    }

    public int getMithrilMedal() {
        return mithrilMedal;
    }

    public void setSillusCrest(int sillusCrest) {
        this.sillusCrest = sillusCrest;
    }

    public void setCeramiumMedal(int ceraniumMedal) {
        this.ceraniumMedal = ceraniumMedal;
    }

    public void setFavorableBundle(int favorableBundle) {
        this.favorableBundle = favorableBundle;
    }

    public void setCeramiumFragments(int ceraniumFragments) {
        this.ceraniumFragments = ceraniumFragments;
    }

    public void setValorBundle(int valorBundle) {
        this.valorBundle = valorBundle;
    }

    public void setMithrilMedal(int mithrilMedal) {
        this.mithrilMedal = mithrilMedal;
    }
}
