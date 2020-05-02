package com.aionemu.gameserver.eventEngine.battleground.controllers;

import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.eventEngine.battleground.model.gameobjects.BattleGroundAgent;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.eventEngine.battleground.services.battleground.BattleGroundManager;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Eloann
 */
public class BattleGroundAgentController extends NpcController {

    @Override
    public void onDialogRequest(final Player player) {
        if (player.getCommonData().getRace() != getOwner().getObjectTemplate().getRace()) {
            return;
        }
        if (player.battlegroundWaiting) {
            PacketSendUtility.sendMessage(player, "You are already registered in a battleground.");
        } else {
            String message = "Do you want to register in a battleground ?";
            RequestResponseHandler responseHandler = new RequestResponseHandler(player) {
                @Override
                public void acceptRequest(Creature requester, Player responder) {
                    BattleGroundManager.sendRegistrationForm(player);
                }

                @Override
                public void denyRequest(Creature requester, Player responder) {
                }
            };
            boolean requested = player.getResponseRequester().putRequest(902247, responseHandler);
            if (requested) {
                PacketSendUtility.sendPacket(player, new SM_QUESTION_WINDOW(902247, 1, 1, message));
            }
        }
    }

    @Override
    public BattleGroundAgent getOwner() {
        return (BattleGroundAgent) super.getOwner();
    }
}
