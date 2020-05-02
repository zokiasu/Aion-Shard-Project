package ai.instance.nightmareCircus;

import ai.NoActionAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

@AIName("hard_iron_chain")
public class HardIronChainAI2 extends NoActionAI2 {

    @Override
    protected void handleDied() {
        super.handleDied();
        AI2Actions.deleteOwner(this);
    }
}
