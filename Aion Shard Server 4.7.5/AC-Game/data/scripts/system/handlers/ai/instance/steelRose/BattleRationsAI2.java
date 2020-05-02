package ai.instance.steelRose;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;

@AIName("battle_rations") //730770
public class BattleRationsAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleUseItemFinish(Player player) {
        switch (getNpcId()) {
            case 730770:
                player.getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.HP, 5000);
                AI2Actions.deleteOwner(this);
                break;
        }
    }
}
