package ai.instance.udasTempleLower;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.actions.PlayerActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_NPC_INFO;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Debilkarim BossScript TODO: InfernalRift - Teleport random player to oven -
 * need correct server packets
 *
 * @author Antraxx
 */
@AIName("debilkarimthemaker")
public class DebilkarimTheMakerAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private AtomicBoolean hasHealed = new AtomicBoolean(false);
    private Future<?> taskHeal;
    private Future<?> taskInfernalRift;
    private Future<?> taskInfernalRiftTeleport;
    protected List<Integer> percents = new ArrayList<Integer>();
    private final List<Integer> spawnedNpc = new ArrayList<Integer>();

    private Player getTargetPlayer() {
        List<Player> players = new ArrayList<Player>();
        for (Player player : getKnownList().getKnownPlayers().values()) {
            if (!PlayerActions.isAlreadyDead(player) && MathUtil.isIn3dRange(player, getOwner(), 40) && player != getTarget()) {
                players.add(player);
            }
        }
        return !players.isEmpty() ? players.get(Rnd.get(players.size())) : null;
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{50, 25, 10, 5});
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 50:
                        if (hasHealed.compareAndSet(false, true)) {
                            startHealTask();
                        }
                        break;
                    case 25:
                        // temporary disabled
                        //if (taskHoellenriss == null || taskHoellenriss.isDone()) {
                        //	startHoellenRissTask();
                        //}
                        break;
                    case 10:
                        rndSpawn(215845, 5);
                        break;
                    case 5:
                        rndSpawn(215845, 5);
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500038, getObjectId(), 0, 0);
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        addPercent();
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        removeHelpersSpawn();
        addPercent();
        cancelHealTask();
        cancelInfernalRiftTask();
        getOwner().getEffectController().removeAllEffects();
        isHome.set(true);
        hasHealed.set(false);
    }

    @Override
    protected void handleDied() {
        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500039, getObjectId(), 0, 0);
        super.handleDied();
        removeHelpersSpawn();
        percents.clear();
        cancelHealTask();
        cancelInfernalRiftTask();
    }

    @Override
    protected void handleDespawned() {
        removeHelpersSpawn();
        super.handleDespawned();
        cancelHealTask();
        cancelInfernalRiftTask();
    }

    private void startHealTask() {
        taskHeal = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                // Spawn Protection of Aion
                if (!isAlreadyDead() && isHome.get() == false) {
                    rndSpawn(281420, 6);
                }
            }
        }, 8000);
        AI2Actions.targetSelf(this);
        AI2Actions.useSkill(this, 18636);
    }

    private void cancelHealTask() {
        if (taskHeal != null && !taskHeal.isCancelled()) {
            taskHeal.cancel(true);
        }
    }

    @SuppressWarnings("unused")
    private void startInfernalRiftTask() {
        taskInfernalRift = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead() || isHome.get() == true) {
                    cancelInfernalRiftTask();
                } else {
                    startOvenEvent();
                }
            }
        }, 15000, 15000);
    }

    private void cancelInfernalRiftTask() {
        if (taskInfernalRiftTeleport != null && !taskInfernalRiftTeleport.isCancelled()) {
            taskInfernalRiftTeleport.cancel(true);
        }
        if (taskInfernalRift != null && !taskInfernalRift.isCancelled()) {
            taskInfernalRift.cancel(true);
        }
    }

    private void rndSpawn(int npcId, int count) {
        if (!isAlreadyDead()) {
            for (int i = 0; i < count; i++) {
                SpawnTemplate summon = null;
                summon = rndSpawnInRange(npcId, 7);
                VisibleObject npc = SpawnEngine.spawnObject(summon, getPosition().getInstanceId());
                addHelpersSpawn(npc.getObjectId());
                PacketSendUtility.broadcastPacket(getOwner(), new SM_NPC_INFO((Npc) npc, ""));
            }
        }
    }

    protected SpawnTemplate rndSpawnInRange(int npcId, float distance) {
        float direction = Rnd.get(0, 199) / 100f;
        float x = (float) (Math.cos(Math.PI * direction) * distance);
        float y = (float) (Math.sin(Math.PI * direction) * distance);
        return SpawnEngine.addNewSingleTimeSpawn(getPosition().getMapId(), npcId, getPosition().getX() + x, getPosition().getY() + y,
                getPosition().getZ(), getPosition().getHeading());
    }

    private void teleportRandomPlayerToOven() {
        Player target = getTargetPlayer();
        if (target == null) {
            return;
        }
        World.getInstance().updatePosition(target, (float) 530.42725, (float) 1299.0607, (float) 196.63838, (byte) 90);
        PacketSendUtility.broadcastPacketAndReceive(target, new SM_FORCED_MOVE(target, target));
    }

    private void startOvenEvent() {
        SkillTemplate hoellenRissSkill = DataManager.SKILL_DATA.getSkillTemplate(19000);
        int timer = hoellenRissSkill.getDuration() + 1000;
        taskInfernalRiftTeleport = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                // teleport random player to oven
                if (!isAlreadyDead() && isHome.get() == false) {
                    teleportRandomPlayerToOven();
                }
            }
        }, timer);
        AI2Actions.useSkill(this, 19000);
    }

    protected void addHelpersSpawn(int objId) {
        synchronized (spawnedNpc) {
            spawnedNpc.add(objId);
        }
    }

    /**
     * Remove Helpers
     */
    private void removeHelpersSpawn() {
        for (Integer object : spawnedNpc) {
            VisibleObject npc = World.getInstance().findVisibleObject(object);
            if (npc != null && npc.isSpawned()) {
                npc.getController().onDelete();
            }
        }
        spawnedNpc.clear();
    }
}
