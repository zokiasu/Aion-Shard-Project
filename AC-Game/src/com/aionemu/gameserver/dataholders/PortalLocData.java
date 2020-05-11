package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.portal.PortalLoc;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "portalLoc"
})
@XmlRootElement(name = "portal_locs")
public class PortalLocData {

    @XmlElement(name = "portal_loc")
    protected List<PortalLoc> portalLoc;
    @XmlTransient
    private TIntObjectHashMap<PortalLoc> portalLocs = new TIntObjectHashMap<PortalLoc>();

    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        for (PortalLoc loc : portalLoc) {
            portalLocs.put(loc.getLocId(), loc);

        }
        portalLoc.clear();
        portalLoc = null;
    }

    public int size() {
        return portalLocs.size();
    }

    public PortalLoc getPortalLoc(int locId) {
        return portalLocs.get(locId);
    }
}
