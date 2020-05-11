package ai.instance.tallocsHollow;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

/**
 * @author Dr.Nism
 */
@AIName("komad_sentry")
public class KomadSentryAI2 extends AggressiveNpcAI2 {

	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		int lifetime = (getNpcId() == 281514 ? 20000 : 20000);
		toDespawn(lifetime);
	}
	
	private void toDespawn(int delay) {
		ThreadPoolManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				AI2Actions.deleteOwner(KomadSentryAI2.this);
			}
		}, delay);
	}
}
