package ai.portals;

import java.util.List;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.autogroup.AutoGroupType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.portal.PortalPath;
import com.aionemu.gameserver.network.aion.serverpackets.SM_AUTO_GROUP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FIND_GROUP;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.PortalService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author xTz
 * @reworked vlog
 * @reworked Blackfire
 */
@AIName("portal_dialog")
public class PortalDialogAI2 extends PortalAI2 {

    protected int rewardDialogId = 5;
    protected int startingDialogId = 10;
    protected int questDialogId = 10;

    @Override
    protected void handleDialogStart(Player player) {
		if (getTalkDelay() == 0) {
			checkDialog(player);
        } else {
            super.handleDialogStart(player);
        }
    }

    @Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
        QuestEnv env = new QuestEnv(getOwner(), player, questId, dialogId);
        env.setExtendedRewardIndex(extendedRewardIndex);
        if (questId > 0 && QuestEngine.getInstance().onDialog(env)) {
            return true;
        }
        if (dialogId == DialogAction.INSTANCE_PARTY_MATCH.id()) {
            AutoGroupType agt = AutoGroupType.getAutoGroup(player.getLevel(), getNpcId());
            if (agt != null) {
                PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(agt.getInstanceMaskId()));
            }
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 0));
        }/* else if (dialogId == DialogAction.SELECT_ACTION_1012.id()) {
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1182));
        }*/ else if (dialogId == DialogAction.OPEN_INSTANCE_RECRUIT.id()) {
            AutoGroupType agt = AutoGroupType.getAutoGroup(player.getLevel(), getNpcId());
            if (agt != null) {
                PacketSendUtility.sendPacket(player, new SM_FIND_GROUP(0x1A, agt.getInstanceMapId()));
            }
        } else {
            if (questId == 0) {
                PortalPath portalPath = DataManager.PORTAL2_DATA.getPortalDialog(getNpcId(), dialogId, player.getRace());
                if (portalPath != null) {
                    PortalService.port(portalPath, player, getObjectId());
                }
            } else {
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), dialogId, questId));
            }
        }
        return true;
    }

    @Override
    protected void handleUseItemFinish(Player player) {
            checkDialog(player);
	}

    private void checkDialog(Player player) {
        int npcId = getNpcId();
        int teleportationDialogId = DataManager.PORTAL2_DATA.getTeleportDialogId(npcId);
        List<Integer> relatedQuests = QuestEngine.getInstance().getQuestNpc(npcId).getOnTalkEvent();
        boolean playerHasQuest = false;
        boolean playerCanStartQuest = false;
		if (!relatedQuests.isEmpty()) {
            for (int questId : relatedQuests) {
                QuestState qs = player.getQuestStateList().getQuestState(questId);
                if (qs != null && (qs.getStatus() == QuestStatus.START || qs.getStatus() == QuestStatus.REWARD)) {
                    playerHasQuest = true;
                    break;
                } else if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
                    if (QuestService.checkStartConditions(new QuestEnv(getOwner(), player, questId, 0), false)) {
                        playerCanStartQuest = true;
                        continue;
                    }
                }
            }
        }
		if (playerHasQuest) {
            boolean isRewardStep = false;
            for (int questId : relatedQuests) {
                QuestState qs = player.getQuestStateList().getQuestState(questId);
                if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), rewardDialogId, questId));
                    isRewardStep = true;
                    break;
                }
            }
            if (!isRewardStep) {
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), questDialogId));
            }
        } else if (playerCanStartQuest) {
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), startingDialogId));
        } else {
        	switch (npcId) {
				//Wisplight Abbey 4.7.5
				case 804677: //Atreia Defense Portal
				case 804678: //Balaurea Advance Portal
				case 804679: //Balaurea Invasion Portal
				case 804680: //Balaurea Frontier Portal
				//Fatebound Abbey 4.7.5
				case 804682: //Atreia Defense Portal
				case 804683: //Balaurea Advance Portal
				case 804684: //Balaurea Invasion Portal
				case 804685: //Balaurea Frontier Portal
				case 831117: //Shugo Imperial Tomb
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 10, 0));
				break;
				case 730840:
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 10, 0));
					break;
				case 730841:
				case 730883:
				case 804621:
				case 804623:
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 10, 0));
					break;
				case 804624:
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 10, 0));
					break;

				case 804625:
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 4762, 0)); // instances with infernal dialog.
					break;
				default:
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), teleportationDialogId, 0));
					break;
        	}
        }
    }
}
