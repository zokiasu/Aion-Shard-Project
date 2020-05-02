package ai.instance.nightmareCircus;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;

@AIName("event_pierro")
public class PierroAI2 extends NpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
    }

    @Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
        if (dialogId == 10000) {
            if (player.getRace().equals(Race.ASMODIANS)) {
                SkillEngine.getInstance().applyEffectDirectly(21334, player, player, 0);
            } else {
                SkillEngine.getInstance().applyEffectDirectly(21331, player, player, 0);
            }
        }
        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 0));
        return true;
    }
}
