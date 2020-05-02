package com.aionemu.gameserver.eventEngine.battleground.services.utils;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Eloann
 */
public class UIUtils {

    public static void sendPopupRequest(Player player, String message, RequestResponseHandler handler) {
        boolean request = player.getResponseRequester().putRequest(901756, handler);
        if (request) {
            PacketSendUtility.sendPacket(player, new SM_QUESTION_WINDOW(901756, player.getObjectId(), 1, message));
        }
    }
}
