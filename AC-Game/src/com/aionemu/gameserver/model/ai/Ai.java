package com.aionemu.gameserver.model.ai;

import javax.xml.bind.annotation.*;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ai")
public class Ai {

    @XmlElement(name = "summons")
    private Summons summons;
    @XmlElement(name = "bombs")
    private Bombs bombs;
    @XmlAttribute(name = "npcId")
    private int npcId;

    public Summons getSummons() {
        return this.summons;
    }

    public Bombs getBombs() {
        return this.bombs;
    }

    public int getNpcId() {
        return this.npcId;
    }
}
