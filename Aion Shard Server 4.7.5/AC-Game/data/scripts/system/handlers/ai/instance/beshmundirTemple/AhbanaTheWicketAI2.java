package ai.instance.beshmundirTemple;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Antraxx
 */
@AIName("ahbanathewicked")
public class AhbanaTheWicketAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private Future<?> taskWeepingCurtain;
    protected List<Integer> percents = new ArrayList<Integer>();

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{75});
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 75:
                        taskWeepingCurtainStart();
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        addPercent();
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500045, getObjectId(), 0, 0);
            callForHelp(36);
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        taskWeepingCurtainStop();
        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500047, getObjectId(), 0, 1000);
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        isHome.set(true);
        taskWeepingCurtainStop();
    }

    private void taskWeepingCurtainStop() {
        if ((taskWeepingCurtain != null) && !taskWeepingCurtain.isDone()) {
            taskWeepingCurtain.cancel(true);
        }
    }

    private void taskWeepingCurtainStart() {
        taskWeepingCurtain = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    taskWeepingCurtainStop();
                } else {
                    AI2Actions.useSkill(AhbanaTheWicketAI2.this, 18892);
                }
            }
        }, 10000, 40000);
    }
}
