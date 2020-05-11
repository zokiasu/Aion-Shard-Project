package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.ride.RideInfo;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"rides"})
@XmlRootElement(name = "rides")
public class RideData {

    @XmlElement(name = "ride_info")
    private List<RideInfo> rides;
    @XmlTransient
    private TIntObjectHashMap<RideInfo> rideInfos;

    void afterUnmarshal(Unmarshaller u, Object parent) {
        rideInfos = new TIntObjectHashMap<RideInfo>();

        for (RideInfo info : rides) {
            rideInfos.put(info.getNpcId(), info);
        }
        rides.clear();
        rides = null;
    }

    public RideInfo getRideInfo(int npcId) {
        return rideInfos.get(npcId);
    }

    public int size() {
        return rideInfos.size();
    }
}
