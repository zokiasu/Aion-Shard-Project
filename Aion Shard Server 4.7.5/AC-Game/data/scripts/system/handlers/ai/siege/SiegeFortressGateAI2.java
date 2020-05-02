package ai.siege;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AI2Request;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.PositionUtil;
import com.aionemu.gameserver.skillengine.SkillEngine;

import ai.ActionItemNpcAI2;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * @author Source
 */
@AIName("fortressgate")
public class SiegeFortressGateAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleUseItemFinish(Player player) {
        TeleportService2.moveToTargetWithDistance(getOwner(), player, PositionUtil.isBehind(getOwner(), player) ? 0 : 1, 6);
    }
}
