package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.templates.serial_killer.RankRestriction;

import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

import java.util.List;

/**
 * @author Dtem
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "rankRestriction"
})
@XmlRootElement(name = "serial_killers")
public class SerialKillerData {

    @XmlElement(name = "rank_restriction")
    protected List<RankRestriction> rankRestriction;
    @XmlTransient
    private TIntObjectHashMap<RankRestriction> templates = new TIntObjectHashMap<RankRestriction>();

    /**
	 * @param u 
     * @param parent  
	 */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (RankRestriction template : rankRestriction) {
            templates.put(template.getRankNum(), template);
        }
        rankRestriction.clear();
        rankRestriction = null;
    }

    public int size() {
        return templates.size();
    }

    public RankRestriction getRankRestriction(int rank, Race race) {
    	for (int i = 0; i < this.templates.size(); i++) {
    		RankRestriction rr = (RankRestriction) this.templates.get(i);
    		if (rr.getRankNum() != rank && rr.getRace() != race) {
    			continue;
    		} else {
    			return rr;
    		}
        }
		return null;
    }
}
