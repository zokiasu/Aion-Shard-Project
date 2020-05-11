package admincommands;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldMapType;

/**
 * @rework Eloann
 */
public class Eye extends AdminCommand {

    public Eye() {
        super("eye");
    }

    @Override
    public void execute(Player player, String... params) {
        if (params.length != 0) {
            onFail(player, null);
            return;
        }
        if (player.isAttackMode()) {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.NOT_USE_WHILE_FIGHT));
            return;
        }
        if (player.getPosition().getMapId() == WorldMapType.TIAMARANTA_EYE_2.getId()) {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.NOT_USE_ON_PVP_MAP));
            return;
        }
        if (player.getLevel() > 1 && player.getLevel() < 55) {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.LEVEL_TOO_LOW));
            return;
        }

        if (player.getRace() == Race.ELYOS && !player.isInPrison()) {
            goTo(player, WorldMapType.TIAMARANTA_EYE_2.getId(), 753, 134, 1196);
        } else if (player.getRace() == Race.ASMODIANS && !player.isInPrison()) {
            goTo(player, WorldMapType.TIAMARANTA_EYE_2.getId(), 754, 1459, 1196);
        }
    }

    private static void goTo(final Player player, int worldId, float x, float y, float z) {
        WorldMap destinationMap = World.getInstance().getWorldMap(worldId);
        if (destinationMap.isInstanceType()) {
            TeleportService2.teleportTo(player, worldId, getInstanceId(worldId, player), x, y, z);
        } else {
            TeleportService2.teleportTo(player, worldId, x, y, z);
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

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax: .eye");
    }
}
