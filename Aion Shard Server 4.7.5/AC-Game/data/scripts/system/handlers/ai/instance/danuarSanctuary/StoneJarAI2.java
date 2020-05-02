package ai.instance.danuarSanctuary;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;

@AIName("stone_jar")
public class StoneJarAI2 extends NpcAI2 {

	@Override
	protected void handleDied() {
            		super.handleDied();
            		if (Rnd.get(1, 3) == 1) {
			spawnRemains();
		}
	}
        
	private void spawnRemains() {
                spawn(284026, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
                AI2Actions.deleteOwner(this);
	}
        


}