package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

public class CmdGiveTitle extends AbstractGMHandler {

    public CmdGiveTitle(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {
        Player t = admin;

        if (admin.getTarget() != null && admin.getTarget() instanceof Player)
            t = World.getInstance().findPlayer(Util.convertName(admin.getTarget().getName()));
        Integer titleId = Integer.parseInt(params);

        if ((titleId > 272) || (titleId < 1)) {
            PacketSendUtility.sendMessage(admin, "title id " + titleId + " is invalid (must be between 1 and 272)");
        } else {
            if (t != null) {
                if (!t.getTitleList().addTitle(titleId, false, 0)) {
                    PacketSendUtility.sendMessage(admin, "you can't add title #" + titleId + " to "
                            + (t.equals(admin) ? "yourself" : t.getName()));
                } else {
                    PacketSendUtility.sendMessage(admin, "you added to " + t.getName() + " title #" + titleId);
                    PacketSendUtility.sendMessage(t, admin.getName() + " gave you title #" + titleId);
                }
            }
        }
    }
}
