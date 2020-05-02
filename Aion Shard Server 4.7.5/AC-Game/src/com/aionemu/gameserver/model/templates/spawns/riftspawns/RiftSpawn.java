package com.aionemu.gameserver.model.templates.spawns.riftspawns;

import com.aionemu.gameserver.model.templates.spawns.Spawn;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Source
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiftSpawn")
public class RiftSpawn {

    @XmlAttribute(name = "id")
    private int id;
    @XmlAttribute(name = "world")
    private int world;
    @XmlElement(name = "spawn")
    private List<Spawn> spawns = new ArrayList<Spawn>();

    public int getId() {
        return id;
    }

    public int getWorldId() {
        return world;
    }

    public List<Spawn> getSpawns() {
        return spawns;
    }
}
