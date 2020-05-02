package instance;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

import java.util.Set;

/**
 * @author xTz
 */
@InstanceID(320110000)
public class AlquimiaResearchCenterInstance extends GeneralInstanceHandler {

    @Override
    public void onDropRegistered(Npc npc) {
        if (npc.getNpcId() == 283782) { // Gardien des clés Nimue
            Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
            dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npc.getNpcId(), 185000007, 1)); // Clé de sanctuaire de Léphariste
        }
        if (npc.getNpcId() == 283783) { // Scientifique Zoiks
            Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
            dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npc.getNpcId(), 185000006, 1)); // Clef du labo générateur Daeva
        }

    }

    @Override
    public void onDie(Npc npc) {
        switch (npc.getObjectTemplate().getTemplateId()) {
            case 283783:
                spawn(214132, 514.0324f, 499.51627f, 199.73499f, (byte) 0);
                break;
        }
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);

        PacketSendUtility.sendPacket(player, new SM_DIE(false, false, 0, 8));
        return true;
    }

    @Override
    public void onPlayerLogOut(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }
}
