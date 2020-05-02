package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Magenik, Antraxx
 */
abstract public class AbstractGMHandler {

    protected String params;
    protected Player admin;
    protected Player target;

    public AbstractGMHandler(Player admin, String params) {
        this.admin = admin;
        this.params = params;
        getTarget();
    }

    public void getTarget() {
        VisibleObject t = admin.getTarget();
        if (t instanceof Player) {
            target = (Player) target;
            return;
        }
        target = null;
    }

    public boolean checkTarget() {
        if (target != null) {
            return true;
        }
        PacketSendUtility.sendMessage(admin, "Target not found or target is not an player");
        return false;
    }

}
