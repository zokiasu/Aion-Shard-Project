package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;


public class CmdAddSkill extends AbstractGMHandler {

    public CmdAddSkill(Player admin, String params) {
        super(admin, params);
        run();
    }

    private void run() {
        Player t = admin;

        if (admin.getTarget() != null && admin.getTarget() instanceof Player)
            t = World.getInstance().findPlayer(Util.convertName(admin.getTarget().getName()));

        if (params == null)
            return;

        for (SkillTemplate template : DataManager.SKILL_DATA.getSkillData().valueCollection()) {
            if (template.getNamedesc() != null && template.getNamedesc().equalsIgnoreCase(params)) {
                PacketSendUtility.sendMessage(admin, "You added Skill " + template.getName() + "to " + t.getName());
                PacketSendUtility.sendMessage(t, "Admin has add Skill " + template.getName() + "to you.");
                t.getSkillList().addSkill(t, template.getSkillId(), 1);
            }
        }
    }
}
