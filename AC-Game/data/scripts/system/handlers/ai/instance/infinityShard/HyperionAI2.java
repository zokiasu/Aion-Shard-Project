package ai.instance.infinityShard;

import ai.AggressiveNpcAI2;
import ai.ActionItemNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.*;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.utils.PacketSendUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Eloann
 * @rework Swig
 */
@AIName("hyperion")
public class HyperionAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private Future<?> skillTask;
    private Future<?> BlasterTask;
    private Future<?> EnergyTask;
    protected List<Integer> percents = new ArrayList<Integer>();

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            startSkillTask();
            startBlasterTask();
            startEnergyTask();
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 80:
                        AI2Actions.useSkill(this, 21245);
                        spawnHyperionEasy();
                        spawnAssaultPod();
                        break;
                    case 75:
                        spawnHyperionNormal1();
                        break;
                    case 70:
                        spawnHyperionNormal1();
                        spawnAssaultPod();
                        break;
                    case 60:
                        spawnHyperionNormal1();
                        spawnAssaultPod();
                        break;
                    case 55:
                        spawnHyperionNormal1();
                        break;
                    case 52:
                        spawnHyperionNormal();
                        break;
                    case 50:
                        AI2Actions.useSkill(this, 21244);
                        spawnHyperionHard();
                        spawnAssaultPod();
                        break;
                    case 47:
                        AI2Actions.useSkill(this, 21245);
                        spawnHyperionEasy();
                        break;
                    case 40:
                        AI2Actions.useSkill(this, 21244);
                        spawnHyperionNormal();
                        spawnAssaultPod();
                        break;
                    case 35:
                        spawnHyperionNormal();
                        break;
                    case 30:
                        cancelEnergyTask();
                        AI2Actions.useSkill(this, 21248);
                        spawnHyperionHard();
                        spawnAssaultPod();
                        break;
                    case 25:
                        AI2Actions.useSkill(this, 21244);
                        spawnHyperionHard();
                        spawnHyperionNormal();
                        spawnAssaultPod();
                        break;
                    case 20:
                        spawnHyperionHard();
                        spawnAssaultPod();
                        break;
                    case 10:
                        AI2Actions.useSkill(this, 21246);
                        spawnHyperionNormal();
                        spawnAssaultPod();
                        break;
                    case 5:
                        AI2Actions.useSkill(this, 21249);
                        spawnHyperionHard();
                        spawnAssaultPod();
                        break;
                    case 2:
                        AI2Actions.useSkill(this, 21249);
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{80, 75, 70, 60, 55, 52, 50, 47, 40, 35, 30, 25, 20, 10, 5, 2});
    }

    private void spawnHyperionEasy() {

        spawn(231103, 150.05635f, 128.56758f, 114.49583f, (byte) 16);
        spawn(231103, 147.41049f, 131.2569f, 114.49583f, (byte) 16);
        spawn(231103, 153.60158f, 129.60774f, 114.49583f, (byte) 16);

        sp(233289, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");
        sp(233292, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");
        sp(231096, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");
    }

    private void spawnHyperionNormal() {
        sp(233288, 148.12894f, 148.34091f, 124.03375f, (byte) 105, 1000, "A2142518112");
        sp(233294, 148.12894f, 148.34091f, 124.03375f, (byte) 105, 1000, "A2142518112");
        sp(233296, 148.12894f, 148.34091f, 124.03375f, (byte) 105, 1000, "A2142518112");

        sp(233288, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");
        sp(233296, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");
        sp(233294, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");

        spawn(231103, 150.05635f, 128.56758f, 114.49583f, (byte) 16);
        spawn(231103, 147.41049f, 131.2569f, 114.49583f, (byte) 16);
        spawn(231103, 153.60158f, 129.60774f, 114.49583f, (byte) 16);
    }

    private void spawnHyperionNormal1() {
        sp(233292, 148.12894f, 148.34091f, 124.03375f, (byte) 105, 1000, "A2142518112");
        sp(233294, 148.12894f, 148.34091f, 124.03375f, (byte) 105, 1000, "A2142518112");
        sp(233296, 148.12894f, 148.34091f, 124.03375f, (byte) 105, 1000, "A2142518112");

        sp(233295, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");
        sp(233296, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");
        sp(233294, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");

        spawn(233294, 108.5921f, 145.41702f, 114.03043f, (byte) 20);
        spawn(233295, 150.05635f, 128.56758f, 114.49583f, (byte) 16);
        spawn(231103, 147.41049f, 131.2569f, 114.49583f, (byte) 16);
        spawn(231103, 153.60158f, 129.60774f, 114.49583f, (byte) 16);
    }

    private void spawnHyperionHard() {
        sp(233288, 148.12894f, 148.34091f, 124.03375f, (byte) 105, 1000, "A2142518112");
        sp(233299, 148.12894f, 148.34091f, 124.03375f, (byte) 105, 1000, "A2142518112");
        sp(233296, 148.12894f, 148.34091f, 124.03375f, (byte) 105, 1000, "A2142518112");

        sp(233295, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");
        sp(233296, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");
        sp(233298, 110.090965f, 128.28905f, 124.15179f, (byte) 43, 1000, "B2142518141");

        spawn(233294, 108.5921f, 145.41702f, 114.03043f, (byte) 20);
        spawn(233298, 150.05635f, 128.56758f, 114.49583f, (byte) 16);
        spawn(231103, 147.41049f, 131.2569f, 114.49583f, (byte) 16);
        spawn(231103, 147.41049f, 131.2569f, 114.49583f, (byte) 16);
        spawn(231103, 153.60158f, 129.60774f, 114.49583f, (byte) 16);
    }

    private void spawnAssaultPod() {
        int rnd = Rnd.get(1, 3);
        switch (rnd) {
            case 1:
                spawn(284070, 136.94958f, 133.78249f, 112.12359f, (byte) 74);
                break;
            case 2:
                spawn(284070, 121.06398f, 133.20741f, 112.12359f, (byte) 52);
                break;
            case 3:
                spawn(284070, 124.02135f, 146.44595f, 112.12359f, (byte) 99);
                break;
        }
    }

    private void startSkillTask() {
        skillTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelskillTask();
                } else {
                    Throw();
                }
            }
        }, 30000, 120000);
    }

    private void startBlasterTask() {
        BlasterTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelBlasterTask();
                } else {
                    Blaster();
                }
            }
        }, 2000, 90000);
    }

    private void startEnergyTask() {
        EnergyTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelEnergyTask();
                } else {
                    Energy();
                }
            }
        }, 10000, 160000);
    }

    private void cancelskillTask() {
        if (skillTask != null && !skillTask.isCancelled()) {
            skillTask.cancel(true);
        }
    }

    private void cancelBlasterTask() {
        if (BlasterTask != null && !BlasterTask.isCancelled()) {
            BlasterTask.cancel(true);
        }
    }

    private void cancelEnergyTask() {
        if (EnergyTask != null && !EnergyTask.isCancelled()) {
            EnergyTask.cancel(true);
        }
    }

    private void Throw() {
        SkillEngine.getInstance().getSkill(getOwner(), 21250, 55, getOwner()).useNoAnimationSkill();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                SkillEngine.getInstance().getSkill(getOwner(), 21251, 55, getOwner()).useNoAnimationSkill();
            }
        }, 5000);
    }

    private void Blaster() {
        SkillEngine.getInstance().getSkill(getOwner(), 21241, 60, getOwner().getTarget()).useNoAnimationSkill();
        Player target = getRandomTarget();
        if (target == null) {
        }
    }

    private void Energy() {
        SkillEngine.getInstance().getSkill(getOwner(), 21247, 60, getOwner().getTarget()).useNoAnimationSkill();
        Player target = getRandomTarget();
        if (target == null) {
        }
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        addPercent();
    }

    private void deleteNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            if (npc != null) {
                npc.getController().onDelete();
            }
        }
    }

    private void despawnAdds() {
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        deleteNpcs(instance.getNpcs(231096));
        deleteNpcs(instance.getNpcs(233292));
        deleteNpcs(instance.getNpcs(231103));
        deleteNpcs(instance.getNpcs(233289));
        deleteNpcs(instance.getNpcs(233288));
        deleteNpcs(instance.getNpcs(233294));
        deleteNpcs(instance.getNpcs(233296));
        deleteNpcs(instance.getNpcs(233295));
        deleteNpcs(instance.getNpcs(233299));
        deleteNpcs(instance.getNpcs(233298));
        deleteNpcs(instance.getNpcs(231104));
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        addPercent();
        cancelskillTask();
        cancelBlasterTask();
        cancelEnergyTask();
        isHome.set(true);
        despawnAdds();
    }

    @Override
    protected void handleDespawned() {
        super.handleDespawned();
        percents.clear();
        despawnAdds();
        cancelskillTask();
        cancelBlasterTask();
        cancelEnergyTask();
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        percents.clear();
        cancelskillTask();
        cancelBlasterTask();
        cancelEnergyTask();
        despawnAdds();
    }

    protected void sp(final int npcId, final float x, final float y, final float z, final byte h, final int time, final String walkerId) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                Npc npc = (Npc) spawn(npcId, x, y, z, h);
                npc.getSpawn().setWalkerId(walkerId);
                WalkManager.startWalking((NpcAI2) npc.getAi2());
            }
        }, time);
    }
}
