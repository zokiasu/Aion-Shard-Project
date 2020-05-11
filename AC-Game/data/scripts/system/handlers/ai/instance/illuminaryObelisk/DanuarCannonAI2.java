package ai.instance.illuminaryObelisk;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import java.util.concurrent.atomic.AtomicBoolean;


@AIName("danuar_cannon")
public class DanuarCannonAI2 extends ActionItemNpcAI2 {
	
	private AtomicBoolean canUse = new AtomicBoolean(true);
  
	@Override
	public void handleUseItemFinish(Player player) {
		
		  if (canUse.compareAndSet(true, false)) { 
		  SkillEngine.getInstance().applyEffectDirectly(21511, player, player, 3600000);
		  stopMove(player);
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