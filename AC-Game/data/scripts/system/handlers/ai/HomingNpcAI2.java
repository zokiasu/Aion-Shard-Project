package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AttackIntention;
import com.aionemu.gameserver.ai2.manager.AttackManager;
import com.aionemu.gameserver.ai2.manager.SkillAttackManager;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.skill.NpcSkillEntry;
import com.aionemu.gameserver.model.gameobjects.Homing;

/**
 * @author ATracer
 */
@AIName("homing")
public class HomingNpcAI2 extends GeneralNpcAI2 {

    @Override
    public void think() {
        // homings are not thinking to return :)
    }

    @Override
    public AttackIntention chooseAttackIntention() {
        return AttackIntention.SIMPLE_ATTACK;
    }

    @Override
    protected void handleAttackComplete() {
        super.handleAttackComplete();
        Homing owner = (Homing) getOwner();
        if (owner.getActiveSkillId() != 0) {
            AttackManager.scheduleNextAttack(this);
        }
    }

    @Override
    protected AIAnswer pollInstance(AIQuestion question) {
        switch (question) {
            case SHOULD_DECAY:
                return AIAnswers.NEGATIVE;
            case SHOULD_RESPAWN:
                return AIAnswers.NEGATIVE;
            case SHOULD_REWARD:
                return AIAnswers.NEGATIVE;
            default:
                return null;
        }
    }
}
