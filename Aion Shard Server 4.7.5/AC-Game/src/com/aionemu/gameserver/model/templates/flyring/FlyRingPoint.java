package com.aionemu.gameserver.model.templates.flyring;

import com.aionemu.gameserver.model.utils3d.Point3D;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author M@xx
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlyRingPoint")
public class FlyRingPoint {

    @XmlAttribute(name = "x")
    private float x;
    @XmlAttribute(name = "y")
    private float y;
    @XmlAttribute(name = "z")
    private float z;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public FlyRingPoint() {
    }

    public FlyRingPoint(Point3D p) {
        x = (float) p.x;
        y = (float) p.y;
        z = (float) p.z;
    }
}
