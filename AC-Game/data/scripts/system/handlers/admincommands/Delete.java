package admincommands;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.model.templates.spawns.siegespawns.SiegeSpawnTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

import java.io.IOException;

/**
 * @author Luno
 */
public class Delete extends AdminCommand {

    public Delete() {
        super("delete");
    }

    @Override
    public void execute(Player player, String... params) {

        VisibleObject cre = player.getTarget();
        if (!(cre instanceof Npc)) {
            PacketSendUtility.sendMessage(player, "Wrong target");
            return;
        }
        Npc npc = (Npc) cre;
        SpawnTemplate template = npc.getSpawn();
        if (template.hasPool()) {
            PacketSendUtility.sendMessage(player, "Can't delete pooled spawn template");
            return;
        }
        if (template instanceof SiegeSpawnTemplate) {
            PacketSendUtility.sendMessage(player, "Can't delete siege spawn template");
            return;
        }
        npc.getController().onDelete();
        try {
            DataManager.SPAWNS_DATA2.saveSpawn(player, npc, true);
        } catch (IOException e) {
            e.printStackTrace();
            PacketSendUtility.sendMessage(player, "Could not remove spawn");
            return;
        }
        PacketSendUtility.sendMessage(player, "Spawn removed");
    }

    @Override
    public void onFail(Player player, String message) {
        // TODO Auto-generated method stub
    }
}
