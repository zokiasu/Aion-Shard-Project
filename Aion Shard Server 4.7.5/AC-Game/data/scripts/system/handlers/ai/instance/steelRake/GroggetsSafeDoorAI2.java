package ai.instance.steelRake;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * Author Ranastic
 **/
@AIName("groggetssafedoor")
public class GroggetsSafeDoorAI2 extends ActionItemNpcAI2 {

	@Override
	protected void handleUseItemFinish(Player player) {
		switch (getNpcId()) {
			case 730199: // Groggets Safe Door.
				switch (player.getWorldId()) {
					case 300100000: // Steel Rake.
						PacketSendUtility.sendMessage(player, "you enter <Inside Steel Rake>");
						TeleportService2.teleportTo(player, 300100000, 702.11993f, 500.80948f, 939.60675f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
						break;
				}
				switch (player.getWorldId()) {
					case 300460000: // Steel Rake Cabin 3.0
						PacketSendUtility.sendMessage(player, "you enter <Inside Steel Rake Cabin>");
						TeleportService2.teleportTo(player, 300460000, 702.11993f, 500.80948f, 939.60675f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
						break;
				}
				break;
		}
	}
}
