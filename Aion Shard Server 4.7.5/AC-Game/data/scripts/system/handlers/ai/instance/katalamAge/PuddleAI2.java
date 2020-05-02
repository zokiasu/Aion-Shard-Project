package ai.instance.katalamAge;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author zxl001
 */
@AIName("puddle")
public class PuddleAI2 extends NpcAI2 {

	@Override
	protected void handleCreatureSee(Creature creature) {
		if (creature instanceof Player) {
			final Player player = (Player) creature;
			SkillEngine.getInstance().getSkill(getOwner(), 21356, 56, player).useNoAnimationSkill();
		}
	}
}
