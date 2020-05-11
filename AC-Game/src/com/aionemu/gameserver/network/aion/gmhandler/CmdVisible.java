package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CmdVisible extends AbstractGMHandler {

    public CmdVisible(Player admin, String params) {
        super(admin, params);
        run();
    }

    private void run() {
        admin.getEffectController().unsetAbnormal(AbnormalState.HIDE.getId());
        admin.unsetVisualState(CreatureVisualState.HIDE20);
        PacketSendUtility.broadcastPacket(admin, new SM_PLAYER_STATE(admin), true);
        PacketSendUtility.sendMessage(admin, "You are invisible.");
    }

}
