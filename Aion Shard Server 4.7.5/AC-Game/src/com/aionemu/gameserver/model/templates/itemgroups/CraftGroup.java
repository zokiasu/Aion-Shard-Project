package com.aionemu.gameserver.model.templates.itemgroups;

import com.aionemu.gameserver.model.templates.rewards.CraftReward;
import javolution.util.FastMap;
import org.apache.commons.lang.math.IntRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author Rolandas
 */
public abstract class CraftGroup extends BonusItemGroup {

    private FastMap<Integer, FastMap<IntRange, List<CraftReward>>> dataHolder;

    public ItemRaceEntry[] getRewards(Integer skillId) {
        if (!dataHolder.containsKey(skillId)) {
            return new ItemRaceEntry[0];
        }
        List<ItemRaceEntry> result = new ArrayList<ItemRaceEntry>();
        for (List<CraftReward> items : dataHolder.get(skillId).values()) {
            result.addAll(items);
        }
        return result.toArray(new ItemRaceEntry[0]);
    }

    public ItemRaceEntry[] getRewards(Integer skillId, Integer skillPoints) {
        if (!dataHolder.containsKey(skillId)) {
            return new ItemRaceEntry[0];
        }
        List<ItemRaceEntry> result = new ArrayList<ItemRaceEntry>();
        for (Entry<IntRange, List<CraftReward>> entry : dataHolder.get(skillId).entrySet()) {
            if (!entry.getKey().containsInteger(skillPoints)) {
                continue;
            }
            result.addAll(entry.getValue());
        }
        return result.toArray(new ItemRaceEntry[0]);
    }

    /**
     * @return the dataHolder
     */
    public FastMap<Integer, FastMap<IntRange, List<CraftReward>>> getDataHolder() {
        return dataHolder;
    }

    /**
     * @param dataHolder the dataHolder to set
     */
    public void setDataHolder(FastMap<Integer, FastMap<IntRange, List<CraftReward>>> dataHolder) {
        this.dataHolder = dataHolder;
    }
}
