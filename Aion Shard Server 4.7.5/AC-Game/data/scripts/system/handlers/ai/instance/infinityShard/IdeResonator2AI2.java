package ai.instance.infinityShard;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

/**
 * @author DeathMagnestic
 */
@AIName("ideres2")
public class IdeResonator2AI2 extends NpcAI2 {

    private Future<?> skillTask;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
    }

    private void startpower() {
        /*skillTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                AI2Actions.targetCreature(IdeResonator2AI2.this, getPosition().getWorldMapInstance().getNpc(231073));
                AI2Actions.useSkill(IdeResonator2AI2.this, 21383);
            }
        }, 3000, 5000);*/
    }

    private void cancelskillTask() {
        if (skillTask != null && !skillTask.isCancelled()) {
            skillTask.cancel(true);
        }
    }

    @Override
    protected void handleDied() {
        cancelskillTask();
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        cancelskillTask();
        super.handleDespawned();
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
