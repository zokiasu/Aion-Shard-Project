package ai.events;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.autogroup.AutoGroupType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FIND_GROUP;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * Author Eloann
 */
@AIName("nightmare_circus")
public class NightmareCircusAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 10));
    }

    @Override
    public boolean onDialogSelect(Player player, final int dialogId, int questId, int extendedRewardIndex) {
        switch (dialogId) {
            case 105:
                AutoGroupType agt = AutoGroupType.getAutoGroup(player.getLevel(), getNpcId());
                if (agt != null) {
                    PacketSendUtility.sendPacket(player, new SM_FIND_GROUP(0x1A, agt.getInstanceMapId()));
                }
                break;
        }
        return true;
    }
}
