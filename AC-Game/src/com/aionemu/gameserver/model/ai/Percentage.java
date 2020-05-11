package com.aionemu.gameserver.model.ai;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Percentage")
public class Percentage {

    @XmlAttribute(name = "percent")
    protected int percent;
    @XmlAttribute(name = "skillId")
    protected int skillId = 0;
    @XmlAttribute(name = "isIndividual")
    protected boolean isIndividual = false;
    @XmlElement(name = "summonGroup")
    protected List<SummonGroup> summons;

    public List<SummonGroup> getSummons() {
        return summons;
    }

    public int getPercent() {
        return percent;
    }

    public int getSkillId() {
        return skillId;
    }

    public boolean isIndividual() {
        return isIndividual;
    }
}
