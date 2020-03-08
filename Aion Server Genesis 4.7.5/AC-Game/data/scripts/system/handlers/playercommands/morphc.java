package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRANSFORM;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

public class Morphc extends PlayerCommand {

    public Morphc() {
        super("morphc");
    }

    @Override
    public void execute(Player player) {

        Player target = player;
        int param = 0;

        target.getTransformModel().setModelId(param);
        PacketSendUtility.broadcastPacketAndReceive(target, new SM_TRANSFORM(target, true));

        PacketSendUtility.sendMessage(target, "Morph successfully cancelled.");
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //morphc");
    }
}
