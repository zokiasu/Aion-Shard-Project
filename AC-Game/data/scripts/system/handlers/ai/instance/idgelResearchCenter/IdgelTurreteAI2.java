package ai.instance.idgelResearchCenter;


import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.concurrent.atomic.AtomicBoolean;


@AIName("turret_idgellab")
public class IdgelTurreteAI2 extends ActionItemNpcAI2 {
	
	private AtomicBoolean canUse = new AtomicBoolean(true);
  
	@Override
	public void handleUseItemFinish(Player player) {
		
		  if (canUse.compareAndSet(true, false)) { 
		  SkillEngine.getInstance().applyEffectDirectly(21123, player, player, 60000 * 3);
			PacketSendUtility.sendBrightYellowMessageOnCenter(player, "You sat the Turrete Vritra.");
			stopMove(player);
			AI2Actions.scheduleRespawn(this);
			AI2Actions.deleteOwner(this);
		}
	}
	
	private void stopMove(Player player) {
		if (player != null) {
			player.getController().onStopMove();
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