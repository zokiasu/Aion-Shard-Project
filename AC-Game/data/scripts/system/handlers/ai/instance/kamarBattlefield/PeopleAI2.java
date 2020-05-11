package ai.instance.kamarBattlefield;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;


@AIName("people")
public class PeopleAI2 extends ActionItemNpcAI2 {

    private boolean isRewarded;

    @Override
    protected void handleDialogStart(Player player) {
        InstanceReward<?> instance = getPosition().getWorldMapInstance().getInstanceHandler().getInstanceReward();
        if (instance != null && !instance.isStartProgress()) {
            return;
        }
        super.handleDialogStart(player);
    }

    @Override
    protected void handleUseItemFinish(Player player) {
        if (!isRewarded) {
            isRewarded = true;
            AI2Actions.handleUseItemFinish(this, player);
            AI2Actions.deleteOwner(this);
        }
    }
}
