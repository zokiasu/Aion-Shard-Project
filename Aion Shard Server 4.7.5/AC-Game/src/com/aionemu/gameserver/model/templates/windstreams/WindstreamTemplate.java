package com.aionemu.gameserver.model.templates.windstreams;

import javax.xml.bind.annotation.*;

/**
 * @author LokiReborn
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WindFlight")
public class WindstreamTemplate {

    @XmlElement(required = true)
    protected StreamLocations locations;
    @XmlAttribute
    protected int mapid;

    /**
     * Gets the value of the locations property.
     */
    public StreamLocations getLocations() {
        return locations;
    }

    /**
     * Gets the value of the mapid property.
     */
    public int getMapid() {
        return mapid;
    }
}
