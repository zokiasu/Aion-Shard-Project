package ai.instance.theHexway;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * Author Ranastic
 **/
@AIName("shiningmagicward")
public class ShiningMagicWardAI2 extends ActionItemNpcAI2 {

	@Override
	protected void handleUseItemFinish(Player player) {
		switch (getNpcId()) {
			case 700455: // Shining Magic Ward.
				switch (player.getWorldId()) {
					case 300080000: // Left Wing Chamber.
						TeleportService2.teleportTo(player, 400010000, 2784.99f, 2664.03f, 1486.7f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
						break;
				}
				switch (player.getWorldId()) {
					case 300700000: // The Hexway 4.3.
						TeleportService2.teleportTo(player, 600010000, 528.2289f, 767.2058f, 867.7978f, (byte) 60, TeleportAnimation.BEAM_ANIMATION);
						break;
				}
				break;
		}
	}
}
