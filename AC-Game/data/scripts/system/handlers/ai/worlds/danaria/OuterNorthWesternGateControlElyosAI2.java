package ai.worlds.danaria;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * 
 * @author Ranastic
 *
 */

@AIName("outernorthwesterngatecontrolelyos")
public class OuterNorthWesternGateControlElyosAI2 extends ActionItemNpcAI2
{
	@Override
	protected void handleDialogStart(Player player) {
		super.handleDialogStart(player);
	}
	
	@Override
	protected void handleUseItemFinish(Player player) {
		switch(getNpcId()) {
			case 701785: //Outer Northwestern Gate Control.
				despawnNpc(273285); //Outer NorthWestern Gate.
				PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401691));
			break;
		}
		//AI2Actions.deleteOwner(this);
	}
	
	private void despawnNpc(final int npcId) {
		getKnownList().doOnAllNpcs(new Visitor<Npc>() {
			@Override
			public void visit(Npc npc) {
				if (npc.getNpcId() == npcId) {
					npc.getController().onDelete();
				}
			}
		});
	}
}