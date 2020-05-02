package com.aionemu.gameserver.model.templates;

import com.aionemu.gameserver.model.templates.expand.Expand;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Simple
 */
@XmlRootElement(name = "cube_npc")
@XmlAccessorType(XmlAccessType.FIELD)
public class CubeExpandTemplate {

    @XmlElement(name = "expand", required = true)
    protected List<Expand> cubeExpands;
    @XmlAttribute(name = "id", required = true)
    private int Id;

    public int getNpcId() {
        return Id;
    }

    public boolean contains(int level) {
        for (Expand expand : cubeExpands) {
            if (expand.getLevel() == level) {
                return true;
            }
        }
        return false;
    }

    public Expand get(int level) {
        for (Expand expand : cubeExpands) {
            if (expand.getLevel() == level) {
                return expand;
            }
        }
        return null;
    }
}
