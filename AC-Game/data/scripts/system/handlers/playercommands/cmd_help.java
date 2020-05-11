package playercommands;

import com.aionemu.gameserver.cache.HTMLCache;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.HTMLService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author Imaginary
 */
public class cmd_help extends PlayerCommand {

    public cmd_help() {
        super("help");
    }

    @Override
    public void execute(Player player, String... params) {
        HTMLService.showHTML(player, HTMLCache.getInstance().getHTML("pcommands.xhtml"));
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax: .help");
    }
}
