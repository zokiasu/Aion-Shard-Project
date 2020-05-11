package ai.instance.ophidanBridge;

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
 * @author Eloann
 */
@AIName("ophidan_bridge_cannon")
public class OphidanCannonAI2 extends ActionItemNpcAI2 {

    private AtomicBoolean canUse = new AtomicBoolean(true);

    @Override
    protected void handleUseItemFinish(Player player) {

        if (canUse.compareAndSet(true, false)) {
            int morphSkill = getMorphSkill();
            SkillEngine.getInstance().getSkill(getOwner(), morphSkill, 60, player).useNoAnimationSkill();
            AI2Actions.scheduleRespawn(this);
            AI2Actions.deleteOwner(this);
        }
    }

    private int getMorphSkill() {
        switch (getNpcId()) {
            case 701646: //elyos
                return 21434;
            case 701647: //asmodian
                return 21435;
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
