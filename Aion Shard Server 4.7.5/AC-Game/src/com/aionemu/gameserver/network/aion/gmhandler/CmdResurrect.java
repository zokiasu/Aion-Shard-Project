package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RESURRECT;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CmdResurrect extends AbstractGMHandler {

    public CmdResurrect(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {
        Player t = target != null ? target : admin;
        if (!t.getLifeStats().isAlreadyDead()) {
            return;
        }
        t.setPlayerResActivate(true);
        PacketSendUtility.sendPacket(t, new SM_RESURRECT(admin));
    }

}
