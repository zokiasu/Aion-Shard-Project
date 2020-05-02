package ai.siege;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

/**
 * @author cheatkiller
 */
@AIName("guardtower")
public class GuardTowerAI2 extends NpcAI2 {

    private Future<?> task;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        getOwner().getObjectTemplate().setAttackRange(30);
    }

    @Override
    protected void handleCreatureSee(Creature creature) {
        checkDistance(this, creature);
    }

    @Override
    protected void handleCreatureNotSee(Creature creature) {
        if (task != null) {
            task = null;
        }
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        checkDistance(this, creature);
    }

    @SuppressWarnings("unused")
    private void checkDistance(NpcAI2 ai, Creature creature) {
        if (creature instanceof Player) {
            if (task == null) {
                if (MathUtil.isIn3dRange(this.getOwner(), creature, 30)) {
                    AI2Actions.targetCreature(this, creature);
                    attack();
                }
            }
        }
    }

    private void cancelTask() {
        if (task != null && !task.isCancelled()) {
            task.cancel(true);
        }
    }

    private void attack() {
        final Player p = (Player) getOwner().getTarget();
        task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (p == null || p.getLifeStats().isAlreadyDead()) {
                    cancelTask();
                }
                getOwner().getController().useSkill(getSkillList().getRandomSkill().getSkillId(), 55);
            }
        }, 1000, 8000);
        getOwner().getController().addTask(TaskId.SKILL_USE, task);
    }
}
