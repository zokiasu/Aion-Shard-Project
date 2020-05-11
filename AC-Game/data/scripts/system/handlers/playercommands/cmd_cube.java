package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.CubeExpandService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;

/**
 * @author Kamui, Maestros
 */
public class cmd_cube extends PlayerCommand {

    public cmd_cube() {
        super("cube");
    }

    @Override
    public void execute(Player player, String... params) {
        if (player.getNpcExpands() >= 12) {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.CUBE_ALLREADY_EXPANDED));
            return;
        }
        while (player.getNpcExpands() < 12) {
            CubeExpandService.expand(player, true);
        }
        PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.CUBE_SUCCESS_EXPAND));
    }

    @Override
    public void onFail(Player admin, String message) {
        PacketSendUtility.sendMessage(admin, "Syntax : .cube");
    }
}
