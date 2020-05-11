package playercommands;

import com.aionemu.gameserver.model.gameobjects.Gatherable;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author Maestros
 */
public class cmd_userinfo extends PlayerCommand {

    public cmd_userinfo() {
        super("userinfo");
    }

    @Override
    public void execute(Player player, String... params) {
        VisibleObject target = player.getTarget();

        if (target instanceof Player) {
            PacketSendUtility.sendMessage(player, "Du darfst dir keine Infos von anderen Spielern holen");
        } else if (target instanceof Npc) {
            Npc npc = (Npc) player.getTarget();
            PacketSendUtility.sendMessage(player, "[NPC Info]" + "\nName: " + npc.getName() + "\nId: " + npc.getNpcId() + "\nMap ID: " + player.getTarget().getWorldId());
        } else if (target instanceof Gatherable) {
            Gatherable gather = (Gatherable) target;
            PacketSendUtility.sendMessage(player, "[Gather Info]\n" + "Name: " + gather.getName() + "\nId: " + gather.getObjectTemplate().getTemplateId() + "\nMap ID: "
                    + player.getTarget().getWorldId());
        }
    }

    @Override
    public void onFail(Player player, String message) {
        // TODO Auto-generated method stub
    }
}
