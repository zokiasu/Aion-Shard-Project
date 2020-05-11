package com.aionemu.gameserver.model.templates.robot;

import com.aionemu.gameserver.model.templates.RobotBounds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Ever'
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RobotInfo", propOrder = {"bound"})
public class RobotInfo {

    protected RobotBounds bound;
    @XmlAttribute
    protected Integer type;
    @XmlAttribute(required = true)
    protected int id;
    @XmlAttribute(name = "name", required = true)
    private String name;

    public int getRobotId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RobotBounds getBound() {
        return bound;
    }
}
