package com.aionemu.gameserver.model.templates.shield;

import com.aionemu.gameserver.model.utils3d.Point3D;

import javax.xml.bind.annotation.*;

/**
 * @author M@xx, Wakizashi
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Shield")
public class ShieldTemplate {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "map")
    protected int map;
    @XmlAttribute(name = "id")
    protected int id;
    @XmlAttribute(name = "radius")
    protected float radius;
    @XmlElement(name = "center")
    protected ShieldPoint center;

    public String getName() {
        return name;
    }

    public int getMap() {
        return map;
    }

    public float getRadius() {
        return radius;
    }

    public ShieldPoint getCenter() {
        return center;
    }

    public int getId() {
        return id;
    }

    public ShieldTemplate() {
    }

    ;

    public ShieldTemplate(String name, int mapId, Point3D center) {
        this.name = name;
        this.map = mapId;
        this.radius = 6;
        this.center = new ShieldPoint(center);
    }
}