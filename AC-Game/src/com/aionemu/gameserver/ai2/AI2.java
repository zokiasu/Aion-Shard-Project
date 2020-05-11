package com.aionemu.gameserver.ai2;

import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemAttackType;

/**
 * @author ATracer
 */
public interface AI2 {

    void onCreatureEvent(AIEventType event, Creature creature);

    void onCustomEvent(int eventId, Object... args);

    void onGeneralEvent(AIEventType event);

    /**
     * If already handled dialog return true.
     */
    boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex);

    void think();

    boolean canThink();

    AIState getState();

    AISubState getSubState();

    String getName();

    boolean poll(AIQuestion question);

    AIAnswer ask(AIQuestion question);

    boolean isLogging();

    long getRemainigTime();

    int modifyDamage(int damage);

    int modifyOwnerDamage(int damage);

    int modifyHealValue(int value);

    int modifyMaccuracy(int value);

    ItemAttackType modifyAttackType(ItemAttackType type);

    int modifyARange(int value);
}
