package ai.defensiveCanon;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;
import com.aionemu.gameserver.skillengine.SkillEngine;

@AIName("elyosdefensivecanon")
public class ElyosDefensiveCanonAI2 extends ActionItemNpcAI2 {

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
		Npc owner = getOwner();
		player.getController().stopProtectionActiveTask();
		SkillEngine.getInstance().getSkill(player, 20364, 1, player).useNoAnimationSkill();
		AI2Actions.scheduleRespawn(this);
		AI2Actions.deleteOwner(this);
	}
}
