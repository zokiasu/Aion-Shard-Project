package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.abyssracebonus.AbyssRaceBonus;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Eloann
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"abyssRaceBonus"})
@XmlRootElement(name = "abyss_race_bonuses")
public class AbyssRaceBonusData {

    @XmlElement(name = "abyss_race_bonus")
    protected List<AbyssRaceBonus> abyssRaceBonus;
    @XmlTransient
    private TIntObjectHashMap<AbyssRaceBonus> templates = new TIntObjectHashMap<AbyssRaceBonus>();

    /**
	 * @param u  
     * @param parent 
	 */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (AbyssRaceBonus template : abyssRaceBonus) {
            templates.put(template.getId(), template);
        }
        abyssRaceBonus.clear();
        abyssRaceBonus = null;
    }

    public int size() {
        return templates.size();
    }

    public AbyssRaceBonus getAbyssRaceBonus(int id) {
        return templates.get(id);
    }
}
