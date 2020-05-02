package ai.instance.idgelResearchCenter;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import java.util.concurrent.Future;


@AIName("fire_crown")
public class FireCrownAI2 extends NpcAI2 {

	private Future<?> task;

	@Override
	protected void handleSpawned() {

		super.handleSpawned();
		final int skill = getOwner().getNpcId() == 284642 ? 21127 : 21128;
		int delay = getNpcId() == 284642 ? 500 : 2000;
		task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				AI2Actions.useSkill(FireCrownAI2.this, skill);
			}
		}, delay, delay);

	}


	@Override
	protected AIAnswer pollInstance(AIQuestion question) {
		switch (question) {
			case SHOULD_DECAY:
				return AIAnswers.NEGATIVE;
			case SHOULD_RESPAWN:
				return AIAnswers.NEGATIVE;
			case SHOULD_REWARD:
				return AIAnswers.NEGATIVE;
			default:
				return null;
		}
	}
}
