package ai.siege.anoha;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author yayaya
 */
@AIName("tank_anoha_siege")
public class TankAnohaAI2 extends ActionItemNpcAI2 {

    private AtomicBoolean canUse = new AtomicBoolean(true);

    @Override
    protected void handleUseItemFinish(Player player) {

        if (canUse.compareAndSet(true, false)) {
            int morphSkill = getMorphSkill();
            SkillEngine.getInstance().getSkill(getOwner(), morphSkill, 120, player).useNoAnimationSkill();
            AI2Actions.scheduleRespawn(this);
            AI2Actions.deleteOwner(this);
        }
    }

    private int getMorphSkill() {
        switch (getNpcId()) {
            case 234533: //Anoha Elyos
            case 234537: //Anoha Elyos
            case 702586: //Anoha Elyos
                return 21592;
            case 234534: //Anoha Asmodian
            case 234538: //Anoha Asmodian
            case 702587: //Anoha Asmodian
                return 21593;
        }
        return 0;
    }

    @Override
    protected AIAnswer pollInstance(AIQuestion question) {
        switch (question) {
            case SHOULD_REWARD:
                return AIAnswers.NEGATIVE;
            default:
                return super.pollInstance(question);
        }
    }
}
