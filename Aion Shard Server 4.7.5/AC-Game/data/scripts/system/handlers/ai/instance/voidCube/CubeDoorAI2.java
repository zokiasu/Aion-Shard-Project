package ai.instance.voidCube;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;

import ai.GeneralNpcAI2;

/**
 * @author zxl001
 */
@AIName("cubedoor")
public class CubeDoorAI2 extends GeneralNpcAI2 {
	
	@Override
	protected void handleBackHome() {
	}
	
	@Override
	protected void handleDied() {
		super.handleDied();
		getOwner().getController().delete();
	}
	
	@Override
	protected void handleCreatureAggro(Creature creature) {
		super.handleCreatureAggro(creature);
		if (creature.getCastingSkill().getSkillId() == 10600) {
			while(!isAlreadyDead())
				getOwner().getController().onAttack(creature, 10600, getLifeStats().getCurrentHp() + 1, true);
		}
	}
}
