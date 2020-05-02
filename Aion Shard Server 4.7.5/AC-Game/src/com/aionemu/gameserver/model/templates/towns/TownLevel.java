package com.aionemu.gameserver.model.templates.towns;

import com.aionemu.gameserver.model.templates.spawns.Spawn;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author ViAl
 */
@XmlType(name = "town_level")
public class TownLevel {

    @XmlAttribute(name = "level")
    protected int level;
    @XmlElement(name = "spawn")
    protected List<Spawn> spawns;

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the spawn
     */
    public List<Spawn> getSpawns() {
        return spawns;
    }
}
