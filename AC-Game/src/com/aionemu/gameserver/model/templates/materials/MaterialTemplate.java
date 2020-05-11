package com.aionemu.gameserver.model.templates.materials;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaterialTemplate", propOrder = {"skills"})
public class MaterialTemplate {

    @XmlElement(name = "skill", required = true)
    protected List<MaterialSkill> skills;
    @XmlAttribute(name = "skill_obstacle")
    protected Integer skillObstacle;
    @XmlAttribute(required = true)
    protected int id;

    public List<MaterialSkill> getSkills() {
        return skills;
    }

    public Integer getSkillObstacle() {
        return skillObstacle;
    }

    public int getId() {
        return id;
    }
}
