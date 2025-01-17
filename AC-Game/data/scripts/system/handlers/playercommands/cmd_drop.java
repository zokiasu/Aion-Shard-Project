package playercommands;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.drop.Drop;
import com.aionemu.gameserver.model.drop.DropGroup;
import com.aionemu.gameserver.model.drop.NpcDrop;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author Eloann
 */
public class cmd_drop extends PlayerCommand {

    public cmd_drop() {
        super("drop");
    }

    @Override
    public void execute(Player player, String... params) {
        NpcDrop npcDrop = null;
        if (params.length > 0) {
            int npcId = Integer.parseInt(params[0]);
            NpcTemplate npcTemplate = DataManager.NPC_DATA.getNpcTemplate(npcId);
            if (npcTemplate == null) {
                PacketSendUtility.sendMessage(player, "Incorrect npcId: " + npcId);
                return;
            }
            npcDrop = npcTemplate.getNpcDrop();
        } else {
            VisibleObject visibleObject = player.getTarget();

            if (visibleObject == null) {
                PacketSendUtility.sendMessage(player, "You have no target !");
                return;
            }

            if (visibleObject instanceof Player) {
                PacketSendUtility.sendMessage(player, "Your target must be a npc !");
                return;
            }

            if (visibleObject instanceof Npc) {
                npcDrop = ((Npc) visibleObject).getNpcDrop();
            }
        }
        if (npcDrop == null) {
            Npc npc = (Npc) player.getTarget();
            PacketSendUtility.sendMessage(player, "NPC ID :" + " " + npc.getNpcId() + " has no drops.");
            return;
        }

        int count = 0;
        PacketSendUtility.sendMessage(player, "[Mob Drops]\n");
        for (DropGroup dropGroup : npcDrop.getDropGroup()) {
            PacketSendUtility.sendMessage(player, "DropGroup: " + dropGroup.getGroupName());
            for (Drop drop : dropGroup.getDrop()) {
                if(player.isGM()) {
                    PacketSendUtility.sendMessage(player, "[item:" + drop.getItemId() + "]" + " Rate: " + drop.getChance());
                } else {
                    PacketSendUtility.sendMessage(player, "[item:" + drop.getItemId() + "]");// + " Rate: " + drop.getChance());
                }
                count++;
            }
        }

        PacketSendUtility.sendMessage(player, "There are " + count + " drops on NPC.");
        Npc npc = (Npc) player.getTarget();
        PacketSendUtility.sendMessage(player, "NpcId :" + " " + npc.getNpcId());
    }

    @Override
    public void onFail(Player player, String message) {
        // TODO Auto-generated method stub
    }
}
