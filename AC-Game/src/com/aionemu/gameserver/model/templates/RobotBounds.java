package com.aionemu.gameserver.model.templates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Ever'
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RobotBounds")
public class RobotBounds extends BoundRadius {

    public RobotBounds() {
    }

    public RobotBounds(float front, float side, float upper) {
        super(front, side, upper);
    }
}
