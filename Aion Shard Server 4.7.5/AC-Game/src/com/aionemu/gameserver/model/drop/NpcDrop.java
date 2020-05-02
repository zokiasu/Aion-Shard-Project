package com.aionemu.gameserver.model.drop;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author MrPoke
 */
public class NpcDrop implements DropCalculator {

    protected List<DropGroup> dropGroup;
    protected int npcId;

    /**
     * @param dropGroup
     * @param npcId
     */
    public NpcDrop(List<DropGroup> dropGroup, int npcId) {
        super();
        this.dropGroup = dropGroup;
        this.npcId = npcId;
    }

    public List<DropGroup> getDropGroup() {
        if (dropGroup == null) {
            return Collections.emptyList();
        }
        return this.dropGroup;
    }

    /**
     * Gets the value of the npcId property.
     */
    public int getNpcId() {
        return npcId;
    }

    @Override
    public int dropCalculator(Set<DropItem> result, int index, float dropModifier, Race race, Collection<Player> groupMembers) {
        if (dropGroup == null || dropGroup.isEmpty()) {
            return index;
        }
        for (DropGroup dg : dropGroup) {
            if (dg.getRace() == Race.PC_ALL || dg.getRace() == race) {
                index = dg.dropCalculator(result, index, dropModifier, race, groupMembers);
            }
        }
        return index;
    }
}
