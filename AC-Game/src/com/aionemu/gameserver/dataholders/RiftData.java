package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.rift.RiftLocation;
import com.aionemu.gameserver.model.templates.rift.RiftTemplate;
import javolution.util.FastMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Source
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rift_locations")
public class RiftData {

    @XmlElement(name = "rift_location")
    private List<RiftTemplate> riftTemplates;
    @XmlTransient
    private FastMap<Integer, RiftLocation> rift = new FastMap<Integer, RiftLocation>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (RiftTemplate template : riftTemplates) {
            rift.put(template.getId(), new RiftLocation(template));
        }
    }

    public int size() {
        return rift.size();
    }

    public FastMap<Integer, RiftLocation> getRiftLocations() {
        return rift;
    }
}
