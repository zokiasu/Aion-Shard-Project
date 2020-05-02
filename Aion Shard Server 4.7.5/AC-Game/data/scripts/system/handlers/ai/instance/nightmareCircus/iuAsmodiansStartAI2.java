package ai.instance.nightmareCircus;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.portal.PortalPath;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.services.teleport.PortalService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;

@AIName("iu_asmodians_start")
public class iuAsmodiansStartAI2 extends NpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
    }

    @Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
        if (dialogId == 10000) {
            SkillEngine.getInstance().applyEffectDirectly(21332, player, player, 320000 * 3);
            PortalPath portalPath = DataManager.PORTAL2_DATA.getPortalDialog(getNpcId(), dialogId, player.getRace());
            if (portalPath != null) {
                PortalService.port(portalPath, player, getObjectId());
            }
        }
        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 0));
        return true;
    }
}
