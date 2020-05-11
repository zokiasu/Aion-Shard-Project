package ai.instance.danuarSanctuary;

import java.util.List;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * 
 * @author Ranastic
 *
 */

@AIName("steelrosecannon")
public class SteelRoseCannonAI2 extends ActionItemNpcAI2
{
	@Override
	protected void handleUseItemFinish(Player player) { //Use the cannon on the 2nd floor to destroy the door in the 3rd floor.
		WorldPosition worldPosition = player.getPosition();
		if (worldPosition.isInstanceMap()) {
			if (worldPosition.getMapId() == 301140000) { //Danuar Sanctuary 4.3
				WorldMapInstance worldMapInstance = worldPosition.getWorldMapInstance();
				killNpc(worldMapInstance.getNpcs(233142));
				TeleportService2.teleportTo(player, 301140000, player.getInstanceId(), 933.042f, 870.44336f, 305.45728f, (byte) 18, TeleportAnimation.BEAM_ANIMATION);
			}
		}
	}
	
	private void killNpc(List<Npc> npcs) {
		for (Npc npc: npcs) {
			AI2Actions.killSilently(this, npc);
		}
	}
}