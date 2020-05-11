package ai.worlds.idian_depth;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Creature;

/**
 * @rework Ever
 */
@AIName("forgottengrave")
public class ForgottenGraveAI2 extends NpcAI2 {

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        int owner = getOwner().getNpcId();
        int spawnNpc = 0;
        switch (owner) {
            case 230862:
                spawnNpc = 283906;
                break;
        }
        spawn(spawnNpc, getOwner().getSpawn().getX(), getOwner().getSpawn().getY(), getOwner().getSpawn().getZ(), getOwner().getSpawn().getHeading());
		AI2Actions.deleteOwner(this);
		AI2Actions.scheduleRespawn(this);
    }
}
