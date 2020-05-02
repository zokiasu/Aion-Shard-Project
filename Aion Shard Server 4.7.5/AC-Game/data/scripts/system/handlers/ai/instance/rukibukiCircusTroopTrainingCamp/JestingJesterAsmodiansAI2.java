package ai.instance.rukibukiCircusTroopTrainingCamp;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Ranastic
 */
@AIName("jestingjesterasmodians")
public class JestingJesterAsmodiansAI2 extends NpcAI2
{
	@Override
	protected void handleDialogStart(Player player) {
		PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
	}
	
	@Override
	public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
		if (dialogId == 10000) {
			SkillEngine.getInstance().applyEffectDirectly(21334, player, player, 3600000 * 3);
		}
		PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 0));
		return true;
	}
}