package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CmdStartQuest extends AbstractGMHandler {

    public CmdStartQuest(Player admin, String params) {
        super(admin, params);
        run();
    }

    private void run() {
        Player t = target != null ? target : admin;

        Integer questID = Integer.parseInt(params);
        if (questID <= 0) {
            return;
        }

        @SuppressWarnings("static-access")
        QuestTemplate qt = DataManager.getInstance().QUEST_DATA.getQuestById(questID);
        if (qt == null) {
            PacketSendUtility.sendMessage(admin, "Quest with ID: " + questID + "was not founded");
            return;
        }

        QuestEnv env = new QuestEnv(null, t, questID, 0);
        QuestService.startQuest(env, 0);
    }

}
