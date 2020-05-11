package com.aionemu.gameserver.model.templates.walker;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteParent", propOrder = {"versions"})
public class RouteParent {

    @XmlElement(name = "version", required = true)
    protected List<RouteVersion> versions;
    @XmlAttribute(required = true)
    protected String id;

    public List<RouteVersion> getRouteVersion() {
        if (versions == null) {
            versions = new ArrayList<RouteVersion>();
        }
        return this.versions;
    }

    public String getId() {
        return id;
    }
}
