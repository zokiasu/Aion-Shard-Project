package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.templates.portal.InstanceExit;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "instanceExit"
})
@XmlRootElement(name = "instance_exits")
public class InstanceExitData {

    @XmlElement(name = "instance_exit")
    protected List<InstanceExit> instanceExit;
    @XmlTransient
    protected List<InstanceExit> instanceExits = new ArrayList<InstanceExit>();

    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        for (InstanceExit exit : instanceExit) {
            instanceExits.add(exit);
        }
        instanceExit.clear();
        instanceExit = null;
    }

    public InstanceExit getInstanceExit(int worldId, Race race) {
        for (InstanceExit exit : instanceExits) {
            if (exit.getInstanceId() == worldId && (race.equals(exit.getRace()) || exit.getRace().equals(Race.PC_ALL))) {
                return exit;
            }
        }
        return null;
    }

    public int size() {
        return instanceExits.size();
    }
}
