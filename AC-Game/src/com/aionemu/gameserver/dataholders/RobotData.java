package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.robot.RobotInfo;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Ever'
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"robots"})
@XmlRootElement(name = "robots")
public class RobotData {

    @XmlElement(name = "robot_info")
    private List<RobotInfo> robots;
    @XmlTransient
    private TIntObjectHashMap<RobotInfo> robotInfos;

    void afterUnmarshal(Unmarshaller u, Object parent) {
        robotInfos = new TIntObjectHashMap<RobotInfo>();
        for (RobotInfo info : robots) {
            robotInfos.put(info.getRobotId(), info);
        }
        robots.clear();
        robots = null;
    }

    public RobotInfo getRobotInfo(int npcId) {
        return (RobotInfo) robotInfos.get(npcId);
    }

    public int size() {
        return robotInfos.size();
    }
}
