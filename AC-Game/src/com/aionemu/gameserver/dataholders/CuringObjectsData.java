package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.curingzones.CuringTemplate;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "curingObject"
})
@XmlRootElement(name = "curing_objects")
public class CuringObjectsData {

    @XmlElement(name = "curing_object")
    protected List<CuringTemplate> curingObject;
    @XmlTransient
    private List<CuringTemplate> curingObjects = new ArrayList<CuringTemplate>();

    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        for (CuringTemplate template : curingObject) {
            curingObjects.add(template);
        }
    }

    public int size() {
        return curingObjects.size();
    }

    public List<CuringTemplate> getCuringObject() {
        return curingObjects;
    }
}
