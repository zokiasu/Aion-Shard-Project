package com.aionemu.gameserver.questEngine.handlers.models;

import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.template.KillSpawned;
import javolution.util.FastMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author vlog, modified Bobobear
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KillSpawnedData")
public class KillSpawnedData extends MonsterHuntData {

    @XmlElement(name = "spawned_monster", required = true)
    protected List<SpawnedMonster> spawnedMonster;

    @Override
    public void register(QuestEngine questEngine) {
        FastMap<List<Integer>, SpawnedMonster> spawnedMonsters = new FastMap<List<Integer>, SpawnedMonster>();
        for (SpawnedMonster m : spawnedMonster) {
            spawnedMonsters.put(m.getNpcIds(), m);
        }
        KillSpawned template = new KillSpawned(id, startNpcIds, endNpcIds, spawnedMonsters);
        questEngine.addQuestHandler(template);
    }
}
