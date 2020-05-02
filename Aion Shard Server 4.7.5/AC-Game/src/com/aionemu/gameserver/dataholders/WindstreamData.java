package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.windstreams.WindstreamTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author LokiReborn
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "windstreams")
public class WindstreamData {

    @XmlElement(name = "windstream")
    private List<WindstreamTemplate> wts;
    private TIntObjectHashMap<WindstreamTemplate> windstreams;

    void afterUnmarshal(Unmarshaller u, Object parent) {
        windstreams = new TIntObjectHashMap<WindstreamTemplate>();
        for (WindstreamTemplate wt : wts) {
            windstreams.put(wt.getMapid(), wt);
        }

        wts = null;
    }

    public WindstreamTemplate getStreamTemplate(int mapId) {
        return windstreams.get(mapId);
    }

    /**
     * @return items.size()
     */
    public int size() {
        return windstreams.size();
    }
}
