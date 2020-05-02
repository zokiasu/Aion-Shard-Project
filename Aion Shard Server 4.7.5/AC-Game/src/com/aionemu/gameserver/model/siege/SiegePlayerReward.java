package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;

/**
 * @author Eloann
 */
public class SiegePlayerReward extends InstanceReward<SiegeAbyssRace> {

    private final byte buffId;

    public SiegePlayerReward(Integer mapId) {
        super(mapId, 0);
        buffId = 12;
    }

    public final boolean isKatalam() {
        return mapId == 600050000;
    }

    public final boolean isDanaria() {
        return mapId == 600060000;
    }

    public void regPlayerReward(Integer object) {
        if (!containPlayer(object)) {
            addPlayerReward(new SiegeAbyssRace(object, buffId));
        }
    }

    @Override
    public void addPlayerReward(SiegeAbyssRace reward) {
        super.addPlayerReward(reward);
    }

    @Override
    public SiegeAbyssRace getPlayerReward(Integer object) {
        return (SiegeAbyssRace) super.getPlayerReward(object);
    }

    public boolean canRewarded() {
        return mapId == 600050000 || mapId == 600060000;
    }

    public byte getBuffId() {
        return buffId;
    }

    @Override
    public void clear() {
        super.clear();
    }
}
