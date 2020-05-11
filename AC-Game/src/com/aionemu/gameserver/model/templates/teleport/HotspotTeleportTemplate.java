package com.aionemu.gameserver.model.templates.teleport;

import com.aionemu.gameserver.model.Race;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "hotspot_template")
@XmlAccessorType(XmlAccessType.NONE)
public class HotspotTeleportTemplate {

    /**
     * Location Id.
     */
    @XmlAttribute(name = "id", required = true)
    private int locId;
    /**
     * location name.
     */
    @XmlAttribute(name = "name")
    private String name = "";
    @XmlAttribute(name = "mapId", required = true)
    private int mapId;
    @XmlAttribute(name = "posX", required = true)
    private float x = 0;
    @XmlAttribute(name = "posY", required = true)
    private float y = 0;
    @XmlAttribute(name = "posZ", required = true)
    private float z = 0;
    @XmlAttribute(name = "heading")
    private int heading = 0;
    @XmlAttribute(name = "race")
    private Race race;
    @XmlAttribute(name = "kinah")
    private int kinah = 0;
    @XmlAttribute(name = "kinah_dis")
    private float kinah_dis = 0;
    @XmlAttribute(name = "level")
    private int level = 0;

    public int getLocId() {
        return locId;
    }

    public int getMapId() {
        return mapId;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public int getHeading() {
        return heading;
    }

    public Race getRace() {
        return race;
    }

    public int getKinah() {
        return kinah;
    }

    public float getDisKinah() {
        return kinah_dis;
    }

    public int getLevel() {
        return level;
    }
}
