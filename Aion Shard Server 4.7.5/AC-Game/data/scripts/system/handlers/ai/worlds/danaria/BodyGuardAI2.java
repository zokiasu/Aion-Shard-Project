package ai.worlds.danaria;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.handler.AggroEventHandler;
import com.aionemu.gameserver.ai2.handler.CreatureEventHandler;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author DeathMagnestic
 */
@AIName("bodyguard")
public class BodyGuardAI2 extends AggressiveNpcAI2 {

    private boolean canThink = false;

    @Override
    public boolean canThink() {
        return canThink;
    }

    @Override
    protected void handleCreatureSee(Creature creature) {
        CreatureEventHandler.onCreatureSee(this, creature);
        AggroEventHandler.onAggro(this, creature);
        VisibleObject target = creature.getTarget();
        if (creature.getSkillNumber() >= 1 || creature.getCastingSkillId() >= 1 || creature.isCasting()) {
            if (target instanceof Player && !creature.getRace().equals(((Player) target).getRace())) {
                canThink = true;
                AggroEventHandler.onAggro(this, creature);
                StartChain(creature);
            }
            if (target instanceof Creature) {
                canThink = false;
                AggroEventHandler.onAggro(this, creature);
            }
        }
    }

    public void StartChain(final Creature player) {
        SkillEngine.getInstance().getSkill(player, 20672, 65, player).useNoAnimationSkill();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                SkillEngine.getInstance().getSkill(player, 20542, 65, player).useNoAnimationSkill();
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        SkillEngine.getInstance().getSkill(getOwner(), 20548, 65, player).useNoAnimationSkill();
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                SkillEngine.getInstance().getSkill(getOwner(), 21263, 65, player).useNoAnimationSkill();
                            }
                        }, 2000);
                    }
                }, 500);
            }
        }, 500);
        canThink = false;
    }
}