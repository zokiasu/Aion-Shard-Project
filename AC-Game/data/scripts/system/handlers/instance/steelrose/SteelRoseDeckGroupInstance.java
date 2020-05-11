package instance.steelrose;

import com.aionemu.commons.utils.Rnd;
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
 * @author Eloann
 */
@InstanceID(301050000)
public class SteelRoseDeckGroupInstance extends GeneralInstanceHandler {

    @Override
    public void onDie(Npc npc) {
        int npcId = npc.getNpcId();

        switch (npcId) {
            case 230743: //Captain Rumakiki
                spawn(730766, 428.06598f, 486.64233f, 1075.4449f, (byte) 0, 87); // Hidden Passage
                break;
        }
    }

    public void onDropRegistered(Npc npc) {
        Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
        int npcId = npc.getNpcId();
        switch (npcId) {
            case 701818: // Steel Rose Deck Barrel
            case 701819: // Steel Rose Deck Barrel
                switch (Rnd.get(1, 8)) {
                    case 1:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050009, 2)); // Steel Rose Idian: Physical Attack
                        break;
                    case 2:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050010, 2)); // Steel Rose Idian: Magical Attack
                        break;
                    case 3:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050011, 2)); // Steel Rose Idian: Physical Defense
                        break;
                    case 4:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050012, 2)); // Steel Rose Idian: Magical Defense
                        break;
                    case 5:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050013, 2)); // Glossy Steel Rose Idian: Physical Attack
                        break;
                    case 6:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050014, 2)); // Glossy Steel Rose Idian: Magical Attack
                        break;
                    case 7:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050015, 2)); // Glossy Steel Rose Idian: Physical Defense
                        break;
                    case 8:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050016, 2)); // Glossy Steel Rose Idian: Magical Defense
                        break;
                }
                break;
        }
    }

    @Override
    public void onPlayerLogOut(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }
}
