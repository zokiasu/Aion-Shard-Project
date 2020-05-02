package ai.instance.beshmundirTemple;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.services.NpcShoutsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Antraxx
 */
@AIName("taroslifebane")
public class TarosLifebaneAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    protected List<Integer> percents = new ArrayList<Integer>();

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{75, 50, 25});
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        addPercent();
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 75:
                        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500074, getObjectId(), 0, 0);
                        break;
                    case 50:
                        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500074, getObjectId(), 0, 0);
                        break;
                    case 25:
                        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500074, getObjectId(), 0, 0);
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
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500073, getObjectId(), 0, 0);
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        percents.clear();
        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500075, getObjectId(), 0, 0);
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        isHome.set(true);
    }
}
