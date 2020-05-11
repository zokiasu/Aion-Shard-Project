package ai.instance.admaStronghold;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * 
 * @author Ranastic
 *
 */
@AIName("ntuamusteddybear")
public class NtuamusTeddyBearAI2 extends ActionItemNpcAI2 {
	
	@Override
	protected void handleUseItemFinish(Player player) {
		player.getEffectController().removeEffect(18462); //Deep Wound.
	}
}