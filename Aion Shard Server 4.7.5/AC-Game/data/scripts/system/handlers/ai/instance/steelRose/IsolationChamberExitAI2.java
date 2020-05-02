package ai.instance.steelRose;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @rework pralinka
 **/
@AIName("isolation_chamber_exit")
public class IsolationChamberExitAI2 extends ActionItemNpcAI2
{
	@Override
	protected void handleUseItemFinish(Player player) {
		switch (getNpcId()) {
		    case 730768: //Isolation Chamber Exit.
				switch (player.getWorldId()) {
                    case 301010000: //Steel Rose Cargo Solo
						PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "You enter <Prison Deck>", ChatType.BRIGHT_YELLOW_CENTER), true);
						TeleportService2.teleportTo(player, 301010000, 461.5f, 485.0f, 877.8f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
			        break;
				} 
				switch (player.getWorldId()) {
                    case 301030000: //Steel Rose Cargo Group
						PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "You enter <Prison Deck>", ChatType.BRIGHT_YELLOW_CENTER), true);
						TeleportService2.teleportTo(player, 301030000, 461.5f, 485.0f, 877.8f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
			        break;
				}
		    break;
		}
	}
}