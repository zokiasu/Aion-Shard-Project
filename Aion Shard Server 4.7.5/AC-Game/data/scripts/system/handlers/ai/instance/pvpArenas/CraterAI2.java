package ai.instance.pvpArenas;

import ai.GeneralNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.Future;

/**
 * @author wasacacax
 */
@AIName("crater")
public class CraterAI2 extends GeneralNpcAI2 {

    private Future<?> eventTask;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        startEventTask();
    }

    @Override
    protected void handleDied() {
        cancelEventTask();
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        cancelEventTask();
        super.handleDespawned();
    }

    private void cancelEventTask() {
        if (eventTask != null && !eventTask.isDone()) {
            eventTask.cancel(true);
        }
    }

    private void startEventTask() {
        eventTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelEventTask();
                } else {
                    SkillEngine.getInstance().getSkill(getOwner(), 20057, 60, getOwner()).useNoAnimationSkill();
                }
            }
        }, 1000, 3000);
    }
}
