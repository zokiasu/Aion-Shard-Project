package admincommands;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Eloann
 * @modified GiGatR00n
 */
public class AddTitle extends AdminCommand {

    public AddTitle() {
        super("addtitle");
    }

    @Override
    public void execute(Player admin, String... params) {
        if ((params.length < 1) || (params.length > 2)) {
            onFail(admin, null);
            return;
        }

        int titleId = Integer.parseInt(params[0]);
        if ((titleId > 286) || (titleId < 1)) {
            PacketSendUtility.sendMessage(admin, "title id " + titleId + " is invalid (must be between 1 and 286)");
            return;
        }

        VisibleObject target = admin.getTarget();

        if (target == null) {
            PacketSendUtility.sendMessage(admin, "No target selected");
            return;
        }

        if (target instanceof Player) {
            Player player = (Player) target;

            boolean sucess = false;

            try {
                if (params.length == 2) {
                    int expireMinutes = Integer.parseInt(params[1]);
                    sucess = player.getTitleList().addTitle(titleId, true, expireMinutes);
                } else {
                    sucess = player.getTitleList().addTitle(titleId, true, 0);
                }
            } catch (NumberFormatException ex) {
                PacketSendUtility.sendMessage(admin, "Missing integer");
                return;
            }

            if (sucess) {
                PacketSendUtility.sendMessage(admin, "Title added!");
            } else {
                PacketSendUtility.sendMessage(admin, "You can't add this title");
            }
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //addtitle <title_id> [expire time]");
    }
}
