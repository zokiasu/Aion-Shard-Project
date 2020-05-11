package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author Maestross
 */
public class cmd_exchange extends PlayerCommand {

    public cmd_exchange() {
        super("exchange");
    }

    @Override
    public void execute(Player player, String... params) {
        int ap = 15000;
        int derived = 186000147;
        int derived_q = 5;
        if (player.getAbyssRank().getAp() < ap) {
            PacketSendUtility.sendMessage(player, "Du hast nicht genug AP, du hast nur: " + ap);
            return;
        }
        ItemService.addItem(player, derived, derived_q);
        AbyssPointsService.addAp(player, -ap);

    }
}
