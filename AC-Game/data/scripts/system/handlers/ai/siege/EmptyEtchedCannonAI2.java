package ai.siege;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;

/**
 * @author Ranastic
 */

@AIName("emptyetchedcannon")
public class EmptyEtchedCannonAI2 extends ActionItemNpcAI2 {

	@Override
	protected void handleDialogStart(Player player) {
		if (player.getInventory().getItemCountByItemId(186000246) > 0) { // Magic Cannonball.
			super.handleUseItemStart(player);
		}
		else {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(2281763));
		}
	}

	@Override
	protected void handleUseItemFinish(Player player) {
		Npc owner = getOwner();
		player.getController().stopProtectionActiveTask();
		SkillEngine.getInstance().getSkill(player, 21386, 1, player).useNoAnimationSkill();
		AI2Actions.deleteOwner(this);
		AI2Actions.scheduleRespawn(this);
	}
}
