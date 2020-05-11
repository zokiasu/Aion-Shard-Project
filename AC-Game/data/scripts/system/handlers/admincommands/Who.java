package admincommands;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

import java.util.Collection;

public class Who extends AdminCommand {

    public Who() {
        super("who");
    }

    @Override
    public void execute(Player admin, String... params) {

        Collection<Player> players = World.getInstance().getAllPlayers();

        PacketSendUtility.sendMessage(admin, "Player :");

        for (Player player : players) {
            if (params != null && params.length > 0) {
                String cmd = params[0].toLowerCase();

                if (("ely").startsWith(cmd)) {
                    if (player.getCommonData().getRace() == Race.ASMODIANS) {
                        continue;
                    }
                }

                if (("asmo").startsWith(cmd)) {
                    if (player.getCommonData().getRace() == Race.ELYOS) {
                        continue;
                    }
                }

                if (("member").startsWith(cmd) || ("premium").startsWith(cmd)) {
                    if (player.getPlayerAccount().getMembership() == 0) {
                        continue;
                    }
                }
            }

            PacketSendUtility.sendMessage(admin, "Char: " + player.getName() + " - Race: " + player.getCommonData().getRace().name() + " - Acc: " + player.getAcountName());
        }
    }
}
