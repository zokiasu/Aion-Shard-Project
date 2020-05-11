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
@AIName("siegedrill")
public class SiegeDrillAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleUseItemFinish(Player player) {
        WorldPosition worldPosition = player.getPosition();
        if (worldPosition.isInstanceMap()) {
            if (worldPosition.getMapId() == 301140000) {
                WorldMapInstance worldMapInstance = worldPosition.getWorldMapInstance();
                killNpc(worldMapInstance.getNpcs(233189));
            }
        }
    }

    private void killNpc(List<Npc> npcs) {
        for (Npc npc : npcs) {
            AI2Actions.killSilently(this, npc);
        }
    }
}
