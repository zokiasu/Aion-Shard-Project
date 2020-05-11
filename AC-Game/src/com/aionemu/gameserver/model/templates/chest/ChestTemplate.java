package com.aionemu.gameserver.model.templates.chest;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Wakizashi
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Chest")
public class ChestTemplate {

    @XmlAttribute(name = "npcid")
    protected int npcId;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlElement(name = "keyitem")
    protected List<KeyItem> keyItem;

    /**
     * @return the npcId
     */
    public int getNpcId() {
        return npcId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the keyItem
     */
    public List<KeyItem> getKeyItem() {
        return keyItem;
    }
}
