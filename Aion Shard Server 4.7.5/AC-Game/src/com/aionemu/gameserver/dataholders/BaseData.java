package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.base.BaseLocation;
import com.aionemu.gameserver.model.templates.base.BaseTemplate;
import javolution.util.FastMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Source
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "base_locations")
public class BaseData {

    @XmlElement(name = "base_location")
    private List<BaseTemplate> baseTemplates;
    @XmlTransient
    private FastMap<Integer, BaseLocation> base = new FastMap<Integer, BaseLocation>();

    /**
     * @param u
     * @param parent
     */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (BaseTemplate template : baseTemplates) {
            base.put(template.getId(), new BaseLocation(template));
        }
    }

    public int size() {
        return base.size();
    }

    public FastMap<Integer, BaseLocation> getBaseLocations() {
        return base;
    }
}
