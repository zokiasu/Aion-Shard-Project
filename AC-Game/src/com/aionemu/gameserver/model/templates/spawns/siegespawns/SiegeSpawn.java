package com.aionemu.gameserver.model.templates.spawns.siegespawns;

import com.aionemu.gameserver.model.siege.SiegeModType;
import com.aionemu.gameserver.model.siege.SiegeRace;
import com.aionemu.gameserver.model.templates.spawns.Spawn;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiegeSpawn")
public class SiegeSpawn {

    @XmlElement(name = "siege_race")
    private List<SiegeRaceTemplate> siegeRaceTemplates;
    @XmlAttribute(name = "siege_id")
    private int siegeId;

    public int getSiegeId() {
        return siegeId;
    }

    public List<SiegeRaceTemplate> getSiegeRaceTemplates() {
        return siegeRaceTemplates;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "SiegeRaceTemplate")
    public static class SiegeRaceTemplate {

        @XmlElement(name = "siege_mod")
        private List<SiegeModTemplate> SiegeModTemplates;
        @XmlAttribute(name = "race")
        private SiegeRace race;

        public SiegeRace getSiegeRace() {
            return race;
        }

        public List<SiegeModTemplate> getSiegeModTemplates() {
            return SiegeModTemplates;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "SiegeModTemplate")
        public static class SiegeModTemplate {

            @XmlElement(name = "spawn")
            private List<Spawn> spawns;
            @XmlAttribute(name = "mod")
            private SiegeModType siegeMod;

            public List<Spawn> getSpawns() {
                return spawns;
            }

            public SiegeModType getSiegeModType() {
                return siegeMod;
            }
        }
    }
}
