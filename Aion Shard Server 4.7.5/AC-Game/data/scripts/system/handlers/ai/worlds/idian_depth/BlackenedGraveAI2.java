package ai.worlds.idian_depth;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * @author Eloann
 * @rework Sayem
 */

@AIName("blackened_grave")
public class BlackenedGraveAI2 extends NpcAI2 {
    @Override
	protected void handleDied() {
		final WorldPosition position = getPosition();
		if (position != null) {
			//Spawn Executioner Penemon after death.
			spawn(284262, position.getX(), position.getY(), position.getZ(), (byte) 0);
		}
		super.handleDied();
		AI2Actions.deleteOwner(this);
		AI2Actions.scheduleRespawn(this);
	}
	
	@Override
	//Graveyard 1 DMG p. H. modifier.
    public int modifyDamage(int damage) {
        return super.modifyDamage(1);
    }
}