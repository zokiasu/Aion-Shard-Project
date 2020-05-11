package ai.instance.admaStronghold;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AIName;

/**
 * 
 * @author Ranastic
 *
 */
@AIName("zombies")
public class ZombiesAI2 extends AggressiveNpcAI2 {
	
	@Override
	public boolean canThink() {
		return false;
	}
}