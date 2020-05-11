package com.aionemu.gameserver.questEngine.handlers.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author vlog
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpawnedMonster")
public class SpawnedMonster extends Monster {

    @XmlAttribute(name = "spawner_object", required = true)
    protected int spawnerObject;

    public int getSpawnerObject() {
        return spawnerObject;
    }
}
