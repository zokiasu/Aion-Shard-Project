package com.aionemu.gameserver.questEngine.handlers.models;

import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.template.ItemOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Bobobear
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemOrdersData")
public class ItemOrdersData extends XMLQuest {

    @XmlAttribute(name = "start_item_id", required = true)
    protected int startItemId;
    @XmlAttribute(name = "talk_npc_id1")
    protected int talkNpc1;
    @XmlAttribute(name = "talk_npc_id2")
    protected int talkNpc2;
    @XmlAttribute(name = "end_npc_id", required = true)
    protected int endNpcId;

    @Override
    public void register(QuestEngine questEngine) {
        ItemOrders template = new ItemOrders(id, startItemId, talkNpc1, talkNpc2, endNpcId);
        questEngine.addQuestHandler(template);
    }
}
