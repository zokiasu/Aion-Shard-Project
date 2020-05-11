package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author Ritsu
 */
@AIName("general_first_skill")
public class GeneralFirstSkillAI2 extends GeneralNpcAI2 {

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        if (getSkillList().getUseInSpawnedSkill() != null) {
            int skillId = getSkillList().getUseInSpawnedSkill().getSkillId();
            int skillLevel = getSkillList().getSkillLevel(skillId);
            SkillEngine.getInstance().getSkill(getOwner(),
                    skillId, skillLevel, getOwner()).useSkill();
        }
    }

    @Override
    protected void handleRespawned() {
        super.handleRespawned();
        if (getSkillList().getUseInSpawnedSkill() != null) {
            int skillId = getSkillList().getUseInSpawnedSkill().getSkillId();
            int skillLevel = getSkillList().getSkillLevel(skillId);
            SkillEngine.getInstance().getSkill(getOwner(),
                    skillId, skillLevel, getOwner()).useSkill();
        }
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        if (getSkillList().getUseInSpawnedSkill() != null) {
            int skillId = getSkillList().getUseInSpawnedSkill().getSkillId();
            int skillLevel = getSkillList().getSkillLevel(skillId);
            SkillEngine.getInstance().getSkill(getOwner(),
                    skillId, skillLevel, getOwner()).useSkill();
        }
    }
}
