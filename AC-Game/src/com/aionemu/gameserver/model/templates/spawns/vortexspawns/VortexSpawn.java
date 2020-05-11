package com.aionemu.gameserver.model.templates.spawns.vortexspawns;

import com.aionemu.gameserver.model.templates.spawns.Spawn;
import com.aionemu.gameserver.model.vortex.VortexStateType;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Source
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VortexSpawn")
public class VortexSpawn {

    @XmlAttribute(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    @XmlElement(name = "state_type")
    private List<VortexSpawn.VortexStateTemplate> VortexStateTemplate;

    public List<VortexStateTemplate> getSiegeModTemplates() {
        return VortexStateTemplate;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "VortexStateTemplate")
    public static class VortexStateTemplate {

        @XmlElement(name = "spawn")
        private List<Spawn> spawns;
        @XmlAttribute(name = "state")
        private VortexStateType stateType;

        public List<Spawn> getSpawns() {
            return spawns;
        }

        public VortexStateType getStateType() {
            return stateType;
        }
    }
}
