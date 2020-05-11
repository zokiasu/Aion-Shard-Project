package ai;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;

/**
 * @author xTz
 */
@AIName("invisible_npc")
public class InvisibleNpcAI2 extends AggressiveNpcAI2
{
    @Override
    public void handleSpawned() {
        super.handleSpawned();
        if (!isAlreadyDead()) {
        	AI2Actions.useSkill(InvisibleNpcAI2.this, 19493); 
        	getOwner().setVisualState(CreatureVisualState.HIDE1);
        }
    }
	
    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        getEffectController().removeEffect(19493); 
        getOwner().unsetVisualState(CreatureVisualState.HIDE1);
        getOwner().getEffectController().removeAllEffects();
    }
    
    @Override
	protected void handleBackHome() {
    	super.handleBackHome();
    	if (!isAlreadyDead()) {
        	AI2Actions.useSkill(InvisibleNpcAI2.this, 19493);
        	getOwner().setVisualState(CreatureVisualState.HIDE1);
        }
	}
}
