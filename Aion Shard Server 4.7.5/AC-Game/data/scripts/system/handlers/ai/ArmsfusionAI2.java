package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;

@AIName("armsfusion")
public class ArmsfusionAI2 extends NpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        PacketSendUtility.sendBrightYellowMessageOnCenter(player, "The price of the fusion has been set at 0. Do not rely on the price displayed.");
    }
}
