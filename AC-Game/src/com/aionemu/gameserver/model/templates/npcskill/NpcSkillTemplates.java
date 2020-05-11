package com.aionemu.gameserver.model.templates.npcskill;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author AionChs Master
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "npcskills")
public class NpcSkillTemplates {

    @XmlAttribute(name = "npcid")
    protected int npcId;
    @XmlElement(name = "npcskill")
    protected List<NpcSkillTemplate> npcSkills;

    public int getNpcId() {
        return npcId;
    }

    public List<NpcSkillTemplate> getNpcSkills() {
        return npcSkills;
    }
}
