package ai.worlds.inggison;

import ai.GeneralNpcAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

/**
 * @author Cheatkiller
 */
@AIName("hugeegg")
public class HugeEggAI2 extends GeneralNpcAI2 {

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    public int modifyDamage(int damage) {
        return 1;
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        if (Rnd.get(0, 100) < 50) {
            spawn(217097, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
            AI2Actions.deleteOwner(this);
        }
    }
}
