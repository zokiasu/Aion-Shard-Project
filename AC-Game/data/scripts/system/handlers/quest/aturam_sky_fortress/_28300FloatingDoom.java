/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 * 
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, regarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package quest.aturam_sky_fortress;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author zhkchi
 *
 */
public class _28300FloatingDoom extends QuestHandler {

    private final static int questId = 28300;

    public _28300FloatingDoom() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(799266).addOnQuestStart(questId);
        qe.registerQuestNpc(799266).addOnTalkEvent(questId);
        qe.registerQuestNpc(799531).addOnTalkEvent(questId);
        qe.registerQuestNpc(799530).addOnTalkEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("ATURAM_SKY_FORTRESS_1_300240000"), questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 799266) {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 4762);
                    default:
                        return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
            if (targetId == 799531) {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1011);
                    case SELECT_ACTION_1013:
                        return sendQuestDialog(env, 1013);
                    case SETPRO1:
                        changeQuestStep(env, 0, 1, false);
                        return closeDialogWindow(env);
                }
            }
        } else if ((qs.getStatus() == QuestStatus.REWARD)) {
            if (targetId == 799530) {
                switch (env.getDialog()) {
                    case USE_OBJECT:
                        return sendQuestDialog(env, 10002);
                    case SELECT_QUEST_REWARD:
                        return sendQuestDialog(env, 5);
                    default:
                        return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        if (zoneName == ZoneName.get("ATURAM_SKY_FORTRESS_1_300240000")) {
            Player player = env.getPlayer();
            if (player == null) {
                return false;
            }
            QuestState qs = player.getQuestStateList().getQuestState(questId);
            if (qs != null && qs.getStatus() == QuestStatus.START) {
                int var = qs.getQuestVarById(0);
                if (var == 1) {
                    playQuestMovie(env, 467);
                    changeQuestStep(env, 1, 1, true);
                    return true;
                }
            }
        }
        return false;
    }
}