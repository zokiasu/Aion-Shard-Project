package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.teleport.HotspotTeleportTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "hotspot_teleport")
@XmlAccessorType(XmlAccessType.FIELD)
public class HotspotTeleporterData {

    @XmlElement(name = "hotspot_template")
    private List<HotspotTeleportTemplate> tlist;
    /**
     * A map containing all teleport location templates
     */
    private TIntObjectHashMap<HotspotTeleportTemplate> loctlistData = new TIntObjectHashMap<HotspotTeleportTemplate>();

    /**
     * @param u
     * @param parent
     */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (HotspotTeleportTemplate loc : tlist) {
            loctlistData.put(loc.getLocId(), loc);
        }
    }

    public int size() {
        return loctlistData.size();
    }

    public HotspotTeleportTemplate getHotspotTemplate(int id) {
        return loctlistData.get(id);
    }
}
