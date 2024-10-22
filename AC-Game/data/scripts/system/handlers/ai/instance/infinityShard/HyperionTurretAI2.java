package ai.instance.infinityShard;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author DeathMagnestic
 */
@AIName("hy_turret")
public class HyperionTurretAI2 extends AggressiveNpcAI2 {

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    public void ownerSkillUse(int skillId) {
        if (skillId == 21201) {
            AI2Actions.deleteOwner(this);
        }
    }

    private synchronized void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 5) {
            SkillEngine.getInstance().getSkill(getOwner(), 21201, 65, getTarget()).useNoAnimationSkill();
        }
    }
}
