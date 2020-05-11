package admincommands;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

import java.io.IOException;

/**
 * @author Luno
 * @Reworked GiGatR00n v4.7.5.x
 */
public class SpawnNpc extends AdminCommand {

    public SpawnNpc() {
        super("spawn");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length < 1) {
            PacketSendUtility.sendMessage(admin, "syntax //spawn <template_id> <respawn_time> [<x> <y> <z> <h>] (0 for temp)");
            return;
        }

        int respawnTime = 295;
        int templateId = Integer.parseInt(params[0]);
        float x = admin.getX();
        float y = admin.getY();
        float z = admin.getZ();
        byte heading = admin.getHeading();
        int worldId = admin.getWorldId();

        if (params.length == 2) {
            respawnTime = Integer.valueOf(params[1]);
	        
        } else if (params.length == 6) {
            respawnTime = Integer.valueOf(params[1]);
	        x = Float.valueOf(params[2]);
	        y = Float.valueOf(params[3]);
	        z = Float.valueOf(params[4]);
	        heading = Byte.valueOf(params[5]);
        }
        
        SpawnTemplate spawn = SpawnEngine.addNewSpawn(worldId, templateId, x, y, z, heading, respawnTime);

        if (spawn == null) {
            PacketSendUtility.sendMessage(admin, "There is no template with id " + templateId);
            return;
        }

        VisibleObject visibleObject = SpawnEngine.spawnObject(spawn, admin.getInstanceId());

        if (visibleObject == null) {
            PacketSendUtility.sendMessage(admin, "Spawn id " + templateId + " was not found!");
            return;
        } else if (respawnTime > 0) {
            try {
                DataManager.SPAWNS_DATA2.saveSpawn(admin, visibleObject, false);
            } catch (IOException e) {
                e.printStackTrace();
                PacketSendUtility.sendMessage(admin, "Could not save spawn");
            }
        }

        String objectName = visibleObject.getObjectTemplate().getName();
        PacketSendUtility.sendMessage(admin, objectName + " spawned");
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //spawn <template_id> <respawn_time> (0 for temp)");
    }
}
