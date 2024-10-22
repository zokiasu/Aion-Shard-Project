package ai.instance.shugoImperialTomb;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

/**
 * @author Swig
 */
@AIName("defensetower") //831130, 831250, 831251
public class DefenseTowerAI2 extends AggressiveNpcAI2 {

    private Future<?> task;

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage > 50 && hpPercentage <= 100) {
            SkillEngine.getInstance().applyEffectDirectly(21097, getOwner(), getOwner(), 0);
        }
        if (hpPercentage > 25 && hpPercentage <= 50) {
            SkillEngine.getInstance().applyEffectDirectly(21098, getOwner(), getOwner(), 0);
        }
        if (hpPercentage >= 0 && hpPercentage <= 25) {
            SkillEngine.getInstance().applyEffectDirectly(21099, getOwner(), getOwner(), 0);
        }
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        SkillEngine.getInstance().applyEffectDirectly(21097, getOwner(), getOwner(), 0);
        task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                AI2Actions.useSkill(DefenseTowerAI2.this, 20954);
            }
        }, 2000, 2000);
    }

    @Override
    public void handleDespawned() {
        task.cancel(true);
        super.handleDespawned();
    }

    @Override
    public void handleBackHome() {
    }

    @Override
    public int modifyDamage(int damage) {
        return 1;
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
