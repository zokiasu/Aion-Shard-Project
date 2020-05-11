package ai.instance.unstableSplinterpath;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Luzien, Ritsu
 * @edit Cheatkiller
 */
@AIName("unstabledayshade")
public class UnstableDayshadeAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            AI2Actions.dieSilently(this, creature);
            spawn(281898, 455.5502f, 702.09485f, 433.13727f, (byte) 108); // ebonsoul
            spawn(281897, 447.1937f, 683.72217f, 433.1805f, (byte) 108); // rukril
            AI2Actions.deleteOwner(UnstableDayshadeAI2.this);
        }
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        isHome.set(true);
    }
}
