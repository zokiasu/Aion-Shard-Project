package com.aionemu.gameserver.spawnengine;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.model.templates.walker.WalkerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Forms the walker groups on initial spawn<br>
 * Brings NPCs back to their positions if they die<br>
 * Cleanup and rework will be made after tests and error handling<br>
 * To use only with patch!
 *
 * @author vlog
 * @based on Imaginary's imagination
 * @modified Rolandas
 */
public class WalkerFormator {

    private static final Logger log = LoggerFactory.getLogger(WalkerFormator.class);

    /**
     * If it's the instance first spawn, WalkerFormator verifies and creates
     * groups; {@link #organizeAndSpawn()} must be called after to speed up
     * spawning. If it's a respawn, nothing to verify, then the method places
     * NPC to the first step and resets data to the saved, no organizing is
     * needed.
     *
     * @param npc
     * @param instance
     * @return <tt>true</tt> if npc was brought into world by the method call.
     */
    public static boolean processClusteredNpc(Npc npc, int worldId, int instanceId) {
        SpawnTemplate spawn = npc.getSpawn();
        if (spawn.getWalkerId() != null) {
            InstanceWalkerFormations formations = WalkerFormationsCache.getInstanceFormations(worldId, instanceId);
            WalkerGroup wg = formations.getSpawnWalkerGroup(spawn.getWalkerId());

            if (wg != null) {
                npc.setWalkerGroup(wg);
                wg.respawn(npc);
                return false;
            }

            WalkerTemplate template = DataManager.WALKER_DATA.getWalkerTemplate(spawn.getWalkerId());
            if (template == null) {
                log.warn("Missing walker ID: " + spawn.getWalkerId());
                return false;
            }
            if (template.getPool() < 2) {
                return false;
            }
            return formations.cacheWalkerCandidate(new ClusteredNpc(npc, instanceId, template));
        }
        return false;
    }

    /**
     * Organizes spawns in all processed walker groups. Must be called only when
     * spawning all npcs for the instance of world.
     */
    public static void organizeAndSpawn(int worldId, int instanceId) {
        InstanceWalkerFormations formations = WalkerFormationsCache.getInstanceFormations(worldId, instanceId);
        formations.organizeAndSpawn();
    }

    public static void changeWalkerGroup(int worldId, int instanceId, WalkerGroup walkerGroup) {
        InstanceWalkerFormations formations = WalkerFormationsCache.getInstanceFormations(worldId, instanceId);
        formations.changeCluster(walkerGroup);
    }

    /**
     * @param worldId
     * @param instanceId
     */
    public static void onInstanceDestroy(int worldId, int instanceId) {
        WalkerFormationsCache.onInstanceDestroy(worldId, instanceId);
    }
}
