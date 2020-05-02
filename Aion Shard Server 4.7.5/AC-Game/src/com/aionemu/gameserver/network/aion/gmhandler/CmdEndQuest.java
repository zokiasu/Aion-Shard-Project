package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.QuestStateList;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_COMPLETED_LIST;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;


public class CmdEndQuest extends AbstractGMHandler {

    public CmdEndQuest(Player admin, String params) {
        super(admin, params);
        run();
    }

    private void run() {
        Player t = target != null ? target : admin;

        Integer questID = Integer.parseInt(params);
        if (questID <= 0) {
            return;
        }

        DataManager.getInstance();
        QuestTemplate qt = DataManager.QUEST_DATA.getQuestById(questID);
        if (qt == null) {
            PacketSendUtility.sendMessage(admin, "Quest with ID: " + questID + " was not found");
            return;
        }

        QuestStateList list = t.getQuestStateList();
        if (list == null || list.getQuestState(questID) == null) {
            PacketSendUtility.sendMessage(admin, "Quest not founded for target " + t.getName());
            return;
        }
        if (list.getQuestState(questID).getStatus() == QuestStatus.COMPLETE) {
            PacketSendUtility.sendMessage(admin, "Quest allready finished");
            return;
        }
        list.getQuestState(questID).setStatus(QuestStatus.REWARD);
        t.getController().updateNearbyQuests();
        QuestEnv env = new QuestEnv(null, t, questID, 0);
        QuestService.finishQuest(env);
        PacketSendUtility.sendPacket(t, new SM_QUEST_COMPLETED_LIST(t.getQuestStateList().getAllFinishedQuests()));
        t.getController().updateNearbyQuests();
    }

}