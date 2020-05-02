package com.aionemu.gameserver.model.autogroup;

import com.aionemu.gameserver.model.gameobjects.player.Player;

import java.util.Collection;
import java.util.List;

import static ch.lambdaj.Lambda.extract;
import static ch.lambdaj.Lambda.on;

/**
 * @author xTz
 * @author GiGatR00n v4.7.5.x
 */
public class SearchInstance {

    private long registrationTime = System.currentTimeMillis();
    private int instanceMaskId;
    private EntryRequestType ert;
    private List<Integer> members;

    public SearchInstance(int instanceMaskId, EntryRequestType ert, Collection<Player> members) {
        this.instanceMaskId = instanceMaskId;
        this.ert = ert;
        if (members != null) {
            this.members = extract(members, on(Player.class).getObjectId());
        }
    }

    public List<Integer> getMembers() {
        return members;
    }

    public int getInstanceMaskId() {
        return instanceMaskId;
    }

    public int getRemainingTime() {
        return (int) (System.currentTimeMillis() - registrationTime) / 1000 * 256;
    }

    public EntryRequestType getEntryRequestType() {
        return ert;
    }

    public boolean isDredgion() {
        return instanceMaskId == 1 || instanceMaskId == 2 || instanceMaskId == 3;
    }

    public boolean isKamar() {
        return instanceMaskId == 107;
    }

    public boolean isOphidan() {
        return instanceMaskId == 108;
    }

    public boolean isBastion() {
        return instanceMaskId == 109;
    }
    
    public boolean isIdgelDome() {
        return instanceMaskId == 111;
    }    
}
