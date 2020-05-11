package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemRemodelService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.dataholders.DataManager;

/**
 * @author Kashim
 */
public class cmd_preview extends PlayerCommand {

    private static final int REMODEL_PREVIEW_DURATION = 15;

    public cmd_preview() {
        super("preview");
    }

    public void executeCommand(Player admin, String[] params) {

        if (params.length < 1 || params[0] == "") {
            PacketSendUtility.sendMessage(admin, "Syntax: .preview <itemid>");
            return;
        }

        int itemId = 0;
        try {
            itemId = Integer.parseInt(params[0]);
        } catch (Exception e) {
            PacketSendUtility.sendMessage(admin, "Error! Item id's are numbers like 187000090 or [item:187000090]!");
            return;
        }

        if (DataManager.ITEM_DATA.getItemTemplate(itemId) == null) {
            PacketSendUtility.sendMessage(admin, "Item id is incorrect: " + itemId);
            return;
        }

        ItemRemodelService.commandPreviewRemodelItem(admin, itemId, REMODEL_PREVIEW_DURATION);
    }

    @Override
    public void execute(Player player, String... params) {
        executeCommand(player, params);
    }
}
