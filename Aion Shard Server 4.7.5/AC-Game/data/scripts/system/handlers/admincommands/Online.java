package admincommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author VladimirZ
 */
public class Online extends AdminCommand {

    public Online() {
        super("online");
    }

    @Override
    public void execute(Player admin, String... params) {

        int playerCount = DAOManager.getDAO(PlayerDAO.class).getOnlinePlayerCount();

        if (playerCount == 1) {
            PacketSendUtility.sendMessage(admin, "There is " + (playerCount) + " player online !");
        } else {
            PacketSendUtility.sendMessage(admin, "There are " + (playerCount) + " players online !");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax: //online");
    }
}
