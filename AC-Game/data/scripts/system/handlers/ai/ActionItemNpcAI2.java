package ai;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.controllers.observer.ItemUseObserver;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import static com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE.*;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author xTz
 * @modified vlog
 * @Modified Majka Ajural
 */
@AIName("useitem")
public class ActionItemNpcAI2 extends NpcAI2 {

    protected int startBarAnimation = 1;
    protected int cancelBarAnimation = 2;

    @Override
    protected void handleDialogStart(Player player) {
        handleUseItemStart(player);
    }

    protected void handleUseItemStart(final Player player) {
	
		// If the npc is busy for some quest send a message to player
		if(getOwner() instanceof Npc && getOwner().getIsQuestBusy()) {
			PacketSendUtility.sendPacket(player, STR_ITEM_CANT_USE_UNTIL_DELAY_TIME);
			handleUseItemFinish(player);
			return;
		}
			
        final int delay = getTalkDelay();
        if (delay > 1) {
            final ItemUseObserver observer = new ItemUseObserver() {
                @Override
                public void abort() {
                    player.getController().cancelTask(TaskId.ACTION_ITEM_NPC);
                    PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.END_QUESTLOOT, 0, getObjectId()), true);
                    PacketSendUtility.sendPacket(player, new SM_USE_OBJECT(player.getObjectId(), getObjectId(), 0, cancelBarAnimation));
                    player.getObserveController().removeObserver(this);
                }
            };

            player.getObserveController().attach(observer);
            PacketSendUtility.sendPacket(player, new SM_USE_OBJECT(player.getObjectId(), getObjectId(), getTalkDelay(), startBarAnimation));
            PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.START_QUESTLOOT, 0, getObjectId()), true);
            player.getController().addTask(TaskId.ACTION_ITEM_NPC, ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.END_QUESTLOOT, 0, getObjectId()), true);
                    PacketSendUtility.sendPacket(player, new SM_USE_OBJECT(player.getObjectId(), getObjectId(), getTalkDelay(), cancelBarAnimation));
                    player.getObserveController().removeObserver(observer);
                    handleUseItemFinish(player);
                }
            }, delay));
        } else {
            handleUseItemFinish(player);
        }
    }

    protected void handleUseItemFinish(Player player) {
        if (getOwner().isInInstance()) {
            AI2Actions.handleUseItemFinish(this, player);
        }
    }

    protected int getTalkDelay() {
        return getObjectTemplate().getTalkDelay() * 1000;
    }
}
