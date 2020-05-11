package ai;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;

/**
 * @author boscar
 */
@AIName("blessed")
public class BlessedTotemAI2 extends GeneralNpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        switch (getNpcId()) {
            case 831990:
                super.handleDialogStart(player);
                break;
            default:
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
                break;
        }
    }

    @Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
        long badgeCount = ((Item) player.getInventory().getFirstItemByItemId(186000343) != null ? ((Item) player.getInventory().getFirstItemByItemId(186000343)).getItemCount() : 0);
        boolean blessedbuff = false;
        DialogAction finalAction = DialogAction.getActionByDialogId(dialogId);

        switch (finalAction) {
            case SETPRO1:
                if (badgeCount >= 1) {
                    blessedbuff = true;
                }
                break;
        }

        if (blessedbuff) {
            switch (getNpcId()) {
                case 831990:
                    SkillEngine.getInstance().getSkill(getOwner(), 21650, 1, player).useWithoutPropSkill(); // Prestigious Blessing
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 0));
                    break;
            }
        } else {
            PacketSendUtility.sendMessage(player, "Not enough Prestige Badge ...");
        }

        return true;
    }

}
