package instance.abyss;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.flyring.FlyRing;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.flyring.FlyRingTemplate;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

import java.util.List;

/**
 * @author bobobear
 */
@InstanceID(300070000)
public class RoahInstance extends GeneralInstanceHandler {

    private boolean isStartTimer = false;
    private long startTime;
    private boolean isInstanceDestroyed = false;
    private Race instanceRace;

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        spawnRings();
        spawnGoldChest();
    }

    private void spawnRings() {
        FlyRing f1 = new FlyRing(new FlyRingTemplate("ROAH_WING_1", mapId,
                new Point3D(501.77, 409.53, 94.12),
                new Point3D(503.93, 409.65, 98.9),
                new Point3D(506.26, 409.7, 94.15), 10), instanceId);
        f1.spawn();
    }

    @Override
    public boolean onPassFlyingRing(Player player, String flyingRing) {
        if (flyingRing.equals("ROAH_WING_1")) {
            if (!isStartTimer) {
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INSTANCE_START_IDABRE);
                isStartTimer = true;
                startTime = System.currentTimeMillis();
                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 900));
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        despawnNpcs(getNpcs(700472));
                        despawnNpcs(getNpcs(700473));
                        despawnNpcs(getNpcs(700474));
                        despawnNpcs(getNpcs(701489));
                        despawnNpcs(getNpcs(701484));
                    }
                }, 900000);
            }
        }
        return false;
    }

    @Override
    public void onEnterInstance(Player player) {
        if (isStartTimer) {
            long time = System.currentTimeMillis() - startTime;
            if (time < 900000) {
                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 900 - (int) time / 1000));
            }
        }

    }

    private List<Npc> getNpcs(int npcId) {
        if (!isInstanceDestroyed) {
            return instance.getNpcs(npcId);
        }
        return null;
    }

    private void despawnNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            npc.getController().onDelete();
        }
    }

    private void spawnGoldChest() {
        final int chestId = instanceRace == Race.ELYOS ? 701484 : 701489;
        spawn(chestId, 504.203225f, 503.309275f, 88.97879f, (byte) 32);
    }

    @Override
    public void onInstanceDestroy() {
        isInstanceDestroyed = true;
    }

    @Override
    public void onPlayerLogOut(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }
}
