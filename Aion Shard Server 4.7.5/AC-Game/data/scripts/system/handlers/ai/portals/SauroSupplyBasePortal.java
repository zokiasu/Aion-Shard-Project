package ai.portals;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;


@AIName("sauro_entrance")
public class SauroSupplyBasePortal extends ActionItemNpcAI2 {

    private int entryItem = 186000236;
    private int entryCount = 3;

    @Override
    protected void handleUseItemFinish(Player player) {
        if (player.getInventory().getItemCountByItemId(entryItem) >= entryCount) {
            TeleportService2.teleportTo(player, 301130000, getInstanceId(301130000, player), 641.5419f, 176.81075f, 195.65363f, (byte) 28);
        } else {
            PacketSendUtility.sendMessage(player, "Du brauchst " + entryCount + "Medaillen des Kampfes um hier einzutreten!");
        }
    }

    private static int getInstanceId(int worldId, Player player) {
        if (player.getWorldId() == worldId) {
            WorldMapInstance registeredInstance = InstanceService.getRegisteredInstance(worldId, player.getObjectId());
            if (registeredInstance != null) {
                return registeredInstance.getInstanceId();
            }
        }
        WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(worldId);
        InstanceService.registerPlayerWithInstance(newInstance, player);
        return newInstance.getInstanceId();
    }
}
