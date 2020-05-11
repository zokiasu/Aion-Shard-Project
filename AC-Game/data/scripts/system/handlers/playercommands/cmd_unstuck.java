package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

import java.util.concurrent.TimeUnit;

/**
 * @author Nemiroff Date: 11.01.2010
 * @rework Eloann Date : 12.06.2013
 */
public class cmd_unstuck extends PlayerCommand {

    public cmd_unstuck() {
        super("unstuck");
    }

    @Override
    public void execute(final Player player, String... params) {
        if (player.getLifeStats().isAlreadyDead()) {
            PacketSendUtility.sendMessage(player, "You dont have execute this command. You die");
            return;
        }
        if (player.isInPrison()) {
            PacketSendUtility.sendMessage(player, "You can't use the unstuck command when you are in Prison");
            return;
        }

        PacketSendUtility.sendMessage(player, "You are now freeze for 10 secondes before unstuck.");
        player.getEffectController().setAbnormal(AbnormalState.PARALYZE.getId());
        player.getEffectController().updatePlayerEffectIcons();
        player.getEffectController().broadCastEffects();
        PacketSendUtility.sendPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), 0, 0, (int) TimeUnit.SECONDS.toMillis(10), 0, 0));
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                player.getEffectController().unsetAbnormal(AbnormalState.PARALYZE.getId());
                player.getEffectController().updatePlayerEffectIcons();
                player.getEffectController().broadCastEffects();
                player.getController().cancelUseItem();
                PacketSendUtility.broadcastPacketAndReceive(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), 0, 0, 0, 1, 0));
                TeleportService2.moveToBindLocation(player, true);
            }
        }, (int) TimeUnit.SECONDS.toMillis(10));
    }

    @Override
    public void onFail(Player player, String message) {
        // TODO Auto-generated method stub
    }
}
