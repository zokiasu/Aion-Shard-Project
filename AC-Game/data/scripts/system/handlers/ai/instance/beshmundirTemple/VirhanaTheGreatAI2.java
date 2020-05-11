package ai.instance.beshmundirTemple;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.services.NpcShoutsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Antraxx
 */
@AIName("virhana")
public class VirhanaTheGreatAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private Future<?> taskRage;
    private Future<?> taskRageExec;
    private int count;
    protected List<Integer> percents = new ArrayList<Integer>();

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{50});
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 50:
                        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500065, getObjectId(), 0, 1000);
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void cancelTasks() {
        if ((taskRageExec != null) && !taskRageExec.isDone()) {
            taskRageExec.cancel(true);
        }
        if ((taskRage != null) && !taskRage.isDone()) {
            taskRage.cancel(true);
        }
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500064, getObjectId(), 0, 1000);
            scheduleRage();
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    @Override
    protected void handleDied() {
        cancelTasks();
        super.handleDied();
        percents.clear();
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        addPercent();
    }

    @Override
    protected void handleBackHome() {
        cancelTasks();
        super.handleBackHome();
        isHome.set(true);
        addPercent();
    }

    private void scheduleRage() {
        if (isAlreadyDead() || isHome.equals(true)) {
            return;
        }
        AI2Actions.useSkill(this, 19121);
        taskRage = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                startRage();
            }
        }, 70000);
    }

    private void startRage() {
        if (isAlreadyDead() || isHome.equals(true)) {
            return;
        }
        if (count < 12) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500066, getObjectId(), 0, 1000);
            AI2Actions.useSkill(this, 18897);
            count++;
            taskRageExec = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    startRage();
                }
            }, 10000);
        } else { // restart after a douzen casts
            count = 0;
            scheduleRage();
        }
    }
}
