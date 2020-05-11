package ai.instance.lowerUdasTemple;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.*;
import com.aionemu.gameserver.ai2.event.*;
import com.aionemu.gameserver.ai2.manager.*;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.skill.NpcSkillEntry;

@AIName("kingspin")
public class KingSpinAI2 extends AggressiveNpcAI2 {

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 50) {
            AI2Actions.useSkill(this, 18609); //Exsanguinate.
        }
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
    }

    @Override
    public AttackIntention chooseAttackIntention() {
        VisibleObject currentTarget = getTarget();
        Creature mostHated = getAggroList().getMostHated();
        if (mostHated == null || mostHated.getLifeStats().isAlreadyDead()) {
            return AttackIntention.FINISH_ATTACK;
        }
        if (currentTarget == null || !currentTarget.getObjectId().equals(mostHated.getObjectId())) {
            onCreatureEvent(AIEventType.TARGET_CHANGED, mostHated);
            return AttackIntention.SWITCH_TARGET;
        }
        NpcSkillEntry skill = SkillAttackManager.chooseNextSkill(this);
        if (skill != null) {
            skillId = skill.getSkillId();
            skillLevel = skill.getSkillLevel();
            return AttackIntention.SKILL_ATTACK;
        }
        return AttackIntention.SIMPLE_ATTACK;
    }
}
