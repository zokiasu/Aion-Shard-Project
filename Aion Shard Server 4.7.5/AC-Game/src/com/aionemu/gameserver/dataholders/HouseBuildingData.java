package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.housing.Building;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"buildings"})
public class HouseBuildingData {

    @XmlElement(name = "building")
    protected List<Building> buildings;
    @XmlTransient
    Map<Integer, Building> buildingById = new HashMap<Integer, Building>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        if (buildings == null) {
            return;
        }

        for (Building building : buildings) {
            buildingById.put(building.getId(), building);
        }

        buildings.clear();
        buildings = null;
    }

    public Building getBuilding(int buildingId) {
        return buildingById.get(buildingId);
    }

    public int size() {
        return buildingById.size();
    }
}
