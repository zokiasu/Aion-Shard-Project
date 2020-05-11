package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author Ritsu
 * @Reworked Majka Ajural
 */
@AIName("aggressive_first_skill")
public class AggressiveFirstSkillAI2 extends AggressiveNpcAI2 {
	
	@Override
	protected void handleBackHome() {
		super.handleBackHome();
		setUseInSpawnedSkill();
	}

	@Override
	protected void handleRespawned() {
		super.handleRespawned();
		setUseInSpawnedSkill();
	}

	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		setUseInSpawnedSkill();
	}
	
	private void setUseInSpawnedSkill() {
		if (getSkillList().getUseInSpawnedSkill() != null) {
			int spawnedSkillId = getSkillList().getUseInSpawnedSkill().getSkillId();
			int spawnedSkillLevel = getSkillList().getSkillLevel(spawnedSkillId);
			SkillEngine.getInstance().getSkill(getOwner(), spawnedSkillId, spawnedSkillLevel, getOwner()).useSkill();
		}
	}
}