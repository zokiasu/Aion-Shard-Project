package com.aionemu.gameserver.model.templates.item;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;

import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author vlog
 * @author GiGatR00n v4.7.5.x
 */
@XmlType(name = "RandomItem")
public class RandomItem {

    @XmlAttribute(name = "type")
    protected RandomType type;
    @XmlAttribute(name = "count")
    protected int count;
    @XmlAttribute(name = "rnd_min")
    public int rndMin;
    @XmlAttribute(name = "rnd_max")
    public int rndMax;
    @XmlAttribute(name = "race")
    public Race race = Race.PC_ALL;
    @XmlAttribute(name = "player_class")
    public PlayerClass playerClass = PlayerClass.ALL;    

    public int getCount() {
        return count;
    }

    public RandomType getType() {
        return type;
    }

    public int getRndMin() {
        return rndMin;
    }

    public int getRndMax() {
        return rndMax;
    }

    public final Race getRace() {
        return race;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }    
    
    public final int getResultCount() {
        if (count == 0 && rndMin == 0 && rndMax == 0) {
            return 1;
        } else if (rndMin > 0 || rndMax > 0) {
            if (rndMax < rndMin) {
                LoggerFactory.getLogger(RandomItem.class).warn("Wrong rnd result item definition {} {}", rndMin, rndMax);
                return 1;
            } else {
                return Rnd.get(rndMin, rndMax);
            }
        } else {
            return count;
        }
    }
}
