package ai.instance.danuarSanctuary;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.List;

/**
 * @author Eloann
 */
@AIName("sanctuary_cannon_2")
public class SanctuaryCannon2AI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleUseItemFinish(Player player) { 
  //Use the cannon in the mittle of the room destroy the door to the opposite side.
        WorldPosition worldPosition = player.getPosition();
        if (worldPosition.isInstanceMap()) {
            if (worldPosition.getMapId() == 301140000) {
                WorldMapInstance worldMapInstance = worldPosition.getWorldMapInstance();
                killNpc(worldMapInstance.getNpcs(730865));
            }
        }
    }

    private void killNpc(List<Npc> npcs) {
        for (Npc npc : npcs) {
            AI2Actions.killSilently(this, npc);
        }
    }
}
