package instance;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Eloann
 */
@InstanceID(300030000)
public class NochsanaTrainingsCamp extends GeneralInstanceHandler {

    @Override
    public void onEnterInstance(final Player player) {
        ItemService.addItem(player, 182205676, 1); //Training Siege Weapon
    }

    @Override
    public void handleUseItemFinish(Player player, Npc npc) {
        switch (npc.getNpcId()) {
            case 700437: //Nochsana Artifact
                SkillEngine.getInstance().getSkill(npc, 1872, 10, player).useNoAnimationSkill();
                break;
        }
    }

    private void removeItems(Player player) {
        Storage storage = player.getInventory();
        storage.decreaseByItemId(182205676, storage.getItemCountByItemId(182205676));
    }

    @Override
    public void onLeaveInstance(Player player) {
        removeItems(player);
    }

    @Override
    public void onPlayerLogOut(Player player) {
        removeItems(player);
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);

        PacketSendUtility.sendPacket(player, new SM_DIE(false, false, 0, 8));
        return true;
    }
}
