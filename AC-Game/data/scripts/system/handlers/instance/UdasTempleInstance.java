package instance;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

import java.util.Map;

/**
 * @author Antraxx
 */
@InstanceID(300150000)
public class UdasTempleInstance extends GeneralInstanceHandler {

    private boolean isInstanceDestroyed;
    private Map<Integer, StaticDoor> doors;

    private void openVallakhanDoor() {
        if (isDead(215787) && isDead(215788) && isDead(215789) && isDead(215790)) {
            doors.get(99).setOpen(true);
        }
    }

    @Override
    public void onDie(Npc npc) {
        if (isInstanceDestroyed) {
            return;
        }
        switch (npc.getNpcId()) {
            case 215787: // Cota the Gatekeeper
            case 215788: // Kiya the Protector
            case 215789: // Vida the Protector
            case 215790: // Tala the Protector
                openVallakhanDoor();
                break;
            case 215783: // Nexus
                // spawn udas exit
                spawn(730255, 508.361f, 362.717f, 137f, (byte) 30);
        }
    }

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        doors = instance.getDoors();
        doors.get(99).setOpen(false);
        if (Rnd.get(1, 2) == 1) {
            spawn(215787, 778.537f, 661.278f, 134f, (byte) 78);
        } else {
            spawn(215787, 689.529f, 669.005f, 134f, (byte) 103);
        }
    }

    @Override
    public void onInstanceDestroy() {
        isInstanceDestroyed = true;
        doors.clear();
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }

    private boolean isDead(int npcId) {
        Npc npc = getNpc(npcId);
        return (npc == null || npc.getLifeStats().isAlreadyDead());
    }
}
