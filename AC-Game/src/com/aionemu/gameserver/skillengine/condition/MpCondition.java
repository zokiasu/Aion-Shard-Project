package com.aionemu.gameserver.skillengine.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.skillengine.model.Skill;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MpCondition")
public class MpCondition extends Condition {

    @XmlAttribute(required = true)
    protected int value;
    @XmlAttribute
    protected int delta;
    @XmlAttribute
    protected boolean ratio;

    @Override
    public boolean validate(Skill skill) {
        int valueWithDelta = value + delta * skill.getSkillLevel();
        if (ratio) {
            valueWithDelta = (int) ((skill.getEffector().getLifeStats().getMaxMp() * valueWithDelta) / 100);
        }
        return skill.getEffector().getLifeStats().getCurrentMp() > valueWithDelta;
    }
}
