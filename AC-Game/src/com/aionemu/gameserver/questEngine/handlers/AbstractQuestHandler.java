package com.aionemu.gameserver.questEngine.handlers;

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.model.templates.rewards.BonusType;
import com.aionemu.gameserver.questEngine.model.QuestActionType;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * The methods will be overridden in concrete quest handlers
 *
 * @author vlog
 */
public abstract class AbstractQuestHandler {

    public abstract void register();

    /**
     * @param questEnv
     */
    public boolean onDialogEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param questEnv
     */
    public boolean onEnterWorldEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param questEnv
     * @param zoneName
     */
    public boolean onEnterZoneEvent(QuestEnv questEnv, ZoneName zoneName) {
        return false;
    }

    /**
     * @param questEnv
     * @param zoneName
     */
    public boolean onLeaveZoneEvent(QuestEnv questEnv, ZoneName zoneName) {
        return false;
    }

    /**
     * @param questEnv
     * @param item
     */
    public HandlerResult onItemUseEvent(QuestEnv questEnv, Item item) {
        return HandlerResult.UNKNOWN;
    }

    /**
     * @param env
     */
    public boolean onHouseItemUseEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param questEnv
     */
    public boolean onGetItemEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param questEnv
     * @param skillId
     */
    public boolean onUseSkillEvent(QuestEnv questEnv, int skillId) {
        return false;
    }

    /**
     * @param questEnv
     */
    public boolean onKillEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param questEnv
     */
    public boolean onAttackEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param questEnv
     */
    public boolean onLvlUpEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param questEnv
     */
    public boolean onDieEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onLogOutEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onNpcReachTargetEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onNpcLostTargetEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param questEnv
     * @param movieId
     */
    public boolean onMovieEndEvent(QuestEnv questEnv, int movieId) {
        return false;
    }

    /**
     * @param questEnv
     */
    public boolean onQuestTimerEndEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param questEnv
     */
    public boolean onInvisibleTimerEndEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param questEnv
     * @param flyingRing
     */
    public boolean onPassFlyingRingEvent(QuestEnv questEnv, String flyingRing) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onKillRankedEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onKillInWorldEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param env
     * @param itemId
     */
    public boolean onFailCraftEvent(QuestEnv env, int itemId) {
        return false;
    }

    /**
     * @param env
     * @param itemId
     */
    public boolean onEquipItemEvent(QuestEnv env, int itemId) {
        return false;
    }

    /**
     * @param questEventType
     * @param objects
     */
    public boolean onCanAct(QuestEnv env, QuestActionType questEventType, Object... objects) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(env.getQuestId());
        return qs != null && qs.getStatus() == QuestStatus.START;
    }

    /**
     * @param questEnv
     */
    public boolean onAddAggroListEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param questEnv
     */
    public boolean onAtDistanceEvent(QuestEnv questEnv) {
        return false;
    }

    /**
     * @param questEnv
     * @param worldId
     */
    public boolean onEnterWindStreamEvent(QuestEnv questEnv, int worldId) {
        return false;
    }

    /**
     * @param questEnv
     * @param rideItemId
     */
    public boolean rideAction(QuestEnv questEnv, int rideItemId) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onDredgionRewardEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onKamarRewardEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onOphidanRewardEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onBastionRewardEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param env
     * @param bonusType
     * @param rewardItems
     */
    public HandlerResult onBonusApplyEvent(QuestEnv env, BonusType bonusType, List<QuestItems> rewardItems) {
        return HandlerResult.UNKNOWN;
    }

    /**
     * @param env
     */
    public boolean onProtectEndEvent(QuestEnv env) {
        return false;
    }

    /**
     * @param env
     */
    public boolean onProtectFailEvent(QuestEnv env) {
        return false;
    }
}
