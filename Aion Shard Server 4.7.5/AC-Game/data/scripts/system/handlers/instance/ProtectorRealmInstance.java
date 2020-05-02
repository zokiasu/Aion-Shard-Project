package instance;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Eloann
 */
@InstanceID(300330000)
public class ProtectorRealmInstance extends GeneralInstanceHandler {

    @Override
    public void onDie(Npc npc) {
        switch (npc.getObjectTemplate().getTemplateId()) {
            case 218760:
            case 218761:
            case 218762:
                despawnNpc(npc);
                break;
        }
    }

    @Override
    public boolean onReviveEvent(Player player) {
        PlayerReviveService.revive(player, 100, 100, false, 0);
        player.getGameStats().updateStatsAndSpeedVisually();
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
        return TeleportService2.teleportTo(player, mapId, instanceId, 241.03264f, 125.24156f, 125.24156f, (byte) 106);
    }

    private void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }
}
