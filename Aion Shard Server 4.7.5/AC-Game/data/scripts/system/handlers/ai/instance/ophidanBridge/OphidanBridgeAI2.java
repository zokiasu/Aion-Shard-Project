package ai.instance.ophidanBridge;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author DeathMagnestic
 * @rework Eloann
 */
@AIName("ophidan_bridge")
public class OphidanBridgeAI2 extends ActionItemNpcAI2 {

    @Override
    public void handleUseItemFinish(Player player) {
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        if (instance != null) {
            instance.getDoors().get(47).setOpen(true);
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1401879);
            AI2Actions.deleteOwner(this);
        }
    }
}
