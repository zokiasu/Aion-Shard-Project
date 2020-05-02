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
@AIName("accountant_cabin")
public class AccountantCabinEntranceAI2 extends ActionItemNpcAI2
{
	@Override
	protected void handleUseItemFinish(Player player) {
		switch (getNpcId()) {
		    case 730764: //Accountant's Cabin Entrance.
				switch (player.getWorldId()) {
                    case 301020000: //Steel Rose Quarters Solo
					    PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "You enter <Accountant's Cabin>", ChatType.BRIGHT_YELLOW_CENTER), true);
						TeleportService2.teleportTo(player, 301020000, 702.11993f, 500.80948f, 939.60675f, (byte) 60, TeleportAnimation.BEAM_ANIMATION);
			        break;
				} 
				switch (player.getWorldId()) {
                    case 301040000: //Steel Rose Quarters Group
					    PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "You enter <Accountant's Cabin>", ChatType.BRIGHT_YELLOW_CENTER), true);
						TeleportService2.teleportTo(player, 301040000, 702.11993f, 500.80948f, 939.60675f, (byte) 60, TeleportAnimation.BEAM_ANIMATION);
			        break;
				}
		    break;
		}
	}
}