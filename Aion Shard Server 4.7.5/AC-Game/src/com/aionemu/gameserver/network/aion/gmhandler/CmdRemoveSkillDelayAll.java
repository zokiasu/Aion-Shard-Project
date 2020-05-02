package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.model.gameobjects.player.Player;

public class CmdRemoveSkillDelayAll extends AbstractGMHandler {

    public CmdRemoveSkillDelayAll(Player admin) {
        super(admin, "");
        run();
    }

    //TODO its a little bit odd
    private void run() {
        /*Player t = target != null ? target : admin;
		if (t.getRemoveSkillDelay() == 1) {
			t.setRemoveSkillDelay(0);
			PacketSendUtility.sendMessage(t, "Now you got your normal Skill Cooldowns!");
		} else if (t.getRemoveSkillDelay() == 0) {
			t.setRemoveSkillDelay(1);
			PacketSendUtility.sendMessage(t, "Now you wont have any Skill Cooldowns!");
		}*/
    }

}
