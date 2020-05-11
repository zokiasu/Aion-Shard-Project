package ai.instance.abyssal_splinter;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

/**
 * @author Eloann
 */
@AIName("pazuzuworm")
public class PazuzuWormAI2 extends AggressiveNpcAI2 {

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                AI2Actions.targetCreature(PazuzuWormAI2.this, getPosition().getWorldMapInstance().getNpc(219942));
                AI2Actions.useSkill(PazuzuWormAI2.this, 19291);
            }
        }, 3000);
    }
}
