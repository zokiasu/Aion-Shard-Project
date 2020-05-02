package ai.instance.nightmareCircus;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.container.CreatureLifeStats;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.ThreadPoolManager;

@AIName("ariatte")
public class AriatteAI2 extends NpcAI2 {

    @Override
    public void handleSpawned() {
        startSchedule();
    }

    private void startSchedule() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                checkForHeal();
            }
        }, 10000);
    }

    private void checkForHeal() {
        if (!isAlreadyDead() && getPosition().isSpawned()) {
            for (VisibleObject object : getKnownList().getKnownObjects().values()) {
                Creature creature = (Creature) object;
                CreatureLifeStats<?> lifeStats = creature.getLifeStats();
                if (isInRange(creature, 10) && !creature.getEffectController().hasAbnormalEffect(21363)
                        && !lifeStats.isAlreadyDead() && (lifeStats.getCurrentHp() < lifeStats.getMaxHp())) {
                    if (creature instanceof Player) {
                        doHeal(creature);
                        break;
                    }
                }
            }
            startSchedule();
        }
    }

    private void doHeal(Creature creature) {
        AI2Actions.targetSelf(this);
        AI2Actions.useSkill(this, 21363);
        apllyEffect(creature, 21363);
    }

    public void playerSkillUse(Player player, int skillId) {
        apllyEffect(player, skillId);
    }

    private void apllyEffect(Creature creature, int skillId) {
        SkillEngine.getInstance().applyEffectDirectly(skillId, getOwner(), creature, 0);
    }
}
