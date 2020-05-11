package com.aionemu.gameserver.spawnengine;

import com.aionemu.commons.utils.internal.chmv8.PlatformDependent;

import java.util.Map;

/**
 * @author Rolandas
 */
public class WorldWalkerFormations {

    private Map<Integer, InstanceWalkerFormations> formations;

    public WorldWalkerFormations() {
        formations = PlatformDependent.newConcurrentHashMap();
    }

    /**
     * @param instanceId
     * @return
     */
    protected InstanceWalkerFormations getInstanceFormations(int instanceId) {
        InstanceWalkerFormations instanceFormation = formations.get(instanceId);
        if (instanceFormation == null) {
            instanceFormation = new InstanceWalkerFormations();
            formations.put(instanceId, instanceFormation);
        }
        return instanceFormation;
    }
}
