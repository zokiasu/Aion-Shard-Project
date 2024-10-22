package ai.instance.beshmundirTemple;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

@AIName("macunbello")
public class MacunbelloAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private boolean wave1 = false;
    private boolean wave2 = false;
    private ArrayList<Npc> wave_npcs = new ArrayList<Npc>();
    private Future<?> wave1_task;
    private Future<?> wave2_task;

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500060, getObjectId(), 0, 1000);
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    @Override
    protected void handleDied() {
        cancelWaveTasks();
        super.handleDied();
        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500063, getObjectId(), 0, 1000);
    }

    @Override
    protected void handleBackHome() {
        cancelWaveTasks();
        isHome.set(true);
        super.handleBackHome();
    }

    private void cancelWaveTasks() {
        if (wave1_task != null && !wave1_task.isDone()) {
            wave1_task.cancel(true);
        }
        if (wave2_task != null && !wave2_task.isDone()) {
            wave2_task.cancel(true);
        }
        killAllAdds();
    }

    private void killAllAdds() {
        if (wave_npcs.size() == 0) {
            return;
        }
        for (Npc npc : wave_npcs) {
            if (npc != null && npc.isSpawned()) {
                npc.getController().onDelete();
            }
        }
        wave_npcs.clear();
    }

    private synchronized void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 70 && !wave1) {
            wave1 = true;
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500061, getObjectId(), 0, 0);
            wave1_task = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (!isAlreadyDead()) {
                        // Macunbello's Right Hand.
                        wave_npcs.add((Npc) spawn(281698, 955.97986f, 160.24693f, 241.77303f, (byte) 108));
                        wave_npcs.add((Npc) spawn(281698, 1003.958f, 159.76878f, 241.77016f, (byte) 30));
                        wave_npcs.add((Npc) spawn(281698, 1007.1438f, 109.738075f, 242.7066f, (byte) 30));
                        wave_npcs.add((Npc) spawn(281698, 952.05365f, 109.55048f, 242.7103f, (byte) 30));
                        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500062, getObjectId(), 0, 4000);
                    }
                }
            }, 4000);
            return;
        }
        if (hpPercentage <= 30 && !wave2) {
            wave2 = true;
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500061, getObjectId(), 0, 0);
            wave2_task = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (!isAlreadyDead()) {
                        wave_npcs.add((Npc) spawn(281698, 955.97986f, 160.24693f, 241.77303f, (byte) 108));
                        wave_npcs.add((Npc) spawn(281698, 1003.958f, 159.76878f, 241.77016f, (byte) 30));
                        wave_npcs.add((Npc) spawn(281698, 1007.1438f, 109.738075f, 242.7066f, (byte) 30));
                        wave_npcs.add((Npc) spawn(281698, 952.05365f, 109.55048f, 242.7103f, (byte) 30));
                        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500062, getObjectId(), 0, 4000);
                    }
                }
            }, 4000);
            return;
        }

    }
}
