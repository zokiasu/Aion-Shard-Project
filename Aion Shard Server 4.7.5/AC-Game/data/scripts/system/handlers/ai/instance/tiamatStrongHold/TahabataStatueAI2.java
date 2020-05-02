package ai.instance.tiamatStrongHold;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Cheatkiller
 */
@AIName("tahabatastatue")
public class TahabataStatueAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleUseItemFinish(Player player) {
        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), DialogAction.SELECT_ACTION_1352.id(), 0));
    }

    @Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
        if (dialogId == 10000) {
            TeleportService2.teleportTo(player, 300510000, 1062.7202f, 1069.358f, 786.4852f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
        }
        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 0));
        return true;
    }
}
