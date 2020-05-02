package ai.instance.steelRake;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author xTz
 */
@AIName("suspiciouscannon")
public class SuspiciousCannonAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleUseItemFinish(Player player) {
        player.setState(CreatureState.FLIGHT_TELEPORT);
        player.unsetState(CreatureState.ACTIVE);
        player.setFlightTeleportId(73001);
        PacketSendUtility.sendPacket(player, new SM_EMOTION(player, EmotionType.START_FLYTELEPORT, 73001, 0));
    }
}
