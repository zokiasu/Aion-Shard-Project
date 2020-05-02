package ai.instance.rentusBase;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * 
 * @author Ranastic
 *
 */

@AIName("inhibitorsikar")
public class InhibitorSikarAI2 extends AggressiveNpcAI2
{
	@Override
	public boolean canThink() {
		return false;
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				SkillEngine.getInstance().getSkill(getOwner(), 19657, 1, getOwner()).useNoAnimationSkill(); //Dragon Breath.
				startLifeTask();
			}
		}, 1000);
	}
	
	private void startLifeTask() {
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				AI2Actions.deleteOwner(InhibitorSikarAI2.this);
			}
		}, 15000);
	}
	
	@Override
	public AIAnswer ask(AIQuestion question) {
		switch (question) {
			case CAN_RESIST_ABNORMAL:
				return AIAnswers.POSITIVE;
			default:
				return AIAnswers.NEGATIVE;
		}
	}
	
	@Override
	protected void handleDied() {
		super.handleDied();
		AI2Actions.deleteOwner(this);
	}
}