package com.aionemu.gameserver.questEngine.handlers.template;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author MrPoke Like: Sleeping on the Job quest.
 * @modified Rolandas
 */
public class ReportTo extends QuestHandler {

    private final Set<Integer> startNpcs = new HashSet<Integer>();
    private final Set<Integer> endNpcs = new HashSet<Integer>();
    private final int itemId;

    /**
     * @param id
     * @param startNpcIds
     * @param endNpcIds
     * @param itemId2
     */
    public ReportTo(int questId, List<Integer> startNpcIds, List<Integer> endNpcIds, int itemId) {
        super(questId);
        startNpcs.addAll(startNpcIds);
        startNpcs.remove(0);
        if (endNpcIds != null) {
            endNpcs.addAll(endNpcIds);
            endNpcs.remove(0);
        }
        this.itemId = itemId;
    }

    @Override
    public void register() {
        Iterator<Integer> iterator = startNpcs.iterator();
        while (iterator.hasNext()) {
            int startNpc = iterator.next();
            qe.registerQuestNpc(startNpc).addOnQuestStart(getQuestId());
            qe.registerQuestNpc(startNpc).addOnTalkEvent(getQuestId());
        }
        iterator = endNpcs.iterator();
        while (iterator.hasNext()) {
            int endNpc = iterator.next();
            qe.registerQuestNpc(endNpc).addOnTalkEvent(getQuestId());
        }
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();
        QuestState qs = player.getQuestStateList().getQuestState(getQuestId());

        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            if (startNpcs.isEmpty() || startNpcs.contains(targetId)) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        return sendQuestDialog(env, 1011);
                    }
                    case QUEST_ACCEPT_1:
                    case QUEST_ACCEPT_SIMPLE: {
                        if (itemId != 0) {
                            if (giveQuestItem(env, itemId, 1)) {
                                return sendQuestStartDialog(env);
                            }
                            return false;
                        } else {
                            return sendQuestStartDialog(env);
                        }
                    }
                    default: {
                        return sendQuestStartDialog(env);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (startNpcs.contains(targetId)) {
                if (dialog == DialogAction.FINISH_DIALOG) {
                    return sendQuestSelectionDialog(env);
                }
            } else if (endNpcs.contains(targetId)) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        return sendQuestDialog(env, 2375);
                    }
                    case SELECT_QUEST_REWARD: {
                        if (itemId != 0) {
                            if (player.getInventory().getItemCountByItemId(itemId) < 1) {
                                return sendQuestSelectionDialog(env);
                            }
                        }
                        removeQuestItem(env, itemId, 1);
                        qs.setQuestVar(1);
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(env);
                        return sendQuestEndDialog(env);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (endNpcs.contains(targetId)) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }
}
