package com.aionemu.gameserver.questEngine.handlers.models;

import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.template.ItemCollecting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author MrPoke
 * @modified Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemCollectingData")
public class ItemCollectingData extends XMLQuest {

    @XmlAttribute(name = "start_npc_ids", required = true)
    protected List<Integer> startNpcIds;
    @XmlAttribute(name = "action_item_ids")
    protected List<Integer> actionItemIds;
    @XmlAttribute(name = "end_npc_ids")
    protected List<Integer> endNpcIds;
    @XmlAttribute(name = "next_npc_id", required = true)
    protected int nextNpcId;
    @XmlAttribute(name = "HACTION_QUEST_SELECT_id")
    protected int startDialogId;
    @XmlAttribute(name = "HACTION_QUEST_SELECT_id2")
    protected int startDialogId2;
    @XmlAttribute(name = "item_id")
    protected int itemId;

    @Override
    public void register(QuestEngine questEngine) {
        ItemCollecting template = new ItemCollecting(id, startNpcIds, nextNpcId, actionItemIds, endNpcIds, questMovie, startDialogId, startDialogId2, itemId);
        questEngine.addQuestHandler(template);
    }
}
