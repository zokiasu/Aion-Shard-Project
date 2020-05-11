package ai.instance.idgelResearchCenter;

import java.util.concurrent.atomic.AtomicBoolean;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;

import ai.ActionItemNpcAI2;

/**
 * @author zxl001
 */
@AIName("seallifted")
public class Idf5R2CannonAI2 extends ActionItemNpcAI2 {

	private AtomicBoolean canUse = new AtomicBoolean(true);

	@Override
	protected void handleUseItemFinish(Player player) {
		if (player.getInventory().getItemCountByItemId(186000218) == 0) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401937));
			return;
		}
		if (canUse.compareAndSet(true, false)) {
			SkillEngine.getInstance().getSkill(player, 21395, 1, player).useNoAnimationSkill();
			AI2Actions.deleteOwner(this);
		}
	}

	@Override
	protected AIAnswer pollInstance(AIQuestion question) {
		switch (question) {
			case SHOULD_REWARD:
				return AIAnswers.NEGATIVE;
			default:
				return super.pollInstance(question);
		}
	}
}
