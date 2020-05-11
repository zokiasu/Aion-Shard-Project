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
@AIName("sanctuary_cannon")
public class SanctuaryCannonAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleUseItemFinish(Player player) { //Use the cannon on the 2nd floor to destroy the door in the 3rd floor.
        WorldPosition worldPosition = player.getPosition();
        if (worldPosition.isInstanceMap()) {
            if (worldPosition.getMapId() == 301140000) {
                WorldMapInstance worldMapInstance = worldPosition.getWorldMapInstance();
                killNpc(worldMapInstance.getNpcs(233142));
            }
        }
    }

    private void killNpc(List<Npc> npcs) {
        for (Npc npc : npcs) {
            AI2Actions.killSilently(this, npc);
        }
    }
}
