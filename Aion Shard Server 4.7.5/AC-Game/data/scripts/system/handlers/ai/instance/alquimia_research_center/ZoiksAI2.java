package ai.instance.alquimia_research_center;

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

@AIName("zoiks")
public class ZoiksAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private Future<?> skillTask;
    private Future<?> BlasterTask;
    private Future<?> EnergyTask;
    protected List<Integer> percents = new ArrayList<Integer>();

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 90:
                        AI2Actions.useSkill(this, 2834);
                        spawnAlquimiaZoiks();
                        break;
                    case 80:
                        AI2Actions.useSkill(this, 2855);
                        spawnAlquimiaZoiks();
                        break;
                    case 75:
                        AI2Actions.useSkill(this, 16906);
                        spawnAlquimiaZoiks();
                        break;
                    case 70:
                        AI2Actions.useSkill(this, 2834);
                        spawnAlquimiaZoiks();
                        break;
                    case 60:
                        AI2Actions.useSkill(this, 2855);
                        spawnAlquimiaZoiks();
                        break;
                    case 50:
                        AI2Actions.useSkill(this, 2843);
                        spawnAlquimiaZoiks();
                        break;
                    case 40:
                        AI2Actions.useSkill(this, 2855);
                        spawnAlquimiaZoiks();
                        break;
                    case 30:
                        AI2Actions.useSkill(this, 2843);
                        spawnAlquimiaZoiks();
                        break;
                    case 20:
                        AI2Actions.useSkill(this, 16508);
                        spawnAlquimiaZoiks();
                        break;
                    case 10:
                        AI2Actions.useSkill(this, 2843);
                        spawnAlquimiaZoiks();
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{90, 80, 70, 60, 50, 40, 30, 20 ,10});
    }

    private void spawnAlquimiaZoiks() {
        spawn(231761, 515.95404f, 493.25403f, 199.73503f, (byte) 29);
        spawn(231761, 516.43097f, 503.32056f, 199.73503f, (byte) 87);
        spawn(231761, 519.87946f, 500.02615f, 199.73503f, (byte) 61);
        spawn(231761, 519.87946f, 495.92548f, 199.73503f, (byte) 61);
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
        isHome.set(true);
        despawnAdds();
    }

    @Override
    protected void handleDespawned() {
        super.handleDespawned();
        percents.clear();
        despawnAdds();
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        percents.clear();
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