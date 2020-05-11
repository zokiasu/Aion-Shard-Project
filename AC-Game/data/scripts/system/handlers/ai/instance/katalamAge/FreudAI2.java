package ai.instance.katalamAge;

import java.util.concurrent.Future;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author zxl001
 */
@AIName("freud")
public class FreudAI2 extends AggressiveNpcAI2 {

	private Future<?> task;

	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		if (task == null)
			startTask();
	}

	@Override
	protected void handleDied() {
		super.handleDied();
		if (task != null)
			task.cancel(true);
	}

	@Override
	protected void handleDespawned() {
		super.handleDespawned();
		if (task != null)
			task.cancel(false);
	}

	private void startTask() {
		task = ThreadPoolManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				Npc bossNpc = getPosition().getWorldMapInstance().getNpc(231073);
				if (bossNpc != null && !bossNpc.getLifeStats().isAlreadyDead()) {
					SkillEngine.getInstance().getSkill(getOwner(), 21371, 56, bossNpc).useSkill();
					SkillEngine.getInstance().getSkill(getOwner(), 21258, 56, bossNpc).useSkill();
					buffTask();
				}
			}
		}, 30000);
	}
	
	private void buffTask() {
		task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				startBuff();
			}
		}, 15000, 15000);
	}

	private void startBuff() {
		Npc bossNpc = getPosition().getWorldMapInstance().getNpc(231073);
		if (bossNpc != null && !bossNpc.getLifeStats().isAlreadyDead()) {
			SkillEngine.getInstance().getSkill(getOwner(), 21257, 56, bossNpc).useNoAnimationSkill();
		}
	}
}
