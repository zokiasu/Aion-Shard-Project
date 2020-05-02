package ai.instance.udasTempleLower;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.services.NpcShoutsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Anvilface BossScript
 *
 * @author Antraxx
 */
@AIName("anvilface")
public class AnvilFaceAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    protected List<Integer> percents = new ArrayList<Integer>();

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{50, 25});
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 50:
                        break;
                    case 25:
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
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500036, getObjectId(), 0, 0);
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    @Override
    protected void handleBackHome() {
        addPercent();
        super.handleBackHome();
        isHome.set(true);
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        percents.clear();
        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500037, getObjectId(), 0, 0);
    }
}
