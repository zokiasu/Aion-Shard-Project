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
import com.aionemu.gameserver.services.QuestService;

/**
 * @author MrPoke
 * @reworked vlog, Rolandas
 */
public class ItemCollecting extends QuestHandler {

    private final Set<Integer> startNpcs = new HashSet<Integer>();
    private final Set<Integer> actionItems = new HashSet<Integer>();
    private final Set<Integer> endNpcs = new HashSet<Integer>();
    private final int questMovie;
    private final int nextNpcId;
    private final int startDialogId;
    private final int startDialogId2;
    private final int itemId;

    public ItemCollecting(int questId, List<Integer> startNpcIds, int nextNpcId, List<Integer> actionItemIds, List<Integer> endNpcIds,
                          int questMovie, int startDialogId, int startDialogId2, int itemId) {
        super(questId);
        startNpcs.addAll(startNpcIds);
        startNpcs.remove(0);
        this.nextNpcId = nextNpcId;
        if (actionItemIds != null) {
            actionItems.addAll(actionItemIds);
            actionItems.remove(0);
        }
        if (endNpcIds == null) {
            endNpcs.addAll(startNpcs);
        } else {
            endNpcs.addAll(endNpcIds);
            endNpcs.remove(0);
        }
        this.questMovie = questMovie;
        this.startDialogId = startDialogId;
        this.startDialogId2 = startDialogId2;
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
        if (nextNpcId != 0) {
            qe.registerQuestNpc(nextNpcId).addOnTalkEvent(getQuestId());
        }
        iterator = actionItems.iterator();
        while (iterator.hasNext()) {
            int actionItem = iterator.next();
            qe.registerQuestNpc(actionItem).addOnTalkEvent(getQuestId());
            qe.registerCanAct(getQuestId(), actionItem);
        }

        iterator = endNpcs.iterator();
        while (iterator.hasNext()) {
            int endNpc = iterator.next();
            qe.registerQuestNpc(endNpc).addOnTalkEvent(getQuestId());
        }
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(getQuestId());
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            if (startNpcs.isEmpty() || startNpcs.contains(targetId)) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        return sendQuestDialog(env, startDialogId != 0 ? startDialogId : 1011);
                    }
                    case SETPRO1: {
                        QuestService.startQuest(env);
                        return closeDialogWindow(env);
                    }
                    case SELECT_ACTION_1012: {
                        if (questMovie != 0) {
                            playQuestMovie(env, questMovie);
                        }
                        return sendQuestDialog(env, 1012);
                    }
                    default: {
                        if (itemId != 0) {
                            giveQuestItem(env, itemId, 1);
                        }
                        return sendQuestStartDialog(env);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (targetId == nextNpcId && var == 0) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        return sendQuestDialog(env, 1352);
                    }
                    case SETPRO1: {
                        return defaultCloseDialog(env, 0, 1);
                    }
                }
            } else if (endNpcs.contains(targetId)) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        return sendQuestDialog(env, startDialogId2 != 0 ? startDialogId2 : 2375);
                    }
                    case CHECK_USER_HAS_QUEST_ITEM: {
                        return checkQuestItems(env, var, var, true, 5, 2716);//1004); // reward
                    }
                    case CHECK_USER_HAS_QUEST_ITEM_SIMPLE: {
                        return checkQuestItemsSimple(env, var, var, true, 5, 0, 0); // reward
                    }
                    case FINISH_DIALOG: {
                        return sendQuestSelectionDialog(env);
                    }
                    case SET_SUCCEED: {
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(env);
                        return closeDialogWindow(env);
                    }
                    case SETPRO1: {
                        return checkQuestItemsSimple(env, var, var, true, 5, 0, 0);
                    }
                    case SETPRO2: {
                        return checkQuestItemsSimple(env, var, var, true, 6, 0, 0);
                    }
                    case SETPRO3: {
                        return checkQuestItemsSimple(env, var, var, true, 7, 0, 0);
                    }
                }
            } else if (targetId != 0 && actionItems.contains(targetId)) {
                return true; // looting
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (endNpcs.contains(targetId)) {
                if (itemId != 0) {
                    removeQuestItem(env, itemId, 1);
                }
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }
}
