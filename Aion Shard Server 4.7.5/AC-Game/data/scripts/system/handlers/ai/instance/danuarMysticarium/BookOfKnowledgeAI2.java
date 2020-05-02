package ai.instance.danuarMysticarium;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;

/**
 * @author Ranastic
 */

@AIName("bookofknowledge")
public class BookOfKnowledgeAI2 extends ActionItemNpcAI2 {

	private boolean isRewarded;

	@Override
	protected void handleDialogStart(Player player) {
		InstanceReward<?> instance = getPosition().getWorldMapInstance().getInstanceHandler().getInstanceReward();
		if (instance != null && !instance.isStartProgress()) {
			return;
		}
		super.handleDialogStart(player);
	}

	@Override
	protected void handleUseItemFinish(Player player) {
		if (!isRewarded) {
			isRewarded = true;
			AI2Actions.handleUseItemFinish(this, player);
			final int npcId = getNpcId();
			if (npcId == 831145 || // Danuar Mysticarium Book.
				npcId == 831146) { // Danuar Mysticarium Book.
			}
			AI2Actions.deleteOwner(this);
		}
	}
}
