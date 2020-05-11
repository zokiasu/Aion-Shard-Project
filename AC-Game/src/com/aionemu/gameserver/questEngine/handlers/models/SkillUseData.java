package com.aionemu.gameserver.questEngine.handlers.models;

import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.template.SkillUse;
import javolution.util.FastMap;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author vlog, modified Bobobear
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillUseData")
public class SkillUseData extends XMLQuest {

    @XmlAttribute(name = "start_npc_id")
    protected int startNpc;
    @XmlAttribute(name = "end_npc_id")
    protected int endNpc;
    @XmlElement(name = "skill", required = true)
    protected List<QuestSkillData> skills;

    @Override
    public void register(QuestEngine questEngine) {
        FastMap<List<Integer>, QuestSkillData> questSkills = new FastMap<List<Integer>, QuestSkillData>();
        for (QuestSkillData qsd : skills) {
            questSkills.put(qsd.getSkillIds(), qsd);
        }
        SkillUse questTemplate = new SkillUse(id, startNpc, endNpc, questSkills);
        questEngine.addQuestHandler(questTemplate);
    }
}
