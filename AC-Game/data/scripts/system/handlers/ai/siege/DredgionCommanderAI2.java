package ai.siege;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author Luzien
 */
@AIName("dredgionCommander")
public class DredgionCommanderAI2 extends SiegeNpcAI2 {

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        scheduleOneShot();
    }

    private int getSkill() {
        switch (getNpcId()) {
			case 258236:
				return 18428;
			case 272291:
			case 272292:
			case 272293:
			case 272294:
			case 272295:
				return 21312;	
            case 276649:
			case 276650:
			case 276651:
                return 17572;
            case 276871:
            case 276872:
			case 276873:
				return 18411;
            default:
                return 0;
        }
    }

    private void scheduleOneShot() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (getSkill() != 0) {
                    if (getTarget() instanceof Npc) {
                        Npc target = (Npc) getTarget();
                        Race race = target.getRace();
                        if ((race.equals(Race.GCHIEF_DARK) || race.equals(Race.GCHIEF_LIGHT)) && !target.getLifeStats().isAlreadyDead()) {
                            AI2Actions.useSkill(DredgionCommanderAI2.this, getSkill());
                            getAggroList().addHate(target, 10000);
                        }
                    }
                    scheduleOneShot();
                }
            }
        }, 45 * 1000);
    }
}
