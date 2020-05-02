package ai.instance.danuarMysticarium;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

/**
 * @author Ranastic
 */
@AIName("pashid_lab_unit_sentinel")
public class PashidLabUnitSentinelAI2 extends AggressiveNpcAI2 {

	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		int lifetime = (getNpcId() == 230077 ? 20000 : 600000);
		toDespawn(lifetime);
	}

	@Override
	protected void handleDied() {
		super.handleDied();
		AI2Actions.deleteOwner(PashidLabUnitSentinelAI2.this);
	}

	private void toDespawn(int delay) {
		ThreadPoolManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				AI2Actions.deleteOwner(PashidLabUnitSentinelAI2.this);
			}
		}, delay);
	}
}
