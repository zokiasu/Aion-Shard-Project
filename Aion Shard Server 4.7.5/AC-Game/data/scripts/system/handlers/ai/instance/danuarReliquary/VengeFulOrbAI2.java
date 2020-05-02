package ai.instance.danuarReliquary;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

/**
 * @Author Sayem
 */@AIName("vengefulorb")
public class VengeFulOrbAI2 extends NpcAI2 {

	private Future <? > task;

	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		final int skill;
		switch (getNpcId()) {
			case 284443:
				skill = 21178;
				break;
			default:
				skill = 0;
		}

		if (skill == 0)
			return;

		task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				AI2Actions.useSkill(VengeFulOrbAI2.this, skill);
			}
		}, 0, 2000);

		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				AI2Actions.deleteOwner(VengeFulOrbAI2.this);
			}
		}, 1000);
	}

	@Override
	public void handleDespawned() {
		task.cancel(true);
		super.handleDespawned();
	}
}