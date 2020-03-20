package quest.levinshor;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author pralinka
 */
public class _23730ApeitsRequest extends QuestHandler {

	private final static int questId = 23730;

	public _23730ApeitsRequest() {
		super(questId);
	}

	@Override
	public void register() {
		//qe.registerQuestNpc(802339).addOnTalkEvent(questId);
		//qe.registerOnEnterZone(ZoneName.get("NORTH_OUTPOST_600100000"), questId);
		//qe.registerOnKillInWorld(600100000, questId);
	}
	
	@Override
	public boolean onKillInWorldEvent(QuestEnv env) {
		Player player = env.getPlayer();
		if (env.getVisibleObject() instanceof Player && player != null && player.isInsideZone(ZoneName.get("NORTH_OUTPOST_600100000"))) {
			if ((env.getPlayer().getLevel() >= (((Player)env.getVisibleObject()).getLevel() - 5)) && (env.getPlayer().getLevel() <= (((Player)env.getVisibleObject()).getLevel() + 9))) {
				return defaultOnKillRankedEvent(env, 0, 1, true); // reward
			}
		}
		return false;
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();

		if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 802339) {
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
		if (zoneName == ZoneName.get("NORTH_OUTPOST_600100000")) {
			Player player = env.getPlayer();
			if (player == null)
				return false;
			QuestState qs = player.getQuestStateList().getQuestState(questId);
			if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
				QuestService.startQuest(env);
				PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
				return true;

			}
		}
		return false;
	}
}
