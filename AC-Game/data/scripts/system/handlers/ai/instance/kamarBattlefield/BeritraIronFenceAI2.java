package ai.instance.kamarBattlefield;

import ai.NoActionAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;


@AIName("beritraironfence")
public class BeritraIronFenceAI2 extends NoActionAI2 {

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    public void handleDied() {
        super.handleDied();
        AI2Actions.deleteOwner(this);
    }
}
