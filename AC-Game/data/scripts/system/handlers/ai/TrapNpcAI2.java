package ai;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author ATracer
 * @modified Kashim
 * @Reworked Kill3r
 */
@AIName("trap")
public class TrapNpcAI2 extends NpcAI2 {

    public static int EVENT_SET_TRAP_RANGE = 1;
    @SuppressWarnings("unused")
    private int trapRange = 0;

    @Override
    protected void handleCreatureMoved(Creature creature) {
        tryActivateTrap(creature);
    }

    @Override
    protected void handleSpawned() {
        getKnownList().doUpdate();
        getKnownList().doOnAllObjects(new Visitor<VisibleObject>() {
            @Override
            public void visit(VisibleObject object) {
                if (!(object instanceof Creature)) {
                    return;
                }
                Creature creature = (Creature) object;
                tryActivateTrap(creature);
            }
        });
        super.handleSpawned();
    }

    private void tryActivateTrap(Creature creature) {
        //if (despawnTask != null){
        //    return;
        //}
        int time = 1000;
        if (this.getNpcId() == 749248 || this.getNpcId() == 749249) {
            if (setStateIfNot(AIState.FIGHT)) {
                AI2Actions.targetCreature(this, creature);
                AI2Actions.useSkill(this, getSkillList().getRandomSkill().getSkillId());
                ThreadPoolManager.getInstance().schedule(new TrapDelete(this), 4500);
            }
        }
        if (!creature.getLifeStats().isAlreadyDead() && isInRange(creature, getOwner().getAggroRange() + 2)) {

            Creature creator = (Creature) getCreator();
            if (!creator.isEnemy(creature)) {
                return;
            }

            int npcId = this.getNpcId();

            if (this.getNpcId() == 749248 || this.getNpcId() == 749249 || this.getNpcId() == 749250 || this.getNpcId() == 749251) {
                time = 5000;
            }

            if (setStateIfNot(AIState.FIGHT)) {
                AI2Actions.targetCreature(this, creature);
                AI2Actions.useSkill(this, getSkillList().getRandomSkill().getSkillId());
                ThreadPoolManager.getInstance().schedule(new TrapDelete(this), time);
            }
        }
    }

    /**

    @Override
    protected void handleCustomEvent(int eventId, Object... args) {
        if (eventId == EVENT_SET_TRAP_RANGE) {
            trapRange = (Integer) args[0];
        }
    }
     */

    @Override
    public boolean isMoveSupported() {
        return false;
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

    private static final class TrapDelete implements Runnable {

        private TrapNpcAI2 ai;

        TrapDelete(TrapNpcAI2 ai) {
            this.ai = ai;
        }

        @Override
        public void run() {
            AI2Actions.deleteOwner(ai);
            ai = null;
        }
    }
}
