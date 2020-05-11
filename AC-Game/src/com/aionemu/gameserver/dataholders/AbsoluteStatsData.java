package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.stats.AbsoluteStatsTemplate;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"absoluteStats"})
@XmlRootElement(name = "absolute_stats")
public class AbsoluteStatsData {

    @XmlElement(name = "stats_set", required = true)
    protected List<AbsoluteStatsTemplate> absoluteStats;
    @XmlTransient
    private TIntObjectHashMap<ModifiersTemplate> absoluteStatsData = new TIntObjectHashMap<ModifiersTemplate>();

    /**
	 * @param u  
     * @param parent 
	 */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (AbsoluteStatsTemplate stats : absoluteStats) {
            absoluteStatsData.put(stats.getId(), stats.getModifiers());
        }
        absoluteStats.clear();
        absoluteStats = null;
    }

    public ModifiersTemplate getTemplate(int statSetId) {
        return absoluteStatsData.get(statSetId);
    }

    public int size() {
        return absoluteStatsData.size();
    }
}
