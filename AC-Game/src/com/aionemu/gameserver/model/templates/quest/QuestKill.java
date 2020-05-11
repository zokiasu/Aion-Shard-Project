package com.aionemu.gameserver.model.templates.quest;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestKill")
public class QuestKill {

    @XmlAttribute(name = "seq")
    private int seq;
    @XmlAttribute(name = "npc_ids")
    private List<Integer> npcIds;
    @XmlTransient
    private Set<Integer> npcIdSet;

    /**
     * @return the seq
     */
    public int getSequenceNumber() {
        return seq;
    }

    /**
     * @return the npcIds
     */
    public Set<Integer> getNpcIds() {
        if (npcIdSet == null) {
            npcIdSet = new HashSet<Integer>();
        }
        if (npcIds != null) {
            npcIdSet.addAll(npcIds);
            npcIds.clear();
            npcIds = null;
        }
        return npcIdSet;
    }
}
