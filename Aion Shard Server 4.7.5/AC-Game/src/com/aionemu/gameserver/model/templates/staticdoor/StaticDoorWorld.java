package com.aionemu.gameserver.model.templates.staticdoor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "World")
public class StaticDoorWorld {

    @XmlAttribute(name = "world")
    protected int world;
    @XmlElement(name = "staticdoor")
    protected List<StaticDoorTemplate> staticDoorTemplate;

    /**
     * @return the world
     */
    public int getWorld() {
        return world;
    }

    /**
     * @return the List<StaticDoorTemplate>
     */
    public List<StaticDoorTemplate> getStaticDoors() {
        return staticDoorTemplate;
    }
}
