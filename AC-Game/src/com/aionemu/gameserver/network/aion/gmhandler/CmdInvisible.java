package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CmdInvisible extends AbstractGMHandler {

    public CmdInvisible(Player admin, String params) {
        super(admin, params);
        run();
    }

    private void run() {
        admin.getEffectController().setAbnormal(AbnormalState.HIDE.getId());
        admin.setVisualState(CreatureVisualState.HIDE20);
        PacketSendUtility.broadcastPacket(admin, new SM_PLAYER_STATE(admin), true);
        PacketSendUtility.sendMessage(admin, "You are invisible.");
    }

}
