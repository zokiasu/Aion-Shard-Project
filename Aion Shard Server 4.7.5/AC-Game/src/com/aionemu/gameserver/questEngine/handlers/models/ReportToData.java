package com.aionemu.gameserver.questEngine.handlers.models;

import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.template.ReportTo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author MrPoke
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportToData")
public class ReportToData extends XMLQuest {

    @XmlAttribute(name = "start_npc_ids")
    protected List<Integer> startNpcIds;
    @XmlAttribute(name = "end_npc_ids")
    protected List<Integer> endNpcIds;
    @XmlAttribute(name = "item_id", required = true)
    protected int itemId;

    @Override
    public void register(QuestEngine questEngine) {
        ReportTo template = new ReportTo(id, startNpcIds, endNpcIds, itemId);
        questEngine.addQuestHandler(template);
    }
}
