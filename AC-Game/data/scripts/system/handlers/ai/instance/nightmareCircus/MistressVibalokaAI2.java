package ai.instance.nightmareCircus;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

@AIName("mistress_vibaloka")
public class MistressVibalokaAI2 extends AggressiveNpcAI2 {

    private final List<Integer> percents = new ArrayList<Integer>();
    private boolean canThink = true;
    private Future<?> thinkTask;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        addPercent();
        sendMsg(1500988);
    }

    @Override
    public boolean canThink() {
        return canThink;
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                percents.remove(percent);
                canThink = false;
                thinkTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        int count = Rnd.get(2, 4);
                        while (count > 0) {
                            count--;
                            sp(233150);
                        }
                        sendMsg(1500987);
                        canThink = true;
                        Creature creature = getAggroList().getMostHated();
                        if (creature == null || creature.getLifeStats().isAlreadyDead() || !getOwner().canSee(creature)) {
                            setStateIfNot(AIState.FIGHT);
                            think();
                        } else {
                            getMoveController().abortMove();
                            getOwner().setTarget(creature);
                            getOwner().getGameStats().renewLastAttackTime();
                            getOwner().getGameStats().renewLastAttackedTime();
                            getOwner().getGameStats().renewLastChangeTargetTime();
                            getOwner().getGameStats().renewLastSkillTime();
                            setStateIfNot(AIState.FIGHT);
                            handleMoveValidate();
                        }
                    }
                }, 2000);
                break;
            }
        }
    }

    private void sp(int npcId) {
        float direction = Rnd.get(0, 199) / 100f;
        int distance = Rnd.get(1, 2);
        float x1 = (float) (Math.cos(Math.PI * direction) * distance);
        float y1 = (float) (Math.sin(Math.PI * direction) * distance);
        WorldPosition p = getPosition();
        final Npc npc = (Npc) spawn(npcId, p.getX() + x1, p.getY() + y1, p.getZ(), p.getHeading());
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!NpcActions.isAlreadyDead(npc) && npc.isSpawned()) {
                    NpcActions.delete(npc);
                }
            }
        }, 15000);
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{85, 65, 55, 45, 30, 15});
    }

    private void cancelThinkTask() {
        if (thinkTask != null && !thinkTask.isDone()) {
            thinkTask.cancel(true);
        }
    }

    @Override
    protected void handleDespawned() {
        cancelThinkTask();
        super.handleDespawned();
    }

    private void removeHelpers() {
        WorldPosition p = getPosition();
        if (p != null) {
            WorldMapInstance instance = p.getWorldMapInstance();
            if (instance != null) {
                deleteNpcs(instance.getNpcs(233150));
            }
        }
    }

    private void deleteNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            if (npc != null) {
                npc.getController().onDelete();
            }
        }
    }

    @Override
    protected void handleDied() {
        removeHelpers();
        cancelThinkTask();
        super.handleDied();
    }

    @Override
    protected void handleBackHome() {
        canThink = true;
        addPercent();
        cancelThinkTask();
        removeHelpers();
        super.handleBackHome();
    }

    private void sendMsg(int msg) {
        NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), 0, 0);
    }
}
