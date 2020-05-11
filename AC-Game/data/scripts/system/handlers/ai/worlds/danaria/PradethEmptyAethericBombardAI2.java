package ai.worlds.danaria;

import ai.ActionItemNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;
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

@AIName("pradethemptyaethericbombard")
public class PradethEmptyAethericBombardAI2 extends ActionItemNpcAI2 {

	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		ThreadPoolManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				startLifeTask();
			}
		}, 1000);
	}

	private void startLifeTask() {
		ThreadPoolManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				AI2Actions.deleteOwner(PradethEmptyAethericBombardAI2.this);
			}
		}, 7200000);
	}

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
		SkillEngine.getInstance().getSkill(player, 21385, 1, player).useNoAnimationSkill();
		AI2Actions.deleteOwner(this);
	}
}
