package ai.worlds.kataramunderground;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;

import ai.NoActionAI2;


/**
 * @author zxl001
 */
@AIName("coffin")
public class CoffinAI2 extends NoActionAI2 {
	
	@Override
	protected void handleDied() {
		super.handleDied();
		spawn(284262, 382.3388f, 892.4802f, 559.375f, (byte) 1);
		getOwner().getController().delete();
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		Npc npc = getOwner().getPosition().getWorldMapInstance().getNpc(284262);
		if (npc != null && npc.isSpawned())
			npc.getController().delete();
	}

	@Override
	public int modifyDamage(int damage) {
		return 1;
	}
}
