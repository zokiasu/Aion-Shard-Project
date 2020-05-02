package ai.instance.sauroSupplyBase;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;


@AIName("false_chest")
public class FalseChestAI2 extends NpcAI2 {

	@Override
	protected void handleDied() {
		spawn(230843, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
		super.handleDied();
		AI2Actions.deleteOwner(this);
	}
	
}