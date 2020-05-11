package ai.instance.idgelResearchCenter;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Rinzler
 */
@AIName("retributiveeffigy")
public class RetributiveEffigyAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isAggred = new AtomicBoolean(false);

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isAggred.compareAndSet(false, true)) {
            SkillEngine.getInstance().getSkill(getOwner(), 19406, 60, getOwner()).useNoAnimationSkill();
        }
    }
}
