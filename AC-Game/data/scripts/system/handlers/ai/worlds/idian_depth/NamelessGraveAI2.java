package ai.worlds.idian_depth;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;

/**
 * @rework Ever
 */
@AIName("namelessgrave")
public class NamelessGraveAI2 extends NpcAI2 {

    @Override
    protected void handleDied() {
        super.handleDied();
        int owner = getOwner().getNpcId();
        int spawnNpc = 0;
        switch (owner) {
            case 230984:
                spawnNpc = 283905;
                break;
        }
        spawn(spawnNpc, getOwner().getSpawn().getX(), getOwner().getSpawn().getY(), getOwner().getSpawn().getZ(), getOwner().getSpawn().getHeading());
		AI2Actions.deleteOwner(this);
		AI2Actions.scheduleRespawn(this);
    }
}
