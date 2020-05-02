package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.QuestStateList;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.model.templates.quest.QuestCategory;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_COMPLETED_LIST;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;


public class CmdDeleteQuest extends AbstractGMHandler {

    public CmdDeleteQuest(Player admin, String params) {
        super(admin, params);
        run();
    }

    private void run() {
        Player t = admin;

        if (admin.getTarget() != null && admin.getTarget() instanceof Player)
            t = World.getInstance().findPlayer(Util.convertName(admin.getTarget().getName()));

        Integer questID = Integer.parseInt(params);
        if (questID <= 0) {
            return;
        }

        @SuppressWarnings("static-access")
        QuestTemplate qt = DataManager.getInstance().QUEST_DATA.getQuestById(questID);
        if (qt == null) {
            PacketSendUtility.sendMessage(admin, "Quest with ID: " + questID + " was not found");
            return;
        }

        QuestStateList list = t.getQuestStateList();
        if (list == null || list.getQuestState(questID) == null) {
            PacketSendUtility.sendMessage(admin, "Quest not deleted for target " + t.getName());
            return;
        }

        QuestState qs = list.getQuestState(questID);
        qs.setQuestVar(0);
        qs.setCompleteCount(0);
        if (qt.getCategory() == QuestCategory.MISSION) {
            qs.setStatus(QuestStatus.START);
        } else {
            qs.setStatus(null);
        }
        if (qs.getPersistentState() != PersistentState.NEW) {
            qs.setPersistentState(PersistentState.DELETED);
        }
        PacketSendUtility.sendPacket(t, new SM_QUEST_COMPLETED_LIST(t.getQuestStateList().getAllFinishedQuests()));
        t.getController().updateNearbyQuests();
    }

}
