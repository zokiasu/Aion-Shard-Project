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
@AIName("flarestorm")
public class FlarestormAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    protected List<Integer> percents = new ArrayList<Integer>();
    private Future<?> taskOrbOfAnnihilation;
    private Future<?> taskPressureWave;
    private Future<?> taskUsingSkill;
    private AtomicBoolean isUseOtherskill = new AtomicBoolean(false);

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{90, 75, 50, 25});
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
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500076, getObjectId(), 0, 1000);
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        percents.clear();
        taskOrbOfAnnihilationStop();
        taskPressureWaveStop();
        taskUseSkillStop();
        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500078, getObjectId(), 0, 1000);
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        isHome.set(true);
        isUseOtherskill.set(false);
        taskOrbOfAnnihilationStop();
        taskPressureWaveStop();
        taskUseSkillStop();
        addPercent();
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 90:
                        // druckwelle
                        taskPressureWaveStart();
                        break;
                    case 75:
                        // aschemantel
                        taskUseSkillStart();
                        AI2Actions.useSkill(this, 18997);
                        break;
                    case 50:
                        // aschemantel
                        taskUseSkillStart();
                        AI2Actions.useSkill(this, 18997);
                        break;
                    case 25:
                        // aschemantel
                        taskUseSkillStart();
                        AI2Actions.useSkill(this, 18997);
                        // starte Kugel der Vernichtung
                        taskOrbOfAnnihilationStart();
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void taskOrbOfAnnihilationStart() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (isHome.equals(true) || isAlreadyDead()) {
                    taskOrbOfAnnihilationStop();
                    return;
                }
                AI2Actions.useSkill(FlarestormAI2.this, 18911);
            }
        }, 20000);
    }

    private void taskOrbOfAnnihilationStop() {
        if ((taskOrbOfAnnihilation != null) && !taskOrbOfAnnihilation.isDone()) {
            taskOrbOfAnnihilation.cancel(true);
        }
    }

    private void taskPressureWaveStart() {
        taskPressureWave = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    taskPressureWaveStop();
                } else {
                    if (isUseOtherskill.compareAndSet(false, true)) {
                        AI2Actions.useSkill(FlarestormAI2.this, 18909);
                    }
                }
            }
        }, 45000, 45000);
    }

    private void taskPressureWaveStop() {
        if ((taskPressureWave != null) && !taskPressureWave.isDone()) {
            taskPressureWave.cancel(true);
        }
    }

    private void taskUseSkillStart() {
        isUseOtherskill.set(true);
        taskUsingSkill = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (isHome.equals(true) || isAlreadyDead()) {
                    taskUseSkillStop();
                    return;
                }
                isUseOtherskill.set(false);
            }
        }, 12000);
    }

    private void taskUseSkillStop() {
        isUseOtherskill.set(false);
        if ((taskUsingSkill != null) && !taskUsingSkill.isDone()) {
            taskUsingSkill.cancel(true);
        }
    }
}
